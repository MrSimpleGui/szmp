package com.webdrp.service.impl;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.constant.UserIn;
import com.webdrp.entity.*;
import com.webdrp.entity.dto.CommodityStyleDto;
import com.webdrp.entity.vo.CommissionResultVo;
import com.webdrp.entity.vo.CommodityStyleCommissionVo;
import com.webdrp.entity.vo.CommodityStyleVo;
import com.webdrp.entity.vo.CommodityVo;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.*;
import com.webdrp.service.CommodityStyleService;
import com.webdrp.util.DoubleUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yuanming on 2018/8/10.
 */
@Service
public class CommodityStyleServiceImpl extends BaseServiceImpl<CommodityStyle, CommodityStyleMapper> implements CommodityStyleService {

    @Autowired
    private CommodityStyleMapper commodityStyleMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private CommissionRuleMapper commissionRuleMapper;

    @Autowired
    private GradeMapper gradeMapper;

    /**
     * 根据商品id删除商品款式
     *
     * @param commodityStyle
     */
    public void deleteByCommodityId(CommodityStyle commodityStyle) {

        commodityStyleMapper.deleteByCommodityId(commodityStyle);

    }


    /**
     * 根据商品款式id删除
     *
     * @param commodityStyle
     */
    public void deleteById(CommodityStyle commodityStyle) {
        commodityStyleMapper.deleteById(commodityStyle);
    }


    @Override
    public List<CommodityStyleCommissionVo> loadAllVo(CommodityStyle commodityStyle, Pager pager) {
        List<CommodityStyleCommissionVo> resultList = new ArrayList<>();
        List<CommodityStyle> commodityStyleList = loadAll(commodityStyle, pager);
        if(commodityStyleList.size() != 0){
            //查出所有角色
            List<Grade> gradeList = gradeMapper.findAll(new Grade());
            commodityStyleList.forEach(item->{
                //根据商品分佣模式分别计算出所有角色各种条件下的分佣金额
                Commodity commodity = commodityMapper.findById(item.getCommodityId());
                CommodityStyleCommissionVo vo = new CommodityStyleCommissionVo();
                BeanUtils.copyProperties(item, vo);
                if(commodity.getcType() != 0){
                    CommissionRule commissionRule = new CommissionRule();
                    commissionRule.setModeId(commodity.getcType());
                    List<CommissionRule> ruleList = commissionRuleMapper.findAll(commissionRule);
                    ruleList.sort((s1,s2)->s1.getGradeRank().compareTo(s2.getGradeRank()));
                    if(ruleList.size() != 0){
                        //根据商品单价和角色配置分别计算分佣金额
                        vo.setResultVoList(calCommission(item.getPrice(), ruleList, gradeList));
                    }
                }
                resultList.add(vo);
            });
        }
        return resultList;
    }

    /**
     * 添加商品款式
     *
     * @param commodityStyleDto
     */
    public void insert(CommodityStyleDto commodityStyleDto) {

        Commodity commodity = commodityMapper.findById(commodityStyleDto.getCommodityId());

        if (commodity == null)
            throw new BusinessException("无该商品存在");

        CommodityStyle commodityStyle = new CommodityStyle();

        BeanUtils.copyProperties(commodityStyleDto, commodityStyle);

        commodityStyle.beforeCreate();

        commodityStyleMapper.insert(commodityStyle);

        List<Image> images = commodityStyleDto.getImages();

        if (images == null || images.size() == 0)
            throw new BusinessException("至少上传一张图片");

        for (Image image : images) {
            image.setUseIn(UserIn.GOODS);//设置为商品
            image.beforeCreate();
            imageMapper.insert(image);

            commodityStyleMapper.addCommodityStyleImage(commodityStyle.getId(), image.getId());
        }
    }

    /**
     * 删除商品款式
     *
     * @param commodityStyle
     */
    public void delete(CommodityStyle commodityStyle) {
        Integer id = commodityStyle.getId();
        if (id == null)
            throw new BusinessException("删除失败");

        //删除关联表的图片
        commodityStyleMapper.deleteCommodityStyleImageByCommodityStyleId(commodityStyle);

        commodityStyle.beforeDelete();

        commodityStyleMapper.delete(commodityStyle);

    }

    /**
     * 更新商品款式
     *
     * @param commodityStyleDto
     */
    public void update(CommodityStyleDto commodityStyleDto) {
        CommodityStyle commodityStyle = new CommodityStyle();
        BeanUtils.copyProperties(commodityStyleDto, commodityStyle);
        commodityStyleMapper.update(commodityStyle);
        commodityStyleMapper.deleteCommodityStyleImageByCommodityStyleId(commodityStyle);
        List<Image> images = commodityStyleDto.getImages();
        if (images == null || images.size() == 0)
            throw new BusinessException("至少上传一张图片");
        for (Image image : images) {
            image.setUseIn(UserIn.GOODS);//设置为商品
            image.beforeCreate();
            imageMapper.insert(image);
            commodityStyleMapper.addCommodityStyleImage(commodityStyle.getId(), image.getId());
        }
    }

    /**
     * 获取商品款式详情
     *
     * @param commodityStyle
     * @return
     */
    public CommodityStyleVo detail(CommodityStyle commodityStyle) {
        Integer id = commodityStyle.getId();
        if (id == null)
            throw new BusinessException("无该商品款式");
        CommodityStyle res = commodityStyleMapper.findById(id);
        if (res == null)
            throw new BusinessException("无该商品款式");
        CommodityStyleVo commodityStyleVo = new CommodityStyleVo();
        BeanUtils.copyProperties(res, commodityStyleVo);
        //查找图片的信息
        List<Image> images = commodityStyleMapper.findCommodityStyleImageByCommodityStyleId(id);
        commodityStyleVo.setImages(images);
        return commodityStyleVo;
    }

    private List<CommissionResultVo> calCommission(Double price, List<CommissionRule> ruleList,
                                                   List<Grade> gradeList){
        List<CommissionResultVo> resultVoList = new ArrayList<>();
        ruleList.forEach(item->{
            CommissionResultVo resultVo = new CommissionResultVo();
            Grade grade = gradeList.stream()
                    .filter(g->g.getRank().equals(item.getGradeRank()))
                    .collect(Collectors.toList()).get(0);
            resultVo.setGradeName(grade.getName());
            switch (item.getType()){
                case 0:
                    resultVo.setFirst(DoubleUtil.mul(price, item.getFirstLevel()));
                    resultVo.setSecond(DoubleUtil.mul(price, item.getSecondLevel()));
                    resultVo.setTeamReward(DoubleUtil.mul(price, item.getTeamReward()));
                    resultVo.setSidewayReward(DoubleUtil.mul(price, item.getSidewayCommission()));
                    break;
                default:
                    resultVo.setFirst(item.getFirstLevel());
                    resultVo.setSecond(item.getSecondLevel());
                    resultVo.setTeamReward(item.getTeamReward());
                    resultVo.setSidewayReward(item.getSidewayCommission());
                    break;
            }
            resultVoList.add(resultVo);
        });
        return resultVoList;
    }


}

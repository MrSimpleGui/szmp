package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.constant.CommodityPublish;
import com.webdrp.constant.UserIn;
import com.webdrp.entity.*;

import com.webdrp.entity.dto.CommodityDto;
import com.webdrp.entity.vo.CommodityAllVo;
import com.webdrp.entity.vo.CommodityVoIndex;
import com.webdrp.err.BusinessException;

import com.webdrp.entity.vo.CommodityVo;

import com.webdrp.mapper.CommodityMapper;
import com.webdrp.mapper.CommodityStyleMapper;
import com.webdrp.mapper.ImageMapper;
import com.webdrp.mapper.MenuCommodityMapper;
import com.webdrp.service.CommodityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by yuanming on 2018/8/10.
 */
@Service
public class CommodityServiceImpl extends BaseServiceImpl<Commodity, CommodityMapper> implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private CommodityStyleMapper commodityStyleMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private MenuCommodityMapper menuCommodityMapper;

    /**
     * 删除商品
     *
     * @param commodity
     */
    public void delete(Commodity commodity) {
        try {
            //List<CommodityStyle> commodityStyles = commodityStyleMapper.loadCommodityStyleByCommodity(commodity);
            //根据商品id删除菜单商品
            MenuCommodity menuCommodity = new MenuCommodity();
            menuCommodity.setCommodityId(commodity.getId());
            menuCommodity.beforeDelete();
            menuCommodityMapper.deleteByCommodityId(menuCommodity);
            //先删商品款式
            CommodityStyle commodityStyle = CommodityStyle.CommodityStyleBuilder.builder()
                    .commodityId(commodity.getId()).build();
            commodityStyle.beforeDelete();
            commodityStyleMapper.deleteByCommodityId(commodityStyle);
            //删除商品图片
            commodityMapper.deleteCommodityImageByCommodityId(commodity);//
            //再删除商品
            commodity.beforeDelete();
            commodityMapper.delete(commodity);

        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("删除失败！");
        }
    }

    /**
     * 添加商品
     *
     * @param commodityDto
     */
    public void insert(CommodityDto commodityDto) {
        try {
            Commodity commodity = new Commodity();
            BeanUtils.copyProperties(commodityDto, commodity);
            commodity.setPublish(CommodityPublish.NO);
            commodity.beforeCreate();
            commodityMapper.insert(commodity);

            List<Image> images = commodityDto.getImages();
            if (images == null || images.size() == 0)
                throw new BusinessException("至少上传一张图片");

            for (Image image : images) {
                image.setUseIn(UserIn.GOODS);//设置为商品
                image.beforeCreate();
                imageMapper.insert(image);
                commodityMapper.addCommodityImage(commodity.getId(), image.getId());
            }
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("添加商品失败");
        }
    }

    /**
     * 更新商品信息
     *
     * @param commodityDto
     */
    public void update(CommodityDto commodityDto) {
        try {
            Commodity commodity = new Commodity();
            BeanUtils.copyProperties(commodityDto, commodity);
            commodityMapper.update(commodity);//更新信息
            commodityMapper.deleteCommodityImageByCommodityId(commodity);//先删除关联表数据
            List<Image> images = commodityDto.getImages();
            if (images == null || images.size() == 0)
                throw new BusinessException("至少上传一张图片");

            for (Image image : images) {
                image.setUseIn(UserIn.GOODS);//设置为商品
                image.beforeCreate();
                imageMapper.insert(image);
                commodityMapper.addCommodityImage(commodity.getId(), image.getId());
            }
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("更新失败");
        }
    }


    /**
     * 上下架
     *
     * @param commodity
     */
    public void publish(Commodity commodity) {
        Integer publish = commodity.getPublish();
        if (publish == null || publish.intValue() > 1 || publish.intValue() < 0)
            throw new BusinessException("失败");
        CommodityStyle commodityStyle = new CommodityStyle();
        commodityStyle.setCommodityId(commodity.getId());
        List<CommodityStyle> commodityStyles = commodityStyleMapper.loadCommodityStyleByCommodityId(commodityStyle);
        if(commodityStyles.size() == 0)
            throw new BusinessException("该商品无款式，无法上架");
        commodityMapper.update(commodity);
    }

    /**
     * 获取商品详情
     *
     * @param commodity
     * @return
     */
    public CommodityVo detail(Commodity commodity) {
        Integer id = commodity.getId();
        if (id == null)
            throw new BusinessException("不存在该商品");
        Commodity res = commodityMapper.findById(id);
        if (res == null)
            throw new BusinessException("不存在该商品");
        CommodityVo commodityVo = new CommodityVo();
        BeanUtils.copyProperties(res, commodityVo);
        //查找图片的信息
        List<Image> images = commodityMapper.findCommodityImageByCommodityId(id);
        commodityVo.setImages(images);
        return commodityVo;
    }

    @Override
    public List<CommodityAllVo> findCommodityVo(Commodity commodity) {
        return commodityMapper.findCommodityVo(commodity);
    }

    @Override
    public CommodityAllVo findVoById(Integer commodityId) {
        return commodityMapper.findVoById(commodityId);
    }

    @Override
    public long findCommodityAllCount(Commodity commodity) {
        return commodityMapper.findCommodityAllCount(commodity);
    }

    @Override
    public List<CommodityAllVo> findCommodityAllByPage(Commodity commodity, Pager pager) {
        long countAll = commodityMapper.findCommodityAllCount(commodity);
        pager = getPager(countAll,pager);
        return commodityMapper.findCommodityAllByPage(commodity,pager);
    }

    @Override
    public List<Commodity> findCommodityByVentorId(Commodity commodity) {
        return commodityMapper.findCommodityByVentorId(commodity);
    }

    @Override
    public List<CommodityVoIndex> findIndexCommodity(Pager pager) {

        try {
            //获取首页商品
            Commodity commodity = new Commodity();
            commodity.setClassId(1);
            long countAll = commodityMapper.count(commodity);
            pager = getPager(countAll, pager);

            return commodityMapper.findVoIndexByClassId(commodity, pager);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("删除失败！");
        }

    }

}

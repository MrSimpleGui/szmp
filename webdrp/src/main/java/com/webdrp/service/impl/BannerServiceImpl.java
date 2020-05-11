package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.entity.Banner;
import com.webdrp.entity.CommodityStyle;
import com.webdrp.entity.MenuCommodity;
import com.webdrp.entity.vo.BannerVo;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.BannerMapper;
import com.webdrp.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 15:27 2020-03-17
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Service
public class BannerServiceImpl extends BaseServiceImpl<Banner, BannerMapper> implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<BannerVo> findAllVo(Banner banner, Pager pager) {
        try {
            long countAll = bannerMapper.count(banner);
            pager = getPager(countAll, pager);
            return bannerMapper.findAllVo(banner, pager);
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            throw new BusinessException("删除失败！");
        }
    }
}

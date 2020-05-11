package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.entity.Banner;
import com.webdrp.entity.vo.BannerVo;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 15:26 2020-03-17
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public interface BannerService extends BaseService<Banner> {

    List<BannerVo> findAllVo(Banner banner, Pager pager);
}

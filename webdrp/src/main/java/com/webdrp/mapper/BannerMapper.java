package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.Pager;
import com.webdrp.entity.Banner;
import com.webdrp.entity.vo.BannerVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 15:11 2020-03-17
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Component
public interface BannerMapper extends BaseMapper<Banner> {

    List<BannerVo> findAllVo(@Param("entity") Banner banner, @Param("pager") Pager pager);
}

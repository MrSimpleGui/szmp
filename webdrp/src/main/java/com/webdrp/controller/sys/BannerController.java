package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Banner;
import com.webdrp.entity.Menu;
import com.webdrp.entity.vo.BannerVo;
import com.webdrp.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 18:04 2020-03-16
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Api(tags = "后台 轮播图管理")
@RestController
@RequestMapping("/sys/banner")
public class BannerController extends BaseController<Banner,Integer> {

    @Autowired
    BannerService bannerService;

    /**
     * 抽象业务类  T 实体对象   ID为
     *
     * @return
     */
    @Override
    public BaseService<Banner> getService() {
        return bannerService;
    }

    public Result delete(@RequestHeader(value="token") String token, Integer id) {
        Banner banner = new Banner();
        banner.setId(id);
        getService().delete(banner);
        return Result.success();
    }

    public Result loadAll(@RequestHeader(value="token") String token , Banner banner, Pager pager){
        List<BannerVo> list = bannerService.findAllVo(banner, pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

}

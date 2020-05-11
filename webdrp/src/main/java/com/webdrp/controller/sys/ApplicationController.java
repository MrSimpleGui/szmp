package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Result;
import com.webdrp.constant.ApplicationStatus;
import com.webdrp.constant.ApplicationType;
import com.webdrp.entity.Application;
import com.webdrp.service.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "后台接口 申请表")
@RestController
@RequestMapping("/sys/application")
public class ApplicationController extends BaseController<Application,Integer> {

    @Autowired
    ApplicationService applicationService;

    /**
     * 抽象业务类  T 实体对象 ID为int
     * @return
     */
    @Override
    public BaseService<Application> getService() {
        return applicationService;
    }

    @ApiOperation("申请表状态")
    @GetMapping("/status")
    public Result getStatus(){
        return Result.success().addAttribute("status",ApplicationStatus.map);
    }

    @ApiOperation("申请表type")
    @GetMapping("/types")
    public Result getTypes(){
        return Result.success().addAttribute("types",ApplicationType.map);
    }

    @ApiOperation("设置申请表处理状态")
    @PostMapping("/updateStatus")
    public Result updateStatus(Integer applicationId,Integer status){
        Application application = new Application();
        application.setId(applicationId);
        application.setApplicationStatus(status);
        applicationService.update(application);
        return Result.success();
    }

    //导出

    //查询
}

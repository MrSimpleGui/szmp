package com.webdrp.controller.agent;

import com.webdrp.annotation.AgentUserAnnotation;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.constant.ApplicationStatus;
import com.webdrp.entity.Application;
import com.webdrp.entity.Provider;
import com.webdrp.service.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "agent 申请表")
@RestController
@RequestMapping("/agent/application")
public class AgentApplicationController {

    @Autowired
    ApplicationService applicationService;

//    @ApiOperation("申请列表,带分页")
//    @GetMapping("/list")
    public Result list(@RequestHeader("agentauth") String token,@AgentUserAnnotation Provider provider, Application application, Pager pager){
        application.setApplicationStatus(ApplicationStatus.SUCCESS);
        List<Application> list = applicationService.loadAll(application,pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }
}

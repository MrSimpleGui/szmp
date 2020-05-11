package com.webdrp.controller.agent;

import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Notification;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/agent/notify")
public class AgentNotificationController {

    @Autowired
    NotificationService notificationService;


    @GetMapping(value = "/list")
    @ApiOperation("微信公告")
    public Result list(@RequestHeader("agentauth") String token,Pager pager){
        Notification notification = new Notification();
        notification.setPublished(1);
        notification.setType(0);
        List<Notification> list = notificationService.loadAll(notification,pager);
        return Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }

    @GetMapping(value = "/agentcentor")
    @ApiOperation("运营中心公告")
    public Result listZhongXin(@RequestHeader("agentauth") String token,Pager pager){
        Notification notification = new Notification();
        notification.setPublished(1);
        notification.setType(1);
        List<Notification> list = notificationService.loadAll(notification,pager);
        return Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }

    @GetMapping(value = "/findById")
    @ApiOperation("公告通知获取 单个")
    public Result findById(@RequestHeader("agentauth") String token,@RequestParam Integer id){
        Notification notification = new Notification();
        notification.setPublished(1);
        notification.setType(0);
        notification.setId(id);
        List<Notification> list = notificationService.findAll(notification);
        if (list.size()>0)
            return Result.success(list.get(0));
        else
            return Result.success();
    }

}

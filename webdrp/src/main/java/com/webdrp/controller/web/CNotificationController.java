package com.webdrp.controller.web;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Member;
import com.webdrp.entity.Notification;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.NotificationService;
import com.webdrp.util.MdRenderUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: zhang yuan ming
 * @Date: create in 18:07 2020-03-17
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Api(tags = "C端通知")
@RestController
@RequestMapping("/wechat/api")
public class CNotificationController {

    @Autowired
    NotificationService notificationService;

    @WechatRequest
    @SystemControllerLog(description = "通知列表")
    @GetMapping("/notificationList")
    public Result notificationList(@PBUserAnnotation Member member, Pager pager){
        Notification notification = new Notification();
        notification.setPublished(1);
        List<Notification> list = notificationService.loadAll(notification,pager);
        return Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }
    @WechatRequest
    @SystemControllerLog(description = "通知详情")
    @GetMapping("/notificationDetail")
    public Result notificationDetail(@PBUserAnnotation Member member,@RequestParam Integer id){
        Notification notification = notificationService.findOne(id);
        notification.setContent(MdRenderUtils.renderMdToHtmlString(notification.getContent()));
        return Result.success(notification);
    }

}

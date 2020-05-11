package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Result;
import com.webdrp.constant.CommodityPublish;
import com.webdrp.constant.NotificationType;
import com.webdrp.entity.Notification;
import com.webdrp.service.NotificationService;
import com.webdrp.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(tags = "后台接口 通知")
@RestController
@RequestMapping("/sys/notification")
public class NotificationController extends BaseController<Notification,Integer> {

    @Autowired
    NotificationService notificationService;


    /**
     * 抽象业务类  T实体对象 ID为
     * @return
     */
    @Override
    public BaseService<Notification> getService() {
        return notificationService;
    }


    @PostMapping("/published")
    @ApiOperation("发布接口 notificationId 为ID published发布的时候为1不发布为0 ")
    public Result published(@RequestParam Integer notificationId,@RequestParam Integer published){
        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setPublished(published);
        notificationService.published(notification);
        return Result.success();
    }

    @GetMapping("/getPublished")
    @ApiOperation("发布状态")
    public Result publishedType(){
        return Result.success(CommodityPublish.map);
    }

    @GetMapping("/getNotificationType")
    @ApiOperation("获取通知类型type")
    public Result getNotificationType(){
        return Result.success(NotificationType.map);
    }

    @Override
    public Result delete(String token, Integer id) {
        Notification notification = new Notification();
        notification.setDeleteTime(DateUtils.dateToString(new Date()));
        notificationService.delete(notification);
        return Result.success();
    }
}

package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public interface NotificationMapper extends BaseMapper<Notification> {

    void published(Notification notification);
}

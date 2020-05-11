package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.Notification;
import com.webdrp.mapper.NotificationMapper;
import com.webdrp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends BaseServiceImpl<Notification,NotificationMapper> implements NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    @Override
    public void published(Notification notification) {
        notificationMapper.published(notification);
    }
}

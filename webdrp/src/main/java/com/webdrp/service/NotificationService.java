package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.Notification;

public interface NotificationService extends BaseService<Notification> {

    void published(Notification notification);
}

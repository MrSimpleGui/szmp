package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.Application;

import java.util.List;

public interface ApplicationService extends BaseService<Application> {

    List<Application> findByRichUserId(Application application);
}

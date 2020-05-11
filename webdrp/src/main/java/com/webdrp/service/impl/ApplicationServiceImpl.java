package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.constant.ApplicationStatus;
import com.webdrp.entity.Application;
import com.webdrp.mapper.ApplicationMapper;
import com.webdrp.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl extends BaseServiceImpl<Application,ApplicationMapper> implements ApplicationService {

    @Autowired
    ApplicationMapper applicationMapper;

    @Override
    public List<Application> findByRichUserId(Application application) {
        return applicationMapper.findByRichUserId(application);
    }

    @Override
    public void add(Application application) {
        //默认状态
        application.setApplicationStatus(ApplicationStatus.TODO);
        super.add(application);
    }
}

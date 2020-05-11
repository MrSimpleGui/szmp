package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.Application;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ApplicationMapper extends BaseMapper<Application> {

    List<Application> findByRichUserId(Application application);
}

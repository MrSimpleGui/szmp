package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.CommissionMode;

public interface CommissionModeService extends BaseService<CommissionMode> {

    void insert(CommissionMode commissionMode);

    void delete(CommissionMode commissionMode);

    Boolean selectModeByName(String name);
}

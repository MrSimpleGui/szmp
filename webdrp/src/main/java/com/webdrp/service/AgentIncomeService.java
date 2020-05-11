package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.AgentIncome;

public interface AgentIncomeService extends BaseService<AgentIncome> {

    //旅游线路的某团佣金
    AgentIncome findByCsid(AgentIncome agentIncome);



}

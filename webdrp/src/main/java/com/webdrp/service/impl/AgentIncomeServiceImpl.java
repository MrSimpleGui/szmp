package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.AgentIncome;
import com.webdrp.mapper.AgentIncomeMapper;
import com.webdrp.service.AgentIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentIncomeServiceImpl extends BaseServiceImpl<AgentIncome,AgentIncomeMapper> implements AgentIncomeService {

    @Autowired
    AgentIncomeMapper agentIncomeMapper;

    @Override
    public AgentIncome findByCsid(AgentIncome agentIncome) {
        return agentIncomeMapper.findByCsid(agentIncome);
    }
}

package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.AgentIncome;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AgentIncomeMapper  extends BaseMapper<AgentIncome> {

    //旅游线路的某团佣金
    AgentIncome findByCsid(AgentIncome agentIncome);

    //旅游线路的返佣金
    List<AgentIncome> findByCid(AgentIncome agentIncome);

    //通过佣金类型获取返佣金
    List<AgentIncome> findByAgentIncomeType(AgentIncome agentIncome);


}

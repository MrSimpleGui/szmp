package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.entity.Provider;
import com.webdrp.entity.vo.ProviderCommodityVo;

import java.util.List;

public interface ProviderService extends BaseService<Provider> {
    /**
     * agent 登录，成功返回token 不成功走其他渠道咯
     * @param username
     * @param password
     * @return
     */
    String agentLogin(String username,String password);

    List<Provider> getAgentListCityAndProvince();

    List<ProviderCommodityVo> findAgentCommodityVo(Provider provider, Pager pager);
}

package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.Pager;
import com.webdrp.entity.Provider;
import com.webdrp.entity.vo.ProviderCommodityVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProviderMapper extends BaseMapper<Provider> {

    Provider findByUsername(Provider provider);

    List<Provider> getAgentListCityAndProvince();

    List<ProviderCommodityVo> findAgentCommodityVo(@Param(value = "entity") Provider provider, @Param(value = "pager") Pager pager);
}

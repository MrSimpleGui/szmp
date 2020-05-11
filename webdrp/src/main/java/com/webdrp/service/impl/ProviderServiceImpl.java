package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.constant.AgentType;
import com.webdrp.entity.Provider;
import com.webdrp.entity.vo.ProviderCommodityVo;
import com.webdrp.err.BusinessException;
import com.webdrp.err.CannotLoginError;
import com.webdrp.err.NoUserDataError;
import com.webdrp.mapper.ProviderMapper;
import com.webdrp.service.ProviderService;
import com.webdrp.util.AesUtils;
import com.webdrp.util.AgentPassUtil;
import com.webdrp.util.Sha256decode;
import com.webdrp.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
public class ProviderServiceImpl extends BaseServiceImpl<Provider,ProviderMapper> implements ProviderService {

    @Autowired
    ProviderMapper providerMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void add(Provider provider) {
        provider.setProviderpassword(AgentPassUtil.getPasswordEncode(provider.getProviderpassword()));
        super.add(provider);
    }


    /**
     * agent 登录，成功返回token 不成功走其他渠道咯
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public String agentLogin(String username, String password) {
        //去掉空格
        username = username.trim();
        Provider provider = new Provider();
        provider.setProviderusername(username);
        Provider dbuser = providerMapper.findByUsername(provider);
        if (Objects.nonNull(dbuser)){
            if (dbuser.getAgentType().intValue()==AgentType.AgentShop.intValue()){
                throw new CannotLoginError("商城业主不允许登录");
            }
            //账号正常
            if (dbuser.getProviderpassword().equals(AgentPassUtil.getPasswordEncode(password))){
                if (dbuser.getStatus()==1){
                    String token = getToken(dbuser);
                    return token;
                }else{
                    throw new CannotLoginError("暂时不允许登录");
                }
            }else{
                throw new BusinessException("密码错误");
            }
        }else{
            throw new NoUserDataError("没有此用户！");
        }
    }

    @Override
    public List<Provider> getAgentListCityAndProvince() {
        return providerMapper.getAgentListCityAndProvince();
    }

    @Override
    public List<ProviderCommodityVo> findAgentCommodityVo(Provider provider, Pager pager) {
        long countAll = providerMapper.count(provider);
        pager = getPager(countAll,pager);
        return providerMapper.findAgentCommodityVo(provider, pager);
    }

    public String getToken(Provider agent){

        String uuid = UUIDUtils.getUUID();//加密

        String username = getKey(agent.getProviderusername());

        String hashCode = agent.getProviderusername()+uuid;

        String sign = Sha256decode.getSHA256Str(hashCode);

        //给假的uuid
        String userToken = UUIDUtils.getUUID() + "."+username+"."+sign;

        String cacheToken = uuid + "."+username+"."+sign;

        ValueOperations<String, Object> operations=redisTemplate.opsForValue();

        //System.out.println("richUser = [" + username + "]");

        operations.set(username,cacheToken,60*60*24, TimeUnit.SECONDS);
        operations.set(uuid, agent,60*60*24, TimeUnit.SECONDS);
        return  userToken;
    }

    public String getKey(String str){
        str = AesUtils.encrypt(str,AesUtils.AgentAESLOGINKEY);
        return str;

    }

}

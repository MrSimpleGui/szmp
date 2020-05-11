package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.Member;
import com.webdrp.entity.Order;
import com.webdrp.entity.Propcard;
import com.webdrp.entity.vo.PropcardVo;

import java.util.List;

public interface PropcardService extends BaseService<Propcard> {

    public void rename(Member member);

    public void resuper(Member member, Integer topId);

    public void dealPropcard(Order order);

    public List<PropcardVo> findPropcard(Member member);
}

package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Result;
import com.webdrp.entity.CardBag;
import com.webdrp.entity.Cardbaglog;
import com.webdrp.entity.Cardbaguse;
import com.webdrp.entity.Member;
import com.webdrp.err.BusinessException;
import com.webdrp.mapper.CardBagMapper;
import com.webdrp.mapper.CardbaglogMapper;
import com.webdrp.mapper.CardbaguseMapper;
import com.webdrp.mapper.MemberMapper;
import com.webdrp.service.CardBagService;
import com.webdrp.service.VipCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.smartcardio.Card;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 18:15 2020-04-23
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Service
public class CardBagServiceImpl extends BaseServiceImpl<CardBag, CardBagMapper> implements CardBagService {

    @Autowired
    CardbaguseMapper cardbaguseMapper;

    @Autowired
    CardbaglogMapper cardbaglogMapper;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    CardBagMapper cardBagMapper;

    @Autowired
    VipCardService vipCardService;

    /**
     * 发卡给某人，或者送卡给某人，会改变上级，后台不能使用此接口
     * @param cardbaglog
     */
    @Override
    public void giveCardToUser(Cardbaglog cardbaglog) {

        if (cardbaglog.getOrignUserId().intValue() <= 0){
            throw new BusinessException("发起赠送人错误！");
        }

        if (cardbaglog.getToUserId().intValue() <= 0){
            throw new BusinessException("赠送人错误！");
        }

        if (cardbaglog.getNumber().intValue() <= 0){
            throw new BusinessException("赠送数量错误！");
        }

        if(!cardbaglog.getOrignUserId().equals(99998)){
            CardBag cardBag = cardBagMapper.findByRichUserId(cardbaglog.getOrignUserId());

            if (Objects.isNull(cardBag)){
                throw new BusinessException("会员卡数量不足");
            }

            if (cardBag.getNumber() < cardbaglog.getNumber()){
                if(cardbaglog.getToUserId().equals(99998)){
                    throw new BusinessException("收回卡片数量不足！");
                } else {
                    throw new BusinessException("会员卡数量不足，请减少赠送数量！");
                }
            }
            cardBag.setNumber(cardBag.getNumber() - cardbaglog.getNumber());
            cardBagMapper.updateNumber(cardBag);
        }
        cardbaglogMapper.insert(cardbaglog);
        //不是系统收回就更新卡片数量
        if(!cardbaglog.getToUserId().equals(99998)){
            CardBag toCardBag = cardBagMapper.findByRichUserId(cardbaglog.getToUserId());
            if (Objects.isNull(toCardBag)){
                toCardBag = new CardBag();
                toCardBag.setNumber(cardbaglog.getNumber());
                toCardBag.setRichUserId(cardbaglog.getToUserId());
                cardBagMapper.insert(toCardBag);
            }else{
                toCardBag.setNumber(toCardBag.getNumber() + cardbaglog.getNumber());
                cardBagMapper.updateNumber(toCardBag);
            }
        }
        //系统赠送/收回不改变路径
        if(cardbaglog.getOrignUserId().equals(99998) || cardbaglog.getToUserId().equals(99998)){
            return;
        }
        changeTopUser(cardbaglog);
        return;
    }



    public void changeTopUser(Cardbaglog cardbaglog){
        Member temp = memberMapper.findById(cardbaglog.getOrignUserId());
        Member member = memberMapper.findById(cardbaglog.getToUserId());
        String path = temp.getPath();
        if (StringUtils.isEmpty(path)){
            path = "" + temp.getId();
        }else{
            path = path + "," + temp.getId();
        }
        member.setZid(temp.getId());
        member.setPath(path);
        memberMapper.update(member);
        return;
    }

    /**
     * 用卡
     * @param cardbaguse
     */
    @Override
    public void useCard(Cardbaguse cardbaguse) {
        CardBag cardBag = cardBagMapper.findByRichUserId(cardbaguse.getRichUserId());
        if (Objects.isNull(cardBag)){
            throw new BusinessException("卡包不存在！");
        }
        if (cardBag.getNumber() <= 0){
            throw new BusinessException("卡包数量不足！");
        }


        if (StringUtils.isEmpty(cardbaguse.getPhone())){
            throw new BusinessException("手机号码不正确！");
        }

        if (cardbaguse.getPhone().length() != 11){
            throw new BusinessException("手机号码不正确！");
        }
        try {
            Result result = vipCardService.checkoutUserCanBuy(cardbaguse.getPhone());
            if (result.isStatus() && result.getData().equals("01")){
                vipCardService.openVip(cardbaguse.getPhone(),"","");
                cardBag.setNumber(cardBag.getNumber()-1);
                cardBagMapper.updateNumber(cardBag);
                cardbaguseMapper.insert(cardbaguse);
                updateGradeIfFans(cardbaguse);
                //记录手机号到用户表
                Member member = new Member();
                member.setId(cardbaguse.getRichUserId());
                member.setRegisterPhone(cardbaguse.getPhone());
                memberMapper.updateRegisterPhone(member);
                return;
            }else{
                throw new BusinessException("已开通！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("系统错误！");
        }
    }

    public void updateGradeIfFans(Cardbaguse cardbaguse){
        Member member = memberMapper.findById(cardbaguse.getRichUserId());
        if (Objects.isNull(member)){
            return;
        }
        if (member.getGrade().intValue() == 0){
            member.setGrade(1);
            memberMapper.update(member);
        }
    }

    @Override
    public CardBag findByRichUserId(Integer userId) {
        CardBag cardBag = cardBagMapper.findByRichUserId(userId);
        if (Objects.isNull(cardBag)){
            cardBag = new CardBag();
            cardBag.setNumber(0);
            cardBag.setRichUserId(userId);
            cardBagMapper.insert(cardBag);
        }
        return cardBag;
    }

}

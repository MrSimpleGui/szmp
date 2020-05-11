package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.constant.IncomeType;
import com.webdrp.entity.Income;
import com.webdrp.entity.Member;
import com.webdrp.entity.User;
import com.webdrp.entity.vo.MemberSuperiorVo;
import com.webdrp.entity.vo.MemberSysVo;
import com.webdrp.err.BusinessException;
import com.webdrp.err.LoginForBidden;
import com.webdrp.err.NoSuchUserError;
import com.webdrp.err.PasswordError;
import com.webdrp.mapper.IncomeMapper;
import com.webdrp.mapper.MemberMapper;
import com.webdrp.service.MemberService;
import com.webdrp.util.AesUtils;
import com.webdrp.util.Sha256decode;
import com.webdrp.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by yuanming on 2018/8/3.
 */
@Service
public class MemberServiceImpl extends BaseServiceImpl<Member, MemberMapper> implements MemberService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    IncomeMapper incomeMapper;

    Logger logger = LoggerFactory.getLogger(getClass());




    @Override
    public void add(Member member) {
        if (Objects.isNull(member.getJid())){
            member.setJid(0);
        }
        member.setStatus(0);
        member.setGrade(0);
        member.setWallet(0.00);
        if (member.getZid()==null){
            member.setZid(0);
        }
        if (member.getPassword()!= null){
            member.setPassword(Sha256decode.getSHA256Str(member.getPassword()));
        }
        super.add(member);
    }

    @Override
    public void update(Member member) {
        super.update(member);
    }

    @Override
    public String login(Member member){
        Member memberData = memberMapper.findByUserName(member.getUsername());
        if (Objects.isNull(memberData)){
            throw new NoSuchUserError("没有此用户！");
        }
        System.out.println("richUser = [" + memberData.getPassword() + "\n"+Sha256decode.getSHA256Str(member.getPassword()));
        if (memberData.getStatus()!=0){
            throw new LoginForBidden("拒绝登录");
        }
        if (!memberData.getPassword().equals(Sha256decode.getSHA256Str(member.getPassword()))){
            throw new PasswordError("错误密码！");
        }
        return getToken(memberData);
    }

    @Override
    public Member findByOpenId(String openId) {
        return memberMapper.findByOpenId(openId);
    }

    @Override
    public Member findByOpenId1(String openId) {
        return memberMapper.findByOpenId1(openId);
    }

    /**
     * 统计时间段内的用户数量
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public int countByDate(String startDate, String endDate) {
        return memberMapper.countByDate(startDate,endDate);
    }

    @Override
    public void setRichUserStatus(Member member) {
        memberMapper.setRichUserStatus(member);
    }

    @Override
    public Long findMaxId() {
        return memberMapper.findMaxId();
    }

    @Override
    public void updateAddress(Member member) {
        memberMapper.updateAddress(member);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void setAutoCreate(Integer addnum) {
        Long maxInt = memberMapper.findMaxId();
        maxInt = maxInt+addnum;
        memberMapper.setAutoInAddId(maxInt);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public void addUserWallet(Integer richUserId, Integer money) {
        Member member = memberMapper.findById(richUserId);
        if (Objects.isNull(member)){
            throw new BusinessException("用户不存在");
        }
        Double mm = money+0.00;
        member.setWallet(member.getWallet()+mm);
        memberMapper.update(member);
        Income income = new Income();
        income.setTargetRichUserId(richUserId);
        income.setAmount(0);
        income.setIncomeType(IncomeType.XITONG);
        income.setNote("平台奖励");
        income.setOriginRichUserId(richUserId);
        income.setGrade(member.getGrade());
        income.setOrderId(10000000);
        income.setFloor(1);
        income.setMoney(mm);
        incomeMapper.insert(income);
    }

    @Override
    public void updateOpenId(Member member) {
        memberMapper.updateOpenId(member);
    }

    @Override
    public void updateImageUrl(Member member) {
        memberMapper.updateImageUrl(member);
    }

    @Override
    public void register(Member member) {
        if (Objects.isNull(member.getJid())){
            member.setJid(0);
        }
        member.setStatus(0);
        member.setGrade(0);
        member.setWallet(0.00);
        if (member.getZid()==null){
            member.setZid(0);
        }
        if (member.getPassword()!= null){
            member.setPassword(Sha256decode.getSHA256Str(member.getPassword()));
        }
        memberMapper.register(member);
    }

    @Override
    public Member findByUserName(String username) {
        return memberMapper.findByUserName(username);
    }

    /**
     * 第一层
     *
     * @param richUserId
     * @param pager
     * @return
     */
    @Override
    public List<Member> findFirst(Integer richUserId, Pager pager) {
        long countAll = memberMapper.countFirst(richUserId);
        pager = getPager(countAll,pager);
        return memberMapper.findFirst(richUserId,pager);
    }

    /**
     * 第二层
     *
     * @param richUserId
     * @param pager
     * @return
     */
    @Override
    public List<Member> findSecond(Integer richUserId, Pager pager) {
        long countAll = memberMapper.countSecond(richUserId);
        pager = getPager(countAll,pager);
        return memberMapper.findSecond(richUserId,pager);
    }

    /**
     * 第三层
     *
     * @param richUserId
     * @param pager
     * @return
     */
    @Override
    public List<Member> findThird(Integer richUserId, Pager pager) {
        long countAll = memberMapper.countThird(richUserId);
        pager = getPager(countAll,pager);
        return memberMapper.findThird(richUserId,pager);
    }

    /**
     * 计数
     *
     * @param richUserId
     * @return
     */
    @Override
    public long countFirst(Integer richUserId) {
        return memberMapper.countFirst(richUserId);
    }

    @Override
    public long countSecond(Integer richUserId) {
        return memberMapper.countSecond(richUserId);
    }

    @Override
    public long countThird(Integer richUserId) {
        return memberMapper.countThird(richUserId);
    }

    /**
     * 城市代理 统计人数，团队人数
     *
     * @param member
     * @return
     */
    @Override
    public int findTeamUserCount(Member member) {
        return memberMapper.findTeamUserCount(member);
    }

    /**
     * 团队会员数量
     *
     * @param member
     * @return
     */
    @Override
    public int findTeamVipUserCount(Member member) {
        return memberMapper.findTeamVipUserCount(member);
    }

    /**
     * 某人团队成交所有订单,累计成交
     *
     * @param member
     */
    @Override
    public int findTeamUserOrderCount(Member member, String startTime) {
        return memberMapper.findTeamUserOrderCount(member,startTime);
    }

    /**
     * 统计某人成交所有订单
     *
     * @param member
     * @return
     */
    @Override
    public int findTeamUserOrderCountV1(Member member) {
        return memberMapper.findTeamUserOrderCountV1(member);
    }

    /**
     * 一级级差订单
     *
     * @param member
     * @return
     */
    @Override
    public int findTeamUserOrderCountFirst(Member member) {
        return memberMapper.findTeamUserOrderCountFirst(member);
    }

    /**
     * 团队时间段内的成交量，用做今日成交
     *
     * @param member
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public int findTeamUserDayCount(Member member, String startTime, String endTime) {
        return memberMapper.findTeamUserDayCount(member,startTime,endTime);
    }

    @Override
    public List<Member> findTeamUser(Member member) {
        return memberMapper.findTeamUser(member);
    }

    @Override
    public List<Member> findAllCityUser() {
        return memberMapper.findAllCityUser();
    }

    @Override
    public List<MemberSysVo> findSysRichUser(Member member, Pager pager) {
        long countAll = memberMapper.count(member);
        pager = getPager(countAll,pager);
        return memberMapper.findSysRichUser(member,pager);
    }

    /**
     * 分页
     *
     * @param id
     * @param pager
     * @return
     */
    @Override
    public List<Member> findTeamUserByPage(Integer id, Pager pager) {
        Member member = new Member();
        member.setId(id);
        long countAll = memberMapper.findTeamUserCount(member);
        pager = getPager(countAll,pager);
        return memberMapper.findTeamUserByPage(id,pager);
    }

    @Override
    public List<MemberSuperiorVo> findTeamMemberVoByPage(Integer id, Pager pager) {
        Member member = new Member();
        member.setId(id);
        long countAll = memberMapper.findTeamUserCount(member);
        pager = getPager(countAll,pager);
        return memberMapper.findTeamMemberVoByPage(id, pager);
    }




    /**
     * 更新上家ID
     *
     * @param temp
     * @param newTopId
     */
    @Override
    public boolean updateTopId(Member temp, Object newTopId) {
        if (Objects.isNull(newTopId)){
            return false;
        }

        Integer tips = 0;
        try {
            tips = Integer.parseInt("" + newTopId);
        }catch (Exception e){
            logger.warn("上家ID错误");
//            e.printStackTrace();
            return false;
        }

        if (tips.intValue() <= 0){
            return false;
        }

        if (Objects.isNull(temp)){
            return false;
        }

        if (temp.getGrade().intValue() > 0) {
            return false;
        }else{
            try {
                // 判断是否换上家
                if (Objects.nonNull(tips)){
                    //if (tips.intValue() < temp.getId().intValue()){
                        if (temp.getZid().intValue() != tips.intValue()){
                            Member newMember = findOne(tips);
                            if (Objects.nonNull(newMember)){
                                if (newMember.getGrade().intValue() > 0){
                                    temp.setZid(newMember.getId());
                                    String path = newMember.getPath();
                                    if (StringUtils.isEmpty(path)){
                                        path = "" + newMember.getId();
                                    }else{
                                        if (newMember.getGrade().intValue() == 2 || newMember.getGrade().intValue() == 3){
                                            // 防止大区经理跟底下的人串线
                                            path = "" + newMember.getId();
                                        }else{
                                            path = path + "," + newMember.getId();
                                        }
                                    }
                                    temp.setPath(path);
                                    update(temp);
                                    return true;
                                }
                            }
                        }
                    //}
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 更新用户名密码
     *
     * @param member
     */
    @Override
    public void updateUserNameAndPassword(Member member) {
        memberMapper.updateUserNameAndPassword(member);
    }

    /**
     * 根据token获取用户信息
     *
     * @param token
     * @return
     */
    @Override
    public Member findByToken(String token) {
        String []tokens = token.split("\\.");
        if (tokens.length != 3){
            return null;
        }
        // 签名
        String sign = tokens[2];
        String username = tokens[1];
        // 获取redis的token
        ValueOperations<Object, Object> operations=redisTemplate.opsForValue();
        String cacheToken = "" + operations.get(username);
        if (StringUtils.isEmpty(cacheToken)){
            return null;
        }
        String []cacheTokens = cacheToken.split("\\.");
        if (sign.equals(cacheTokens[2])){
            Member member = (Member) operations.get(cacheTokens[0]);
            // 刷新缓存
            //刷新redis的存活时间3天
            operations.set(cacheTokens[1],cacheToken,60*24*60*3, TimeUnit.SECONDS);
            operations.set(cacheTokens[0],member,60*24*60*3, TimeUnit.SECONDS);
            return member;
        }else{
            return null;
        }
    }

    @Override
    public List<MemberSuperiorVo> findSuperiorById(Integer id) {
        try {
            Member member = memberMapper.findById(id);
            if(Objects.isNull(member)){
                return null;
            }
            String path = new StringBuffer(member.getPath()).append(",").append(id).toString();
            String[] recommender = path.split(",");
            // 获取购买者及全部上级
            List<MemberSuperiorVo> list = memberMapper.findSuperiorListByIds(recommender);
            List<MemberSuperiorVo> sortList = new ArrayList<>();
            for (int i = recommender.length - 1; i >= 0; i--) {
                int currentId = Integer.parseInt(recommender[i]);
                sortList.add(
                        list.stream().filter(item->item.getId().equals(currentId)).collect(Collectors.toList()).get(0)
                );
            }
            return sortList;
        } catch (BusinessException b) {
            throw b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("添加分佣模式失败");
        }
    }

    @Override
    public List<MemberSuperiorVo> loadAll(MemberSuperiorVo memberSuperiorVo, Pager pager) {
        long countAll = memberMapper.count(memberSuperiorVo);
        pager = getPager(countAll,pager);
        return memberMapper.findSuperiorByPage(memberSuperiorVo, pager);
    }

    @Override
    public void clearOneQrCode(Integer id) {
        memberMapper.clearOneQrCode(id);
    }


    @Override
    public void clearAllQrCode() {
        memberMapper.clearAllQrCode();
    }

    /**
     * 更新用户的开放平台唯一ID
     *
     * @param user
     */
    @Override
    public void updateUnionId(Member user) {
        memberMapper.updateUnionId(user);
    }


    //AES加密username作为key
    public String getToken(Member member){
        String uuid = UUIDUtils.getUUID();
        //加密
        String username = AesUtils.encrypt(member.getUsername(),AesUtils.AESLOGINKEY);
        String hashCode = member.getUsername()+uuid;
        String sign = Sha256decode.getSHA256Str(hashCode);
        //给假的uuid
        String userToken = UUIDUtils.getUUID() + "."+username+"."+sign;
        String cacheToken = uuid + "."+username+"."+sign;
        ValueOperations<Object, Object> operations=redisTemplate.opsForValue();
        System.out.println("richUser = [" + username + "]");
        operations.set(username,cacheToken,60*60*24, TimeUnit.SECONDS);
        operations.set(uuid, member,60*60*24, TimeUnit.SECONDS);
        return  userToken;
    }



}


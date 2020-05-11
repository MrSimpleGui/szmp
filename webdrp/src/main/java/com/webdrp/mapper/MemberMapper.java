package com.webdrp.mapper;


import com.webdrp.common.BaseMapper;
import com.webdrp.common.Pager;
import com.webdrp.entity.Member;
import com.webdrp.entity.vo.MemberSuperiorVo;
import com.webdrp.entity.vo.MemberSysVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created by yuanming on 2018/8/2.
 */
@Component
public interface MemberMapper extends BaseMapper<Member>{

    Member findByUserName(String username);

    Member findByOpenId(String openId);

    Member findByOpenId1(String openId);
    //时间段内的用户数量
    int countByDate(@Param("start") String startDate, @Param("end") String endDate);

    void updateWallet(Member member);

    void setRichUserStatus(Member member);

    Long findMaxId();

    void updateAddress(Member member);

   void updateRichUserGrade(Member member);

   List<Member> findJianDian(Integer richUserId);

   void updateJianDian(Member member);

   void setAutoInAddId(Long addnum);

    void updateOpenId(Member member);

    void updateImageUrl(Member member);

    void register(Member member);

    /**
     * 123层用户查询
     * @param richUserId
     * @return
     */
    long countFirst(Integer richUserId);
    List<Member> findFirst(@Param("richUserId") Integer richUserId, @Param(value = "pager")  Pager pager);
    long countSecond(Integer richUserId);
    List<Member> findSecond(@Param("richUserId") Integer richUserId, @Param(value = "pager")  Pager pager);
    long countThird(Integer richUserId);
    List<Member> findThird(@Param("richUserId") Integer richUserId, @Param(value = "pager")  Pager pager);

    /**
     * 城市代理 统计人数，团队人数
     * @param member
     * @return
     */
    int findTeamUserCount(Member member);

    /**
     * 团队会员数量
     * @param member
     * @return
     */
    int findTeamVipUserCount(Member member);

    /**
     * 某人团队成交所有订单,累计成交
     * startTime 开始统计的时间
     */
    int findTeamUserOrderCount(@Param("entity") Member member, @Param("startTime")String startTime);

    /**
     * 团队订单，通过path统计
     * @param member
     * @return
     */
    int findTeamUserOrderCountV1(@Param("entity") Member member);

    /**
     * 一级订单
     * @param member
     * @return
     */
    int findTeamUserOrderCountFirst(@Param("entity") Member member);

    /**
     * 团队时间段内的成交量，用做今日成交
     * @param member
     * @param startTime
     * @param endTime
     * @return
     */
    int findTeamUserDayCount(@Param("entity") Member member, @Param("startTime")String startTime, @Param("endTime")String endTime);


    /**
     * 获取团队所有人
     * @param member
     * @return
     */
    List<Member> findTeamUser(Member member);

    /**
     * 获取所有城市代理
     * @return
     */
    List<Member> findAllCityUser();

    /**
     * 系统层查看所有的注册用户
     * @param member
     * @param pager
     * @return
     */
    List<MemberSysVo> findSysRichUser(@Param("entity") Member member, @Param(value = "pager")  Pager pager);

    List<Member> findTeamUserByPage(@Param("id") Integer id, @Param(value = "pager")  Pager pager);

    List<MemberSuperiorVo> findTeamMemberVoByPage(@Param("id") Integer id, @Param(value = "pager")  Pager pager);

    Member findRecommendById(Integer id);
    /**
     * 获取所有的上家
     * @param richUserIds
     * @return
     */
    List<Member> findByIds(String[] richUserIds);

    List<MemberSuperiorVo> findSuperiorListByIds(String[] richUserIds);

    List<Member> findSuperiorById(Integer id);

    /**
     * 更新用户名密码
     * @param item
     */
    void updateUserNameAndPassword(Member item);

    List<MemberSuperiorVo> findSuperiorByPage(@Param("entity") MemberSuperiorVo memberSuperiorVo,
                                              @Param("pager") Pager pager);

    String findNicknameById(Integer id);

    void clearOneQrCode(Integer id);

    void clearAllQrCode();

    /**
     * 更新开放平台ID
     * @param user
     */
    void updateUnionId(Member user);

    void updateNickname(Member member);
}

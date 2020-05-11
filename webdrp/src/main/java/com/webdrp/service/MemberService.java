package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.entity.Member;
import com.webdrp.entity.vo.MemberSuperiorVo;
import com.webdrp.entity.vo.MemberSysVo;

import java.util.List;

/**
 * Created by yuanming on 2018/8/2.
 */

public interface MemberService extends BaseService<Member>{

    String login(Member member);

    Member findByOpenId(String openId);
    //是授权的公众号
    Member findByOpenId1(String openId);

    int countByDate(String startDate,String endDate);

    void setRichUserStatus(Member member);

    Long findMaxId();

    void updateAddress(Member member);

    void setAutoCreate(Integer addnum);

    void addUserWallet(Integer richUserId, Integer money);

    void updateOpenId(Member member);

    void updateImageUrl(Member member);

    void register(Member member);

    Member findByUserName(String username);

    /**
     * 第一层
     * @param richUserId
     * @return
     */
    List<Member> findFirst(Integer richUserId, Pager pager);


    /**
     * 第二层
     * @param richUserId
     * @return
     */
    List<Member> findSecond(Integer richUserId, Pager pager);

    /**
     * 第三层
     * @param richUserId
     * @return
     */
    List<Member> findThird(Integer richUserId, Pager pager);

    /**
     * 计数
     * @param richUserId
     * @return
     */
    long countFirst(Integer richUserId);
    long countSecond(Integer richUserId);
    long countThird(Integer richUserId);



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
     */
    int findTeamUserOrderCount(Member member, String startTime);

    /**
     * 统计某人成交所有订单
     * @param member
     * @return
     */
    int findTeamUserOrderCountV1(Member member);

    /**
     * 一级级差订单
     * @param member
     * @return
     */
    int findTeamUserOrderCountFirst(Member member);

    /**
     * 团队时间段内的成交量，用做今日成交
     * @param member
     * @param startTime
     * @param endTime
     * @return
     */
    int findTeamUserDayCount(Member member, String startTime, String endTime);

    List<Member> findTeamUser(Member member);

    List<Member> findAllCityUser();

    List<MemberSysVo> findSysRichUser(Member member, Pager pager);

    /**
     * 分页
     * @param id
     * @param pager
     * @return
     */
    List<Member> findTeamUserByPage(Integer id, Pager pager);

    List<MemberSuperiorVo> findTeamMemberVoByPage(Integer id, Pager pager);

    /**
     * 更新上家ID
     * @param temp
     * @param newTopId
     */
    boolean updateTopId(Member temp, Object newTopId);

    /**
     * 更新用户名密码
     * @param member
     */
    void updateUserNameAndPassword(Member member);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    Member findByToken(String token);

    List<MemberSuperiorVo> findSuperiorById(Integer id);

    List<MemberSuperiorVo> loadAll(MemberSuperiorVo memberSuperiorVo, Pager pager);

    void clearOneQrCode(Integer id);

    void clearAllQrCode();

    /**
     * 更新用户的开放平台唯一ID
     * @param user
     */
    void updateUnionId(Member user);
}

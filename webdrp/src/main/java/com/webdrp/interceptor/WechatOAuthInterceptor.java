package com.webdrp.interceptor;

import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.mp.api.OauthApi;
import com.foxinmy.weixin4j.mp.model.OauthToken;
import com.webdrp.annotation.LoginStatus;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.PBKey;
import com.webdrp.entity.Member;
import com.webdrp.err.BusinessException;
import com.webdrp.err.LoginTimeoutRuntimeException;
import com.webdrp.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;

/**
 * 拦截微信请求，用于微信用户认证，未关注时的注册处理
 * 第一个拦截器，用于授权，获取头像
 * Created by hello on 2017/7/5.
 */

public class WechatOAuthInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("oauthApi1")   //两个公众号情况这样查找
    private OauthApi oauthApi;

    @Autowired
    MemberService memberService;

    @Value("${wechat.auth1.url}")
    private  String RedirectUrl;

    @Value("${wechat.develop}")
    private boolean wechatDebug;

    @Value("${server.servlet.context-path}")
    String contextPath;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(o instanceof HandlerMethod)) {
            logger.debug("映射不是一个方法");
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) o;
        Method method = handlerMethod.getMethod();

        logger.debug("url="+RedirectUrl);
        HttpSession session = httpServletRequest.getSession();


        logger.debug("session="+session.getId());
        Object loginState = session.getAttribute(LoginStatus.State);
        System.out.println("进来拦截器了WechatOAuthInterceptor loginState="+loginState);
        // 判断接口是否需要登录
        WechatRequest wechatRequest = method.getAnnotation(WechatRequest.class);


//      TODO  测试使用 ----------------------------- start ---------------------------------
        if (wechatDebug){
            if (wechatRequest !=null){
                Member member = memberService.findOne(99998);
                if (Objects.isNull(member)){
                    throw new BusinessException("没有用户数据");
                }
                session.setAttribute(PBKey.PBLOGINKEY, member);
                return true;
            }
        }
        //假如是APP并且需要用户数据。
        if (Objects.nonNull(wechatRequest)){
            String platForm = httpServletRequest.getHeader(PBKey.PLATFORM);
            if (!Objects.isNull(platForm)){
                // 假如是APP，走APP认证
                if ("APP".equals(platForm)){
                    return appUserForm(httpServletRequest,httpServletResponse);
                }
            }
        }

//   TODO    结束测试 ----------------------------- end ---------------------------------
        //假如有注解需要登录
        if (wechatRequest != null) {
            //登录状态  假如有
            if (loginState != null){
                try {
                    int loginState1 = (int) loginState;
                    switch (loginState1){
                        // 已登录，放行
                        case LoginStatus.LoggedIn:
                            Member member = (Member)session.getAttribute(PBKey.PBLOGINKEY);
                            if (Objects.isNull(member)){
                                logger.error("更新的时候获取用户信息为空，进行重新重定向！");
                                onWechatError(session,httpServletResponse);
                            }
                            if (member.getStatus()==1){
                                httpServletResponse.sendRedirect("http://m.baidu.com");
                                return false;
                            }else{
                                return true;
                            }
                        // 准备进行登陆
                        case LoginStatus.RedirectToDoLogin:
                            String baseCode = ""+ httpServletRequest.getParameter("code");
                            if (baseCode.isEmpty()||baseCode=="null"){
                                //假如code 为空
                                logger.debug("RedirectToDoLogin 假如code 为空");
                                session.removeAttribute(LoginStatus.State);
                                String url =  RedirectUrl + httpServletRequest.getRequestURI();

                                httpServletResponse.sendRedirect(url);
                                logger.debug("RedirectToDoLogin 假如code 为空 redirect");
                                return false;
                            }
                            return this.onRedirectToDoLogin(session,httpServletRequest,httpServletResponse,baseCode);
                        /**
                         * 注册 获取详细信息
                         */
                        case LoginStatus.RedirectToDoRegister:
                            String infoCode = ""+httpServletRequest.getParameter("code");
                            if (infoCode.isEmpty()||infoCode=="null"){
                                //假如code 为空
                                logger.debug("RedirectToDoRegister 假如code 为空");
                                session.removeAttribute(LoginStatus.State);
                                String url =  RedirectUrl + httpServletRequest.getRequestURI();
                                httpServletResponse.sendRedirect(url);
                                logger.debug("RedirectToDoRegister 假如code 为空 redirect");
                                return false;
                            }
                            return this.onRedirectToDoRegister(session,httpServletRequest,httpServletResponse,infoCode);
                        case LoginStatus.UpdateUnionId:
                            String infoCodeUpdate = ""+httpServletRequest.getParameter("code");
                            if (infoCodeUpdate.isEmpty()||infoCodeUpdate=="null"){
                                //假如code 为空
                                logger.debug("RedirectToDoRegister 假如code 为空");
                                session.removeAttribute(LoginStatus.State);
                                String url =  RedirectUrl + httpServletRequest.getRequestURI();
                                httpServletResponse.sendRedirect(url);
                                logger.debug("RedirectToDoRegister 假如code 为空 redirect");
                                return false;
                            }
                            return this.onRedirectToDoUpdateUnionId(session,httpServletRequest,httpServletResponse,infoCodeUpdate);

                    }
                }
                catch (Exception exception){
                    exception.printStackTrace();
                    // 记录错误日志
                    logger.error("");
                    onWechatError(session,httpServletResponse);
                }
            }else{

                //假如不是微信，就进行跳转到登录页面
                String userAgent = httpServletRequest.getHeader("user-agent");
                logger.debug("[preHandle]客户端浏览区引擎="+userAgent);
                if (!userAgent.contains("MicroMessenger")){
                    httpServletResponse.sendRedirect(contextPath +"/pb/app/login");
                    return false;
                }

                try {
                    //拼接重定向Url
                    String url =  RedirectUrl + httpServletRequest.getRequestURI();
                    logger.debug("【preHandle】httpServletRequest.getRequestURI="+httpServletRequest.getRequestURI());
                    //增加参数重定向,只能增加在session
                    logger.debug("【preHandle】url="+url);
                    String queryString = getQueryStringRemoveCode(httpServletRequest);
                    logger.debug("【preHandle】queryString = "+queryString);
                    if (Objects.isNull(queryString)){

                    }else{
                        Integer tips = 0;
                        try {
                            tips =  Integer.parseInt(httpServletRequest.getParameter("tips")) ;
                            logger.debug("【preHandle】sessionId="+session.getId());
                        }catch (Exception e){
                            e.printStackTrace();
                            logger.error("【preHandle】获取上家编号异常！");
                            logger.debug("【preHandle】sessionId="+session.getId());
                            logger.warn("【preHandle】尝试从session中获取！");
                        }
                        if (Objects.isNull(tips)){
                            logger.debug("【preHandle】tips参数为空 sessionId = "+session.getId());
                            tips = 0;
                        }
                        logger.debug("【preHandle】获取到的上家ID为="+tips);
                        //设置参数，用户Id
                        session.setAttribute("tips",tips);
                    }
                    //获取code
                    String redirectUrl = oauthApi.getUserAuthorizationURL(url,"ok","snsapi_base");
                    // 跳转处理登陆
                    // https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf9a770d1d75a5309&redirect_uri=yuanye.xiaoye0724.cn%2Fwechat%2FbusIndex&response_type=code&scope=snsapi_userinfo&state=1234#wechat_redirect
                    session.setAttribute(LoginStatus.State,LoginStatus.RedirectToDoLogin);
                    logger.debug("【preHandle】重定向的141url="+redirectUrl);
                    httpServletResponse.sendRedirect(redirectUrl);
                    logger.debug("【preHandle】sendredirect");
                }catch (Exception exception){
                    // 记录错误日志
                    logger.error("微信错误141 start");
                    exception.printStackTrace();
                    logger.error("微信错误141 end");
                    onWechatError(session,httpServletResponse);
                }
            }
        }//微信注解不存在
        else{
            return true;
        }
        return false;
    }

    private boolean onRedirectToDoUpdateUnionId(HttpSession session, HttpServletRequest request, HttpServletResponse httpServletResponse, String code) throws IOException, WeixinException {
        if (code != null&&!code.isEmpty()){
            OauthToken token = oauthApi.getAuthorizationToken(code);
            com.foxinmy.weixin4j.mp.model.User wechatUserInfo = null;
            try {
                wechatUserInfo = oauthApi.getAuthorizationUser(token);
            }catch (Exception ex){
                ex.printStackTrace();
                session.removeAttribute(LoginStatus.State);
                removeStatusAndSendRedirectToThis(session,request,httpServletResponse);
                return false;
            }

            if (Objects.nonNull(wechatUserInfo)){
                Member wechatUser1 = null;
                Member wechatUser = new Member();
                //查询用户是否在数据库中存在
                wechatUser1 = memberService.findByOpenId1(wechatUserInfo.getOpenId());
                if (Objects.nonNull(wechatUser1)){
                    wechatUser1.setUnionId(wechatUserInfo.getUnionId());
                    memberService.updateUnionId(wechatUser1);
                    session.setAttribute(PBKey.PBLOGINKEY,wechatUser1);
                    session.setAttribute(LoginStatus.State,LoginStatus.LoggedIn);
                    return true;
                }else{
                    // 无 -> 进行注册
                    session.setAttribute(LoginStatus.State,LoginStatus.RedirectToDoRegister);
                    String url =  RedirectUrl+request.getRequestURI();
                    logger.debug("【onRedirectToDoUpdateUnionId】转发注册url = "+url);
                    //增加参数重定向
                    String redireecUrl = oauthApi.getUserAuthorizationURL(url,"","snsapi_userinfo");
                    logger.debug("【onRedirectToDoUpdateUnionId】转发注册 url = "+redireecUrl);
                    httpServletResponse.sendRedirect(redireecUrl);
                }
            }
        }
        // 记录错误日志
        logger.error("【onRedirectToDoUpdateUnionId】转发注册 错误");
        onWechatError(session,httpServletResponse);
        return false;
    }

    /**
     * APP 登录处理
     * @param request
     * @param response
     * @return
     */
    private boolean appUserForm(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(PBKey.AUTH);
        if (Objects.isNull(token)){
            throw new BusinessException("token参数错误");
        }
        Member member = memberService.findByToken(token);
        if (Objects.isNull(member)){
            throw new LoginTimeoutRuntimeException("登录超时");
        }
        request.getSession().setAttribute(PBKey.PBLOGINKEY,member);
        return true;
    }

    /**
     * 重定向前去掉之前的code
     * @param httpServletRequest
     * @return
     */
    public String getQueryStringRemoveCode(HttpServletRequest httpServletRequest){
        Enumeration<String> list = httpServletRequest.getParameterNames();
        String queryString = "";
        for (;list.hasMoreElements();){
            String itemName =  list.nextElement();
            if (!itemName.equals("code")){
                queryString = queryString + "&" + itemName + "=" + httpServletRequest.getParameter(itemName);
            }
        }
        if (queryString.length() > 1){
            queryString = queryString.substring(1);
        }
        return queryString;
    }

    /**
     * 出错重定向回当前
     * @param session
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws IOException
     */
    public void removeStatusAndSendRedirectToThis(HttpSession session, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String url =  RedirectUrl + httpServletRequest.getRequestURI();
        String queryString = getQueryStringRemoveCode(httpServletRequest);
        if (queryString.length() > 0){
            url = url + "?" + queryString;
        }
        httpServletResponse.sendRedirect(url);
    }


    /**
     * 处理注册请求  这里拿到了code  这里进行snsapi_userinfo级别，获取到详细用户数据
     * @param session
     * @param httpServletResponse
     * @param code
     * @return
     * @throws WeixinException
     * @throws IOException
     */
    private boolean onRedirectToDoRegister(HttpSession session, HttpServletRequest request,HttpServletResponse httpServletResponse, String code)
            throws WeixinException, IOException {
        if (code != null&&!code.isEmpty()){
            logger.debug("【onRedirectToDoRegister】onRedirectToDoRegister => "+ code);
            OauthToken token = oauthApi.getAuthorizationToken(code);
            logger.debug("【onRedirectToDoRegister】onRedirectToDoRegister  token 175=>"+token.toString());
            com.foxinmy.weixin4j.mp.model.User wechatUserInfo = null;
            try {
                wechatUserInfo = oauthApi.getAuthorizationUser(token);
            }catch (Exception ex){
                ex.printStackTrace();
                logger.debug("【onRedirectToDoRegister】onRedirectToDoRegister => 获取微信用户信息异常");
                session.removeAttribute(LoginStatus.State);
                logger.debug("【onRedirectToDoRegister】onRedirectToDoRegister => 获取微信用户清除session");
                removeStatusAndSendRedirectToThis(session,request,httpServletResponse);
                return false;
            }

            if (wechatUserInfo != null){
                Member wechatUser1 = null;
                Member wechatUser = new Member();
                //查询用户是否在数据库中存在
                wechatUser1 = memberService.findByOpenId1(wechatUserInfo.getOpenId());
                if (Objects.isNull(wechatUser1)){
                    try {
                        logger.debug("【onRedirectToDoRegister】获取到的信息",wechatUserInfo.toString());
                        //尝试获取用户的上家
                        Integer zid = 0;
                        try {
                            zid = Integer.parseInt(""+session.getAttribute("tips")) ;
                        }catch (Exception e){
                            e.printStackTrace();
                            logger.error("【onRedirectToDoRegister】注册 获取上家信息异常");
                            zid = 0;

                        }
//                        if (zid.intValue()==0){
//                            onWechatErrorNotFoundLastUser(session,httpServletResponse);
//                            return false;
//                        }
                        //增加上家检测
                        Member top = memberService.findOne(zid);
                        if (Objects.nonNull(top)){
                            wechatUser.setZid(zid);
                            wechatUser.setCityId(top.getCityId());
                            String path = top.getPath();
                            // 处理逗号问题
                            if (StringUtils.isEmpty(path)){
                                path = "" + top.getId();
                            }else{
                                path = path + "," + top.getId();
                            }
                            wechatUser.setPath(path);
                        }else{
                            onWechatErrorNotFoundLastUserSystem(session,httpServletResponse);
                            return false;
//                            wechatUser.setZid(0);
//                            wechatUser.setCityId(1);
                        }
                        //信息填写
                        wechatUser.setCity(wechatUserInfo.getCity());
                        wechatUser.setCountry(wechatUserInfo.getCountry());
                        wechatUser.setProvince(wechatUserInfo.getProvince());
                        wechatUser.setHeadImg(wechatUserInfo.getHeadimgurl());
                        wechatUser.setNickname(wechatUserInfo.getNickName());
                        wechatUser.setSex(wechatUserInfo.getGender());
                        wechatUser.setOpenid1(wechatUserInfo.getOpenId());
                        wechatUser.setOpenid(wechatUserInfo.getOpenId());
                        // 开放平台唯一ID
                        wechatUser.setUnionId(wechatUserInfo.getUnionId());
                        wechatUser.setOpenid1wx("小野集市");
                        //设置是否是关注
                        int isSubScribe = wechatUserInfo.isSubscribe()==true?1:0;
                        wechatUser.setIsSubscribe(isSubScribe);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    try {
                        memberService.add(wechatUser);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("【onRedirectToDoRegister】添加微信用户数据异常");
//                        onWechatError(session,httpServletResponse);
                        removeStatusAndSendRedirectToThis(session,request,httpServletResponse);
                        return false;
                    }
                    session.setAttribute(PBKey.PBLOGINKEY,wechatUser);
                }else{
                    session.setAttribute(PBKey.PBLOGINKEY,wechatUser1);
                }
                session.setAttribute(LoginStatus.State,LoginStatus.LoggedIn);
                return true;
            }else{
                logger.debug("【onRedirectToDoRegister】onRedirectToDoRegister => 205 获取微信用户信息为空");
                //TODO是否以游客的身份进入
                session.removeAttribute(LoginStatus.State);
                logger.debug("【onRedirectToDoRegister】onRedirectToDoRegister => 获取微信用户清除session");
            }
        }
        // 记录错误日志
        logger.error("【onRedirectToDoRegister】");
        onWechatError(session,httpServletResponse);
        return false;
    }

    /**
     *  // 处理登陆请求   这里只进行snsapi_base级别
     * @param session
     * @param httpServletRequest
     * @param httpServletResponse
     * @param code
     * @return
     * @throws WeixinException
     * @throws IOException
     */
    private boolean onRedirectToDoLogin(HttpSession session, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String code)
            throws WeixinException, IOException {

        if (code != null){
            OauthToken token = oauthApi.getAuthorizationToken(code);
            logger.debug("【onRedirectToDoLogin】token 信息 230 == > "+token.toString());
            String openId = token.getOpenId();
            String unionId = token.getUnionId();
            // 验证数据库是否有此用户
            Member user = memberService.findByOpenId1(openId);
            if (user != null){

//                try {
//                    // 前置授权切换上家
//                    richUserService.updateTopId(user,session.getAttribute("tips"));
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }

                // 更新开放平台ID
                if (StringUtils.isEmpty(user.getUnionId())){
                    if (!StringUtils.isEmpty(unionId)){
                        user.setUnionId(unionId);
                        memberService.updateUnionId(user);
                    }else{
                        // 无 -> 进行更新UnionID
                        session.setAttribute(LoginStatus.State,LoginStatus.UpdateUnionId);
                        String url =  RedirectUrl+httpServletRequest.getRequestURI();
                        //增加参数重定向
                        String redireecUrl = oauthApi.getUserAuthorizationURL(url,"","snsapi_userinfo");
                        httpServletResponse.sendRedirect(redireecUrl);
                        return false;
                    }
                }

                // 有 -> 标记登陆
                session.setAttribute(LoginStatus.State,LoginStatus.LoggedIn);
                session.setAttribute(PBKey.PBLOGINKEY,user);
                return true;
            }
            else{
                // 无 -> 进行注册
                session.setAttribute(LoginStatus.State,LoginStatus.RedirectToDoRegister);
                String url =  RedirectUrl+httpServletRequest.getRequestURI();
                logger.debug("【onRedirectToDoLogin】注册url = "+url);
                //增加参数重定向

                String redireecUrl = oauthApi.getUserAuthorizationURL(url,"","snsapi_userinfo");
                logger.debug("【onRedirectToDoLogin】redireecUrl 237 url = "+redireecUrl);
                httpServletResponse.sendRedirect(redireecUrl);
            }
        }
        else{
            // 记录错误日志
            logger.error(" 记录错误日志");
            onWechatError(session,httpServletResponse);
        }
        return false;
    }

    // TODO:处理微信异常
    private void onWechatError(HttpSession session, HttpServletResponse response) throws IOException {
        // 清空登陆状态
        session.removeAttribute(LoginStatus.State);
        logger.error("【onWechatError】当前sessionID = "+session.getId());
        // 返回错误页面
        response.sendRedirect(contextPath+"/wechat/h5/personal");
    }


    private void onWechatErrorNotFoundLastUser(HttpSession session, HttpServletResponse response) throws IOException {
        // 清空登陆状态
        session.removeAttribute(LoginStatus.State);
        session.removeAttribute("tips");
        logger.error("【onWechatErrorNotFoundLastUser】当前sessionID = "+session.getId());
        // 返回错误页面
        response.sendRedirect(contextPath+"/err/wechat/notfound");
    }

    private void onWechatErrorNotFoundLastUserSystem(HttpSession session, HttpServletResponse response) throws IOException {
        // 清空登陆状态
        session.removeAttribute(LoginStatus.State);
        session.removeAttribute("tips");
        logger.error("【onWechatErrorNotFoundLastUser】当前sessionID = "+session.getId());
        // 返回错误页面
        response.sendRedirect(contextPath+"/wechat/v1/systemBusy");
    }





    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


}

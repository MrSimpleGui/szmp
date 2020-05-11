package com.webdrp.interceptor;

/**
 * 支付拦截器，用于支付目录的openID 获取
 * Created by hello on 2017/7/5.
 */

//public class WechatOAuthInterceptor1 implements HandlerInterceptor {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    @Qualifier("oauthApi")   //两个公众号情况这样查找
//    private OauthApi oauthApi;
//
//    @Autowired
//    RichUserService richUserService;
//
//    @Value("${wechat.auth.url}")
//    private  String RedirectUrl;
//
//    @Value("${wechat.develop}")
//    private boolean wechatDebug;
//
//
//    @Override
//    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        // 如果不是映射到方法直接通过
//        if (!(o instanceof HandlerMethod)) {
//            logger.debug("[支付拦截preHandle]映射不是一个方法");
//            return true;
//        }
//        HandlerMethod handlerMethod = (HandlerMethod) o;
//        Method method = handlerMethod.getMethod();
//
//        logger.debug("[支付拦截preHandle]url="+RedirectUrl);
//        HttpSession session = httpServletRequest.getSession();
//
//        logger.debug("[支付拦截preHandle]session="+session.getId());
//        Object loginState = session.getAttribute(PayLoginStatus.State);
//        System.out.println("[支付拦截preHandle] 进来拦截器loginState="+loginState);
//        // 判断接口是否需要登录
//        WechatRequest1 wechatRequest = method.getAnnotation(WechatRequest1.class);
//
//
//
//
////      TODO  测试使用 ----------------------------- start ---------------------------------
//        if (wechatDebug) {
//            if (wechatRequest !=null){
//                RichUser richUser = richUserService.findOne(10000);
//                if (Objects.isNull(richUser)){
//                    throw new BusinessException("没有用户数据");
//                }
//                session.setAttribute(PBKey.PBLOGINKEY,richUser);
//                return true;
//            }
//        }
////      TODO    结束测试 ----------------------------- end ---------------------------------
//
//
//        //假如不是微信，就进行跳转到登录页面
//        String userAgent = httpServletRequest.getHeader("user-agent");
//        logger.debug("[preHandle]客户端浏览区引擎="+userAgent);
//        if (!userAgent.contains("MicroMessenger")){
//            httpServletResponse.sendRedirect("/pb/app/login");
//            return false;
//        }
//
//        //假如有注解需要登录
//        if (wechatRequest != null) {
//            //登录状态  假如有
//            if (loginState != null){
//                try {
//                    int loginState1 = (int) loginState;
//                    switch (loginState1){
//                        // 已登录，放行
//                        case PayLoginStatus.LoggedIn:
//                            RichUser richUser = (RichUser)session.getAttribute(PBKey.PBLOGINKEY);
//                            if (richUser.getStatus()==1){
//                                httpServletResponse.sendRedirect("http://m.baidu.com");
//                                return false;
//                            }else{
//                                return true;
//                            }
//
//                        // 准备进行登陆
//                        case PayLoginStatus.RedirectToDoLogin:
//                            String baseCode = ""+ httpServletRequest.getParameter("code");
//                            if (baseCode.isEmpty()){
//                                //假如code 为空
//                                logger.debug("RedirectToDoLogin 假如code 为空");
//                                session.removeAttribute(PayLoginStatus.State);
//                                String url =  RedirectUrl + httpServletRequest.getRequestURI();
//                                httpServletResponse.sendRedirect(url);
//                                logger.debug("RedirectToDoLogin 假如code 为空 redirect");
//                                return false;
//                            }
//                            return this.onRedirectToDoLogin(session,httpServletRequest,httpServletResponse,baseCode);
//                    }
//                }
//                catch (Exception exception){
//                    exception.printStackTrace();
//                    // 记录错误日志
//                    logger.error("");
//                    onWechatError(session,httpServletResponse);
//                }
//            }else{
//                try {
//                    //拼接重定向Url 并且去掉code
//                    //版本OK，首次会出现bug
//                    String url =  RedirectUrl + httpServletRequest.getRequestURI();
////                    String queryString = httpServletRequest.getQueryString();
//////                    String url = httpServletRequest.getRequestURL().toString();
////                    if (!StringUtils.isEmpty(queryString)){
////                        url = url + "?"+queryString;
////                    }
//                    String queryString = getQueryStringRemoveCode(httpServletRequest);
//                    if (queryString.length() > 0){
//                        url = url + "?" + queryString;
//                    }
//
//
//                    RichUser richUser = (RichUser)session.getAttribute(PBKey.PBLOGINKEY);
//                    if (Objects.nonNull(richUser)){
//                        logger.debug("[preHandle] 重定向前，用户数据不为空,");
//                        logger.debug(richUser.toString());
//                        session.setAttribute(PBKey.PBLOGINKEY,richUser);
//                    }
//
//                    //获取code
//                    String redirectUrl = oauthApi.getUserAuthorizationURL(url,"ok","snsapi_base");
//                    // 跳转处理登陆
//                    session.setAttribute(PayLoginStatus.State,PayLoginStatus.RedirectToDoLogin);
//                    logger.debug("【preHandle】重定向的141url="+redirectUrl);
//                    httpServletResponse.sendRedirect(redirectUrl);
//                    logger.debug("【preHandle】sendredirect");
//                }
//                catch (Exception exception){
//                    // 记录错误日志
//                    logger.error("微信错误141 start");
//                    exception.printStackTrace();
//                    logger.error("微信错误141 end");
//                    onWechatError(session,httpServletResponse);
//                }
//            }
//        }//微信注解不存在
//        else{
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 重定向前去掉之前的code
//     * @param httpServletRequest
//     * @return
//     */
//    public String getQueryStringRemoveCode(HttpServletRequest httpServletRequest){
//        Enumeration<String> list = httpServletRequest.getParameterNames();
//        String queryString = "";
//        for (;list.hasMoreElements();){
//            String itemName =  list.nextElement();
//            if (!itemName.equals("code")){
//                queryString = queryString + "&" + itemName + "=" + httpServletRequest.getParameter(itemName);
//            }
//        }
//        if (queryString.length() > 1){
//            queryString = queryString.substring(1);
//        }
//        return queryString;
//    }
//
//
//    /**
//     * 出错重定向回当前
//     * @param session
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @throws IOException
//     */
//    public void removeStatusAndSendRedirectToThis(HttpSession session, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
//        String url =  RedirectUrl + httpServletRequest.getRequestURI();
//        String queryString = getQueryStringRemoveCode(httpServletRequest);
//        if (queryString.length() > 0){
//            url = url + "?" + queryString;
//        }
//        httpServletResponse.sendRedirect(url);
//    }
//
//
//
//    /**
//     *  // 处理登陆请求   这里只进行snsapi_base级别
//     * @param session
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @param code
//     * @return
//     * @throws IOException
//     */
//    private boolean onRedirectToDoLogin(HttpSession session, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String code)
//            throws IOException {
//        logger.debug("[支付 onRedirectToDoLogin]继续支付获取的的Code为code = "+code);
//        if (code != null && !code.isEmpty()){
//            OauthToken token = null;
//            try {
//                token = oauthApi.getAuthorizationToken(code);
//            } catch (WeixinException e) {
//                session.removeAttribute(PayLoginStatus.State);
//                e.printStackTrace();
//                removeStatusAndSendRedirectToThis(session,httpServletRequest,httpServletResponse);
//                return false;
//            }
//            logger.debug("【支付 onRedirectToDoLogin】token 信息 230 == > "+token.toString());
//            String openId = token.getOpenId();
//            // 验证数据库是否有此用户
//            RichUser user = richUserService.findByOpenId(openId);
//
//            if (Objects.isNull(user)){
//                RichUser temp = (RichUser)session.getAttribute(PBKey.PBLOGINKEY);
//                if (Objects.isNull(temp)){
//                    logger.debug("session中用户为空，返回失败");
//                    return false;
//                }else{
//                    temp.setOpenid(openId);
//                    temp.setOpenidwx("支付的openID O斗");
//                    richUserService.update(temp);
//                    session.setAttribute(PBKey.PBLOGINKEY,temp);
//                    logger.debug("session中用户不为空，返回成功");
//                    session.setAttribute(PayLoginStatus.State,PayLoginStatus.LoggedIn);
//                    return true;
//                }
//            }else{
//                logger.debug("【支付 onRedirectToDoLogin】获取的用户信息为 "+user.toString());
//                logger.debug("【支付 onRedirectToDoLogin】session中用户不为空，返回成功");
//                session.setAttribute(PBKey.PBLOGINKEY,user);
//                session.setAttribute(PayLoginStatus.State,PayLoginStatus.LoggedIn);
//                return true;
//            }
//        } else{
//            // 记录错误日志
//            logger.error(" 记录错误日志");
//            onWechatError(session,httpServletResponse);
//
//        }
//        return false;
//    }
//
//    // TODO:处理微信异常
//    private void onWechatError(HttpSession session, HttpServletResponse response) throws IOException {
//        // 清空登陆状态
//        session.removeAttribute(PayLoginStatus.State);
//        logger.error("onWechatError 微信支付拦截器处理异常");
//        // 返回错误页面
//        response.sendRedirect("/wechat/v1/indexDescription");
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//
//    }
//
//
//}

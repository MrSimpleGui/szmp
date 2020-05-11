package com.webdrp.log.aop;

import com.alibaba.fastjson.JSON;
import com.webdrp.common.PBKey;
import com.webdrp.entity.Member;
import com.webdrp.service.impl.LogService;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.log.annnotion.SystemServiceLog;
import com.webdrp.entity.Log;
import com.webdrp.util.CusAccessObjectUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 下午1:34 2018/2/18
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Aspect
@Component
public class SystemLogAspect {

    private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);

    private static final ThreadLocal<Date> beginTimeThreadLocal = new NamedThreadLocal<Date>("ThreadLocal beginTime");
    private static final ThreadLocal<Log> logThreadLocal = new NamedThreadLocal<Log>("ThreadLocal log");
    private static final ThreadLocal<Member> currentUser=new NamedThreadLocal<>("ThreadLocal user");

    @Autowired
    LogService logService;

    @Autowired(required = false)
    HttpServletRequest request;

    /**
     * spring 线程池
     */
    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    /**
     * Controller层切点 注解拦截SystemControllerLog
     */
    @Pointcut("@annotation(com.webdrp.log.annnotion.SystemControllerLog)")
    public void controllerAspect(){}

    /**
     * 前置通知 用于拦截Controller层记录用户的操作的开始时间
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        Date beginTime = new Date();
        //线程绑定变量（该数据只有当前请求的线程可见）
        beginTimeThreadLocal.set(beginTime);
        //读取session中的用户
        HttpSession session = request.getSession();
        //获取微信用户
        Member wechatUser = (Member) session.getAttribute(PBKey.PBLOGINKEY);
        currentUser.set(wechatUser);
    }
    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     * @param joinPoint 切点
     */
    @SuppressWarnings("unchecked")
    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
        // logger.error("后置通知1");
        Member user = currentUser.get();
//        if(user !=null){
            String title="";
            //日志类型(info:入库,error:错误)
            String type="info";
            //请求的IP  不受转发影响
            String remoteAddr= CusAccessObjectUtil.getIpAddress(request);
            //请求的Uri
            String requestUri=request.getRequestURI();
            //请求的方法类型(post/get)
            String method=request.getMethod();
            //请求提交的参数
            Map<String,String[]> params=request.getParameterMap();

            try {
                title=getControllerMethodDescription2(joinPoint);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 打印JVM信息。
            //得到线程绑定的局部变量（开始时间）
            long beginTime = beginTimeThreadLocal.get().getTime();
            //2、结束时间
            long endTime = System.currentTimeMillis();
            Log log=new Log();
            log.setTitle(title);
            log.setType(type);
            log.setRemoteAddr(remoteAddr);
            log.setRequestUri(requestUri);
            log.setMethod(method);
            log.setParams(JSON.toJSONString(params));

            if (Objects.nonNull(user)){
                log.setUserId(user.getId());
            }

            Date operateDate=beginTimeThreadLocal.get();
            log.setOperateDate(operateDate);
            log.setTimeout(""+(endTime - beginTime));
            //3.再优化:通过线程池来执行日志保存
            taskExecutor.execute(new SaveLogThread(log, logService));
            logThreadLocal.set(log);
//        }

    }

    /**
     *  异常通知 记录操作报错日志
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = logThreadLocal.get();
        log.setType("error");
        log.setException(e.toString());
    }

    /**
     * 获取注解中对方法的描述信息 用于service层注解
     * @param joinPoint 切点
     * @return discription
     */
    public static String getServiceMthodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemServiceLog serviceLog = method
                .getAnnotation(SystemServiceLog.class);
        String discription = serviceLog.description();
        return discription;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return discription
     */
    public static String getControllerMethodDescription2(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemControllerLog controllerLog = method
                .getAnnotation(SystemControllerLog.class);
        String discription = controllerLog.description();
        return discription;
    }

    /**
     * 保存日志线程
     */
    private static class SaveLogThread implements Runnable {
        private Log log;
        private LogService logService;

        public SaveLogThread(Log log, LogService logService) {
            this.log = log;
            this.logService = logService;
        }
        @Override
        public void run() {
            // logger.error("创建日志");
            logService.add(log);
        }
    }

    /**
     * 日志更新线程
     */
    private static class UpdateLogThread extends Thread {
        private Log log;
        private LogService logService;

        public UpdateLogThread(Log log, LogService logService) {
            super(UpdateLogThread.class.getSimpleName());
            this.log = log;
            this.logService = logService;
        }

        @Override
        public void run() {
            logger.error("打印更新日志");
            this.logService.update(log);
        }

    }
}

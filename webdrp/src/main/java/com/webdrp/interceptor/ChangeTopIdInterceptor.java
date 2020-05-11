package com.webdrp.interceptor;

import com.webdrp.common.PBKey;
import com.webdrp.entity.Member;
import com.webdrp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 00:34 2020-04-25
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public class ChangeTopIdInterceptor implements HandlerInterceptor {

    @Autowired
    MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        System.out.println("ChangeTopIdInterceptor");

        Member member = (Member)request.getSession().getAttribute(PBKey.PBLOGINKEY);
        if (Objects.nonNull(member)){

            member = memberService.findOne(member.getId());
            // 假如是粉丝
            if (member.getGrade().intValue() <= 0){
                String tips = request.getParameter("tips");
                if (!StringUtils.isEmpty(tips)){
                    try {
                        int topId = Integer.parseInt(tips);
                        if (topId >= 0){
                           Member topMember = memberService.findOne(topId);
                           if (Objects.nonNull(topMember)){
                               if (topMember.getGrade().intValue() > 0){
                                   String path = topMember.getPath();
                                   if (StringUtils.isEmpty(path)){
                                       path = "" + topMember.getId();
                                   }else{
                                       path = path + "," + topMember.getId();
                                   }
                                   member.setZid(topMember.getId());
                                   member.setPath(path);
                                   memberService.update(member);
                               }
                           }
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}

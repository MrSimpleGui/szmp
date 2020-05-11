package com.webdrp.service.schedule;

import com.webdrp.constant.AnalysisType;
import com.webdrp.entity.Analysis;
import com.webdrp.entity.MerchantAnalysis;
import com.webdrp.entity.Member;
import com.webdrp.mapper.AnalysisMapper;
import com.webdrp.mapper.LogMapper;
import com.webdrp.mapper.MemberMapper;
import com.webdrp.service.MerchantAnalysisService;
import com.webdrp.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by yuanming on 2018/8/15.
 * 定时任务类
 */
@Service
public class AnalysisSchedule {

    @Autowired
    AnalysisMapper analysisMapper;

    @Autowired
    MemberMapper memberMapper;


    @Autowired
    LogMapper logMapper;


    /**
     0/5 * * * * ? ： 每5秒执行一次
     0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
     0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
     0 0 12 ? * WED 表示每个星期三中午12点
     "0 0 12 * * ?" 每天中午12点触发
     "0 15 10 ? * *" 每天上午10:15触发
     "0 15 10 * * ?" 每天上午10:15触发
     "0 15 10 * * ? *" 每天上午10:15触发
     "0 15 10 * * ? 2005" 2005年的每天上午10:15触发
     "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发
     "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发
     "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
     "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发
     "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发
     "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发
     "0 15 10 15 * ?" 每月15日上午10:15触发
     "0 15 10 L * ?" 每月最后一日的上午10:15触发
     "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发
     "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发
     "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
     */
    //每天一点15分执行定时任务进行分析微信用户的最新情况
    @Scheduled(cron = "0 10 1 ? * *")
    public void AnalysisRichUserByDateAnyHour(){
        //获取前一天
        String date = DateUtils.getLastDateYYYYMMDD();
        for (int i = 0;i < 24;i++){
            String startDate = date + " "+i+":00:00";
            String endDate = date + " "+i+":59:59";
            String startHour = i+":00:00";
            String endHour = i+":59:59";
            int count = memberMapper.countByDate(startDate,endDate);
            Analysis analysis = new Analysis();
            analysis.setaType(AnalysisType.RICHUSER_DATE_COUNT);
            analysis.setDate(date);
            analysis.setTitle(startHour+"~"+endHour);
            analysis.setSubTitle(startDate+"到"+endDate+"新用户数量");
            analysis.setHour(""+(i+1));
            analysis.setSumCount(0.00);
            analysis.setNum(count);
            analysisMapper.insert(analysis);
        }
        System.out.println("分析数据定时任务执行时间"+new Date());
    }


//    @Scheduled(cron = "0 44 21 ? * *")

    //每天晚上凌晨去1点38分触发这个定时任务
//    @Scheduled(cron = "0 11 1 ? * *")
    public void AnalysisLogRichUserByDateAnyHour() {
        //获取前一天
        String date = DateUtils.getLastDateYYYYMMDD();
        for (int i = 0; i < 24; i++) {
            String startDate = date + " " + i + ":00:00";
            String endDate = date + " " + i + ":59:59";
            String startHour = i + ":00:00";
            String endHour = i + ":59:59";
            int count = logMapper.findLogCountUserByDate(startDate,endDate);
            Analysis analysis = new Analysis();
            analysis.setaType(AnalysisType.RICHUSER_DATE_COUNT_ACTIVE);
            analysis.setDate(date);
            analysis.setTitle(startHour+"~"+endHour);
            analysis.setSubTitle(startDate+"到"+endDate+"访问用户数量");
            analysis.setHour(""+(i+1));
            analysis.setSumCount(0.00);
            analysis.setNum(count);
            analysisMapper.insert(analysis);
        }
    }

    @Autowired
    MerchantAnalysisService merchantAnalysisService;

    Logger logger = LoggerFactory.getLogger(getClass());


//    @Scheduled(cron = "0 */15 * * * ?")
    public void analysisUserAll(){
        List<Member> members = memberMapper.findAllCityUser();
        for (Member member : members){
            MerchantAnalysis merchantAnalysis = merchantAnalysisService.findByRichUserId(member.getId());
            if (Objects.isNull(merchantAnalysis)){
                merchantAnalysis = new MerchantAnalysis();
                if (Objects.nonNull(member.getJid())){
                    merchantAnalysis.setManagerId(member.getJid());
                }else{
                    merchantAnalysis.setManagerId(0);
                }
                merchantAnalysis.setRichUserId(member.getId());
                merchantAnalysis.setDayCount(0);
                merchantAnalysis.setSumCount(0);
                merchantAnalysis.setVipCount(0);
                merchantAnalysis.setTeamCount(0);
                try {
                    merchantAnalysisService.add(merchantAnalysis);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else {
                int teamUserCount = memberMapper.findTeamUserCount(member);
                int teamVipUserCount = memberMapper.findTeamVipUserCount(member);
                int orderCount = memberMapper.findTeamUserOrderCount(member,merchantAnalysis.getCreateTime());
                String day = DateUtils.dateToYYYYMMDD();
                String startTime = day + " 00:00:00";
                String endTime = day + " 23:59:59";
                int dayOrderCount = memberMapper.findTeamUserDayCount(member,startTime,endTime);
                merchantAnalysis.setDayCount(dayOrderCount);
                merchantAnalysis.setSumCount(orderCount);
                merchantAnalysis.setVipCount(teamVipUserCount);
                merchantAnalysis.setTeamCount(teamUserCount);
                merchantAnalysisService.update(merchantAnalysis);
            }

        }

    }

}

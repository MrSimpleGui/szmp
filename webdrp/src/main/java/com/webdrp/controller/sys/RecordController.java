package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.constant.RecordStatus;
import com.webdrp.entity.Income;
import com.webdrp.entity.Record;
import com.webdrp.entity.dto.RecordDto;
import com.webdrp.entity.vo.RecordVo;
import com.webdrp.service.IncomeService;
import com.webdrp.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yuanming on 2018/8/14.
 */
@Api(tags = "后台接口系统端 打款记录接口")
@RestController
@RequestMapping("/sys/record")
public class RecordController extends BaseController<Record,Integer>{

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RecordService recordService;

    /**
     * 抽象业务类  T 实体对象 ID为
     * @return
     */
    @Override
    public BaseService<Record> getService() {
        return recordService;
    }

//    @RequestMapping(value = "/findVoByPage",method = RequestMethod.GET)
    @ApiOperation("分页带条件查询所有")
    @ResponseBody
    public Result loadAll(@RequestHeader(value="token") String token, Record record, Pager pager){
        RecordVo recordVo = new RecordVo();
        BeanUtils.copyProperties(record, recordVo);
        List<RecordVo> list = recordService.loadAll(recordVo,pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }

    @ApiOperation(value = "同意接口 审核提现到 用户账户上，根据提现类型",notes = "只需要给RecordId")
    @PostMapping("/toUserRealWallet")
    public Result toUserRealWallet(@RequestHeader("token")String token,Record record){
//        recordService.toUserRealWallet(record);
        return Result.success();
    }

    @ApiOperation(value = "拒绝接口 审核提现到 用户账户上，根据提现类型",notes = "只需要给recordID")
    @PostMapping("/rejectoUserRealWallet")
    public Result rejectToUserRealWallet(@RequestHeader("token")String token,Record record){
        recordService.rejectoUserRealWallet(record);
        return Result.success();
    }

    @ApiOperation(value = "打款重试接口 审核提现到 用户账户上，根据提现类型",notes = "只需要给RecordId")
    @PostMapping("/retryToUserRealWallet")
    public Result retryToUserRealWallet(@RequestHeader("token")String token,Record record){
        logger.error("打款重试",record.toString());
//        recordService.toUserRealWallet(record);
        return Result.success();
    }

    @ApiOperation(value = "人工打款接口",notes = "说明是人工打了的, 需要给RecordId 和 note(必填)")
    @PostMapping("/idoit")
    public Result idoit(@RequestHeader("token")String token,Record record){
        logger.error("打款重试",record.toString());
        Record demo = new Record();
        demo.setId(record.getId());
        demo.setNote(record.getNote());
        demo.setStatus(RecordStatus.YES);
        recordService.update(demo);
        return Result.success();
    }


    @ApiOperation("获取提现状态")
    @GetMapping("/recordStatus")
    public Result getRecordStatus(@RequestHeader(value = "token") String token){
        return Result.success(RecordStatus.map);
    }


    @ApiOperation("禁用更新")
    @Override
    public Result update(@RequestHeader(value = "token") String token, Record record) {
        return Result.fail("禁用更新",213);
    }


    @ApiOperation("统计今天数据")
    @PostMapping("/count/today")
    public void countTodayRecord() throws ParseException {
        RecordDto recordDto = new RecordDto();
        Date date = new Date();
        String temp = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String startTime  = temp + " 00:00:00";
        String endTime  = temp + " 23:59:59";
        recordDto.setStartTime(startTime);
        recordDto.setEndTime(endTime);
        List<RecordVo> recordVos = recordService.sumStatusRecord(recordDto);
        System.out.println(recordVos);
       // return Result.success().addAttribute("recordVo",recordVo);
    }

    @ApiOperation("统计今天数据")
    @PostMapping("/count/all")
    public void countAllRecord(){
        List<RecordVo> recordVos = recordService.sumStatusRecord(new RecordDto());
        //return Result.success().addAttribute("recordVo",recordVo);
        System.out.println(recordVos);
    }
}

package com.webdrp.controller.web;

import com.webdrp.annotation.PBUserAnnotation;
import com.webdrp.annotation.WechatRequest;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.Record;
import com.webdrp.entity.Member;
import com.webdrp.log.annnotion.SystemControllerLog;
import com.webdrp.service.RecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @Author: zhang yuan ming
 * @Date: create in 21:28 2020-02-19
 * @mail: zh878998515@gmail.com
 * @Description:提现相关
 */
@RestController
@RequestMapping("/wechat/api")
public class CRecordController {

    @Autowired
    RecordService recordService;

    @ApiOperation("提现记录")
    @WechatRequest
    @GetMapping("/recordList")
    public Result incomeList(@PBUserAnnotation Member member, Pager pager){
        Record record = new Record();
        record.setTargetRichUserId(member.getId());
        List<Record> list = recordService.loadAll(record,pager);
        return Result.success().addAttribute("list",list).addAttribute("pager",pager);
    }

    @ApiOperation("发起提现")
    @WechatRequest
    @PostMapping("/toRecord")
    @SystemControllerLog(description = "提现接口")
    public Result dropMoney(@PBUserAnnotation Member member, @RequestParam String name, @RequestParam  Integer money, @RequestParam  String account){
        if (Objects.isNull(money)){
            return Result.fail("提现金额不能为空",100032);
        }

        if ((money%10)>0){
            return Result.fail("提现金额为10的倍数",100032);
        }
        if(money <= 0){
            return Result.fail("金额太小",100032);
        }
        Double mone1 = money + 0.0;

        recordService.addRecord(member,name,account,mone1);
        return Result.success();
    }


}

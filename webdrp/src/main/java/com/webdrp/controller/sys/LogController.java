package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Result;
import com.webdrp.entity.Log;
import com.webdrp.service.impl.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yuanming on 2018/8/9.
 */
@Api(tags = "后台接口微信端log相关")
@RestController
@RequestMapping("/sys/log")
public class LogController  extends BaseController<Log,Integer> {

    @Autowired
    LogService logService;

    /**
     * 抽象业务类  T 实体对象   ID为
     * @return
     */
    @Override
    public BaseService<Log> getService() {
        return logService;
    }
    @ApiOperation("根据个人Id查找")
    @GetMapping("/findByRichUserId")
    public Result findByUserId(@RequestHeader(value="token") String token,@RequestParam Integer id){
        Log log = new Log();
        log.setUserId(id);
        return Result.success(logService.findAll(log));
    }

    @ApiOperation(value = "查看接口访问记录date格式为2012-12-12",notes = "返回的timeCuont为统计数量，methom为平均请求时间")
    @GetMapping("/findLogCountAvg")
    public Result findLogCount(@RequestHeader(value="token") String token, String date){
        return Result.success(logService.findLogCountAvgByDate(date));
    }
}

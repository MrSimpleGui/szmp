package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.CommissionMode;
import com.webdrp.entity.CommissionRule;
import com.webdrp.entity.Commodity;
import com.webdrp.service.CommissionModeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Api(tags = "后台 分佣模式")
@RestController
@RequestMapping("/sys/commissionMode")
public class CommissionModeController extends BaseController<CommissionMode, Integer> {

    @Autowired
    private CommissionModeService commissionModeService;

    @Override
    public BaseService<CommissionMode> getService() {
        return commissionModeService;
    }

//    @PostMapping("/insert")
//    @ApiOperation("插入分佣模式")
    public Result add(@RequestHeader(value = "token") String token, CommissionMode commissionMode){
        if(commissionModeService.selectModeByName(commissionMode.getName())){
            return Result.fail("该模式名存在，请重新设置！", 400);
        }
        commissionModeService.insert(commissionMode);
        return Result.success();
    }
//
//    @PostMapping("/update")
//    @ApiOperation("更新分佣模式")
//    public Result update(@RequestHeader(value = "token") String token, CommissionMode commissionMode){
//        commissionModeService.update(commissionMode);
//        return Result.success();
//    }
//
//    @PostMapping("/delete")
//    @ApiOperation("删除分佣模式")
    public Result delete(@RequestHeader(value = "token") String token, Integer id){
        //需要先删除该模式下的未删除的规则
        CommissionMode commissionMode = new CommissionMode();
        commissionMode.setId(id);
        commissionModeService.delete(commissionMode);
        return Result.success();
    }
//
//    @RequestMapping(value = "/findByPage", method = RequestMethod.GET)
//    @ApiOperation("分页带条件查询所有")
//    @ResponseBody
//    public Result loadAll(@RequestHeader(value = "token") String token, CommissionMode commissionMode, Pager pager) {
//        List<CommissionMode> list = commissionModeService.loadAll(commissionMode, pager);
//        return Result.success().addAttribute("pager", pager).addAttribute("list", list);
//    }
//
//    @RequestMapping(value = "/findAllMode", method = RequestMethod.GET)
//    @ApiOperation("分页带条件查询所有")
//    @ResponseBody
//    public Result findAllMode(@RequestHeader(value = "token") String token) {
//        List<CommissionMode> list = commissionModeService.findAll(new CommissionMode());
//        return Result.success().addAttribute("list", list);
//    }
}

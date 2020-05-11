package com.webdrp.controller.sys;

import com.webdrp.common.BaseController;
import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.common.Result;
import com.webdrp.entity.CommissionRule;
import com.webdrp.entity.Grade;
import com.webdrp.entity.Notification;
import com.webdrp.entity.vo.GradeVo;
import com.webdrp.service.GradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Api(tags = "后台 获取用户角色")
@RestController
@RequestMapping("/sys/grade")
public class GradeController extends BaseController<Grade, Integer> {

    @Autowired
    private GradeService gradeService;

    @Override
    public BaseService<Grade> getService() {
        return gradeService;
    }

    public Result delete(@RequestHeader(value="token") String token, Integer id){
        //暂时不级联删除
        Grade grade = gradeService.findOne(id);
        if(grade == null){
            return Result.fail("用户不存在或者已被删除", 400);
        }
        getService().delete(grade);
        return Result.success();
    }

    public Result add(@RequestHeader(value = "token") String token, Grade grade) {
        if(Objects.isNull(grade) || Objects.isNull(grade.getName()) || Objects.isNull(grade.getRank())){
            return Result.fail("请填写完整信息", 400);
        }
        if(gradeService.findGradeByNameAndRank(grade)){
            return Result.fail("该等级名称或rank已存在", 400);
        }
        gradeService.insert(grade);
        return Result.success();
    }

    @ResponseBody
    public Result loadAll(@RequestHeader(value="token") String token , Grade t, Pager pager){
        List<GradeVo> list = gradeService.loadAllVo(t,pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }


}

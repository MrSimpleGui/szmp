package com.webdrp.common;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Macx on 2018/1/4.
 * 后台管理专用
 */

public abstract class BaseController<T,ID extends Serializable> {
    /**
     * 抽象业务类  T 实体对象   ID为
     * @return
     */
    public abstract  BaseService<T> getService();

    @ApiOperation("单个查找")
    @RequestMapping(value = "/findOne",method = RequestMethod.GET)
    @ResponseBody
    public Result findOne(@RequestHeader(value="token") String token, ID t){
        return Result.success(getService().findOne(t));
    }

    @ApiOperation("单个添加")
    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestHeader(value="token") String token,T t)throws Exception {
        getService().add(t);
        return Result.success(null);
    }

    @ApiOperation("根据Id删除")
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestHeader(value="token") String token,ID id) {
        return Result.success(null);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation("更新")
    @ResponseBody
    public Result update(@RequestHeader(value="token") String token,T t) {
        getService().update(t);
        return  Result.success(null);
    }

    /**
     * 查找所有
     */
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    @ApiOperation("查询所有,支持根据主类参数筛选")
    @ResponseBody
    public Result findAll(@RequestHeader(value="token") String token,T t) {
         return Result.success(getService().findAll(t));
    }

    @RequestMapping(value = "/findByPage",method = RequestMethod.GET)
    @ApiOperation("分页带条件查询所有")
    @ResponseBody
    public Result loadAll(@RequestHeader(value="token") String token , T t, Pager pager){
        List<T> list = getService().loadAll(t,pager);
        return Result.success().addAttribute("pager",pager).addAttribute("list",list);
    }
}

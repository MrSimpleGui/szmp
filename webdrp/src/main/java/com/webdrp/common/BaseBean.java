package com.webdrp.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webdrp.util.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yuanming on 2018/8/1.
 */
public abstract class BaseBean implements Serializable{

    //主键
    private Integer id;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;
    //删除时间
    @JsonIgnore
    private String deleteTime;
    /// <summary>
    /// 获取主键
    /// </summary>
    public Integer getId()
    {
        return this.id;
    }
    /// <summary>
    /// 设置主键
    /// </summary>
    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public void beforeCreate(){
        this.setId(null);
        this.setCreateTime(DateUtils.dateToString(new Date()));
        this.setUpdateTime(DateUtils.dateToString(new Date()));
        this.setDeleteTime(null);
    }
    public void beforeDelete(){
        this.setDeleteTime(DateUtils.dateToString(new Date()));
    }


}

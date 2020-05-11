package com.webdrp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webdrp.common.BaseBean;
import com.webdrp.constant.PermissionType;
import com.webdrp.constant.PermissionWeight;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Permission extends BaseBean {

    @ApiModelProperty("父级节点ID")
    private Integer pid;

    @ApiModelProperty("权限英文名，唯一")
    private String esName;

    @ApiModelProperty("权限中文名")
    private String zhName;

    @ApiModelProperty("权限类型，0前端URL，1前端Button，2后台URL")
    private Integer permissionType;

    @ApiModelProperty("权限级别 0一般权限 1 view 查看,2 add 添加,3 update 更新,4 delete 删除")
    private Integer weight;

    public String getEsName() {
        return esName;
    }

    public void setEsName(String esName) {
        this.esName = esName;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissionTypeString(){
        return PermissionType.getString(getPermissionType());
    }
    public String getWeightString() {
        return PermissionWeight.getString(getWeight());
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}

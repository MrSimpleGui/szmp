package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import lombok.Data;

/**
 * Created by yuanming on 2018/8/8.
 * 对应sys_role
 */
@Data
public class Role extends BaseBean{
    //中文名
    private String roleName;
    //英文名
    private String esName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEsName() {
        return esName;
    }

    public void setEsName(String esName) {
        this.esName = esName;
    }
}

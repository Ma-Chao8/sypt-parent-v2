package com.tianma315.core.procedures.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @author lgc
 * @createDate 2020/5/19 - 18:21
 */
@TableName("link_role")
public class LinkRole {
    /**  */
    @TableId
    private Integer id;
    /**
    * 环节id
    */
    private Integer procedureLinkId;

    /**
    * 角色id
    */
    private Integer roleId;

    /**
    * 状态
    */
    private Integer  status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProcedureLinkId() {
        return procedureLinkId;
    }

    public void setProcedureLinkId(Integer procedureLinkId) {
        this.procedureLinkId = procedureLinkId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

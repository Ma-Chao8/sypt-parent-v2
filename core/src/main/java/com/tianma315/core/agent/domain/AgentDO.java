package com.tianma315.core.agent.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 经销商
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
 @TableName("agent")
public class AgentDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /** 主键 */
    @TableId
    private Long agentId;
    /**  */
    private Long levelId;
    /** 用户id */
    private Long userId;
    /** 直接上级id */
    private Long parentUserId;
    /** 邀请人用户id */
    private Long inviteUserId;
    /** 经销商所属商户 */
    private Long companyId;
    /** 代理商名字 */
    private String agentName;
    /** 联系人 */
    private String linkman;
    /** 电话 */
    private String tel;
    /** 邮箱 */
    private String email;
    /** 创建时间 */
    private Date createDate;
    /** 状态  1可用   0冻结 */
    private Integer status;

    /** 排序字段名称 */
    @TableField(exist = false)
    private String sortName;
    /** 排序顺序 */
    @TableField(exist = false)
    private String sortOrder;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 设置：主键
     */
    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
    /**
     * 获取：主键
     */
    public Long getAgentId() {
        return agentId;
    }
    /**
     * 设置：
     */
    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }
    /**
     * 获取：
     */
    public Long getLevelId() {
        return levelId;
    }
    /**
     * 设置：用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    /**
     * 获取：用户id
     */
    public Long getUserId() {
        return userId;
    }
    /**
     * 设置：直接上级id
     */
    public void setParentUserId(Long parentUserId) {
        this.parentUserId = parentUserId;
    }
    /**
     * 获取：直接上级id
     */
    public Long getParentUserId() {
        return parentUserId;
    }
    /**
     * 设置：邀请人用户id
     */
    public void setInviteUserId(Long inviteUserId) {
        this.inviteUserId = inviteUserId;
    }
    /**
     * 获取：邀请人用户id
     */
    public Long getInviteUserId() {
        return inviteUserId;
    }
    /**
     * 设置：经销商所属商户
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /**
     * 获取：经销商所属商户
     */
    public Long getCompanyId() {
        return companyId;
    }
    /**
     * 设置：代理商名字
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
    /**
     * 获取：代理商名字
     */
    public String getAgentName() {
        return agentName;
    }
    /**
     * 设置：联系人
     */
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }
    /**
     * 获取：联系人
     */
    public String getLinkman() {
        return linkman;
    }
    /**
     * 设置：电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }
    /**
     * 获取：电话
     */
    public String getTel() {
        return tel;
    }
    /**
     * 设置：邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * 获取：邮箱
     */
    public String getEmail() {
        return email;
    }
    /**
     * 设置：创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    /**
     * 获取：创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }
    /**
     * 设置：状态  1可用   0冻结
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * 获取：状态  1可用   0冻结
     */
    public Integer getStatus() {
        return status;
    }
}

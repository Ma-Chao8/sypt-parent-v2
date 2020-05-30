package com.tianma315.core.agent.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 经销商地址
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
 @TableName("agent_address")
public class AgentAddressDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId
    private Long addressId;
    /** 经销商id */
    private Long agentId;
    /** 收货地址 */
    private String address;
    /** 状态 1默认地址  0非默认  2已删除 */
    private Integer status;
    /** 创建时间 */
    private Date createDate;
    /** 省 */
    private String province;
    /** 市 */
    private String city;
    /** 区 */
    private String area;

    private Long companyId;

    /**
     * 设置：
     */
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    /**
     * 获取：
     */
    public Long getAddressId() {
        return addressId;
    }
    /**
     * 设置：经销商id
     */
    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
    /**
     * 获取：经销商id
     */
    public Long getAgentId() {
        return agentId;
    }
    /**
     * 设置：收货地址
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * 获取：收货地址
     */
    public String getAddress() {
        return address;
    }
    /**
     * 设置：状态 1默认地址  0非默认  2已删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /**
     * 获取：状态 1默认地址  0非默认  2已删除
     */
    public Integer getStatus() {
        return status;
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
     * 设置：省
     */
    public void setProvince(String province) {
        this.province = province;
    }
    /**
     * 获取：省
     */
    public String getProvince() {
        return province;
    }
    /**
     * 设置：市
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * 获取：市
     */
    public String getCity() {
        return city;
    }
    /**
     * 设置：区
     */
    public void setArea(String area) {
        this.area = area;
    }
    /**
     * 获取：区
     */
    public String getArea() {
        return area;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}

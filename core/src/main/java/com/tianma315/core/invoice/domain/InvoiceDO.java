package com.tianma315.core.invoice.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;



/**
 * 
 * <pre>
 * 货单
 * </pre>
 * <small> 2019-08-21 14:56:27 | Lgc</small>
 */
 @TableName("invoice")
public class InvoiceDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /** 主键 */
    @TableId
    private Long invoiceId;
    /** 商户编号(识别订单所属商户） */
    private Long companyId;
    /** 货单编号  暂定14位 */
    private String invoiceNumber;
    /** 创建人用户id */
    private Long createBy;
    /**  */
    private Long agentId;
    /** 联系人 */
    private String linkman;
    /**  */
    private String tel;
    /** 发货地址 */
    private String deliverAddres;
    /** 货单创建时间 */
    private Date createdDate;
    /** 发货时间 */
    private Date deliverDate;
    /** 备注 */
    private String remark;
    /** 0未扫码发货 1已发货未收货 2已收货 3部分退货 4全部退货 */
    private Integer state;

    private Long traceFileId;
    private Long warehouseId;

    public Long getTraceFileId() {
        return traceFileId;
    }

    public void setTraceFileId(Long traceFileId) {
        this.traceFileId = traceFileId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * 设置：主键
     */
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
    /**
     * 获取：主键
     */
    public Long getInvoiceId() {
        return invoiceId;
    }
    /**
     * 设置：商户编号(识别订单所属商户）
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
    /**
     * 获取：商户编号(识别订单所属商户）
     */
    public Long getCompanyId() {
        return companyId;
    }
    /**
     * 设置：货单编号  暂定14位
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    /**
     * 获取：货单编号  暂定14位
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    /**
     * 设置：创建人用户id
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    /**
     * 获取：创建人用户id
     */
    public Long getCreateBy() {
        return createBy;
    }
    /**
     * 设置：
     */
    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
    /**
     * 获取：
     */
    public Long getAgentId() {
        return agentId;
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
     * 设置：
     */
    public void setTel(String tel) {
        this.tel = tel;
    }
    /**
     * 获取：
     */
    public String getTel() {
        return tel;
    }
    /**
     * 设置：发货地址
     */
    public void setDeliverAddres(String deliverAddres) {
        this.deliverAddres = deliverAddres;
    }
    /**
     * 获取：发货地址
     */
    public String getDeliverAddres() {
        return deliverAddres;
    }
    /**
     * 设置：货单创建时间
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    /**
     * 获取：货单创建时间
     */
    public Date getCreatedDate() {
        return createdDate;
    }
    /**
     * 设置：发货时间
     */
    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }
    /**
     * 获取：发货时间
     */
    public Date getDeliverDate() {
        return deliverDate;
    }
    /**
     * 设置：备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * 获取：备注
     */
    public String getRemark() {
        return remark;
    }
    /**
     * 设置：0未扫码发货 1已发货未收货 2已收货 3部分退货 4全部退货
     */
    public void setState(Integer state) {
        this.state = state;
    }
    /**
     * 获取：0未扫码发货 1已发货未收货 2已收货 3部分退货 4全部退货
     */
    public Integer getState() {
        return state;
    }
}

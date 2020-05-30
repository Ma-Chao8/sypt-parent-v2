package com.tianma315.core.procedures.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;


/**
 * <pre>
 * 溯源流程模板
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
@TableName("procedures")
public class ProceduresDO implements Serializable {
    /*
    CREATE TABLE `procedures` (
    `procedure_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name` varchar(255) DEFAULT NULL COMMENT '名称',
    `state` int(11) DEFAULT '0' COMMENT '状态',
    `product_id` varchar(255) DEFAULT NULL COMMENT '商品ID',
    `is_open` int(11) DEFAULT NULL COMMENT '是否启用  0:未启用  1:启用',
    `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    `company_id` bigint(20) DEFAULT NULL COMMENT '商户ID',
    `theme_id` bigint(64) DEFAULT NULL COMMENT '主题',
    `theme_icon` varchar(255) DEFAULT NULL COMMENT '主题图标',
    `is_all_product` int(2) DEFAULT NULL COMMENT '是否绑定所有产品',
    `banner_enable` int(11) DEFAULT NULL COMMENT '是否启用轮播图 0 关闭 1启用',
    `footlink_anable` int(11) DEFAULT NULL COMMENT '是否启用底部连接 0关闭 1启用',
    PRIMARY KEY (`procedure_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COMMENT='溯源流程模板';
    */

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    public static final int STATUS_BANNER_ENABLE = 1;
    @TableField(exist = false)
    public static final int STATUS_BANNER_CLOSE = 0;
    @TableField(exist = false)
    public static final int STATUS_FOOTLINK_ENABLE = 1;
    @TableField(exist = false)
    public static final int STATUS_FOOTLINK_CLOSE = 0;


    /**
     * ID
     */
    @TableId
    private Integer procedureId;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 商品ID
     */
    private String productId;
    /**
     * 是否启用  0:未启用  1:启用
     */
    private Integer isOpen;
    /**
     * 创建者
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 商户ID
     */
    private Long companyId;

    /**
     * 是否绑定所有产品
     */
    private Integer isAllProduct;

    /**
     * 是否启用轮播图 0 关闭 1启用
     */
    private Integer bannerEnable;
    /**
     * 是否启用底部连接 0关闭 1启用
     */
    private Integer footlinkAnable;

    /**
     * 主题ID
     */
    private long themeId;

    /**
     * 主题图标
     */
    private String themeIcon;


    /**
     * 设置：ID
     */
    public void setProcedureId(Integer procedureId) {
        this.procedureId = procedureId;
    }

    /**
     * 获取：ID
     */
    public Integer getProcedureId() {
        return procedureId;
    }

    /**
     * 设置：名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：状态
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取：状态
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置：商品ID
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 获取：商品ID
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 设置：是否启用  0:未启用  1:启用
     */
    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * 获取：是否启用  0:未启用  1:启用
     */
    public Integer getIsOpen() {
        return isOpen;
    }

    /**
     * 设置：创建者
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取：创建者
     */
    public Long getCreateBy() {
        return createBy;
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
     * 设置：商户ID
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取：商户ID
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * 设置：是否绑定所有产品
     */
    public void setIsAllProduct(Integer isAllProduct) {
        this.isAllProduct = isAllProduct;
    }

    /**
     * 获取：是否绑定所有产品
     */
    public Integer getIsAllProduct() {
        return isAllProduct;
    }

    public Integer getBannerEnable() {
        return bannerEnable;
    }

    public void setBannerEnable(Integer bannerEnable) {
        this.bannerEnable = bannerEnable;
    }

    public Integer getFootlinkAnable() {
        return footlinkAnable;
    }

    public void setFootlinkAnable(Integer footlinkAnable) {
        this.footlinkAnable = footlinkAnable;
    }

    public long getThemeId() {
        return themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }

    public String getThemeIcon() {
        return themeIcon;
    }

    public void setThemeIcon(String themeIcon) {
        this.themeIcon = themeIcon;
    }
}

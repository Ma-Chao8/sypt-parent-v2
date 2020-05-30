package com.tianma315.core.procedures.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tianma315.core.base.BaseModel;

@TableName("procedures_banner")
public class ProceduresBanner extends BaseModel {
    /*
    CREATE TABLE `procedures_banner` (
    `banner_id` bigint(20) NOT NULL,
    `procedures_id` bigint(20) DEFAULT NULL COMMENT '溯源模板ID',
    `banner_img_id` bigint(20) DEFAULT NULL COMMENT '图片ID',
    `banner_url` varchar(255) DEFAULT NULL COMMENT '图片url',
    `banner_sequence` int(11) DEFAULT NULL COMMENT '轮播图顺序',
    `bannerTitle` varchar(255) DEFAULT NULL COMMENT '标题',
    `banner_redirect_url` varchar(255) DEFAULT NULL COMMENT '跳转链接',
    `banner_status` int(11) DEFAULT NULL COMMENT '状态 0 删除 1可用',
    PRIMARY KEY (`banner_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    */
    @TableField(exist = false)
    public static final int STATUS_DELETE = 0;
    @TableField(exist = false)
    public static final int STATUS_ENABLE = 1;
    @TableId
    private long bannerId;
    private long proceduresId;
    private long bannerImgId;
    private String bannerUrl;
    private int bannerSequence;
    private String bannerTitle;
    private String bannerRedirectUrl;
    private int bannerStatus;

    public long getBannerId() {
        return bannerId;
    }

    public void setBannerId(long bannerId) {
        this.bannerId = bannerId;
    }

    public long getProceduresId() {
        return proceduresId;
    }

    public void setProceduresId(long proceduresId) {
        this.proceduresId = proceduresId;
    }

    public long getBannerImgId() {
        return bannerImgId;
    }

    public void setBannerImgId(long bannerImgId) {
        this.bannerImgId = bannerImgId;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public int getBannerSequence() {
        return bannerSequence;
    }

    public void setBannerSequence(int bannerSequence) {
        this.bannerSequence = bannerSequence;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public String getBannerRedirectUrl() {
        return bannerRedirectUrl;
    }

    public void setBannerRedirectUrl(String bannerRedirectUrl) {
        this.bannerRedirectUrl = bannerRedirectUrl;
    }

    public int getBannerStatus() {
        return bannerStatus;
    }

    public void setBannerStatus(int bannerStatus) {
        this.bannerStatus = bannerStatus;
    }
}

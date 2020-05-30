package com.tianma315.core.procedures.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tianma315.core.base.BaseModel;

@TableName("procedures_footlink")
public class ProceduresFootlink extends BaseModel {
    /*
    CREATE TABLE `procedures_footlink` (
    `footlink_id` bigint(20) NOT NULL AUTO_INCREMENT,
    `procedures_id` bigint(20) DEFAULT NULL COMMENT '模板id',
    `footlink_title` varchar(255) DEFAULT NULL COMMENT '标题',
    `footlink_content` varchar(255) DEFAULT NULL COMMENT '内容',
    `footlink_sequence` int(11) DEFAULT NULL COMMENT '顺序',
    `footlink_redirect_url` varchar(255) DEFAULT NULL COMMENT '跳转链接',
    `footlink_icon_id` bigint(20) DEFAULT NULL COMMENT '图标ID',
    `footlink_icon` varchar(255) DEFAULT NULL COMMENT '图标',
    `footlink_status` int(11) DEFAULT NULL COMMENT '状态 0删除 1可用',
    PRIMARY KEY (`footlink_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    */
    @TableField(exist = false)
    public static final int STATUS_DELETE = 0;
    @TableField(exist = false)
    public static final int STATUS_ENABLE = 1;
    @TableId
    private long footlinkId;
    private long proceduresId;
    private String footlinkTitle;
    private String footlinkContent;
    private int footlinkSequence;
    private String footlinkRedirectUrl;
    private long footlinkIconId;
    private String footlinkIcon;
    private int footlinkStatus;

    public long getFootlinkId() {
        return footlinkId;
    }

    public void setFootlinkId(long footlinkId) {
        this.footlinkId = footlinkId;
    }

    public long getProceduresId() {
        return proceduresId;
    }

    public void setProceduresId(long proceduresId) {
        this.proceduresId = proceduresId;
    }

    public String getFootlinkTitle() {
        return footlinkTitle;
    }

    public void setFootlinkTitle(String footlinkTitle) {
        this.footlinkTitle = footlinkTitle;
    }

    public String getFootlinkContent() {
        return footlinkContent;
    }

    public void setFootlinkContent(String footlinkContent) {
        this.footlinkContent = footlinkContent;
    }

    public int getFootlinkSequence() {
        return footlinkSequence;
    }

    public void setFootlinkSequence(int footlinkSequence) {
        this.footlinkSequence = footlinkSequence;
    }

    public String getFootlinkRedirectUrl() {
        return footlinkRedirectUrl;
    }

    public void setFootlinkRedirectUrl(String footlinkRedirectUrl) {
        this.footlinkRedirectUrl = footlinkRedirectUrl;
    }

    public long getFootlinkIconId() {
        return footlinkIconId;
    }

    public void setFootlinkIconId(long footlinkIconId) {
        this.footlinkIconId = footlinkIconId;
    }

    public String getFootlinkIcon() {
        return footlinkIcon;
    }

    public void setFootlinkIcon(String footlinkIcon) {
        this.footlinkIcon = footlinkIcon;
    }

    public int getFootlinkStatus() {
        return footlinkStatus;
    }

    public void setFootlinkStatus(int footlinkStatus) {
        this.footlinkStatus = footlinkStatus;
    }
}

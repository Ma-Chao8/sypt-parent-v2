package com.tianma315.core.company.domain.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tianma315.core.base.BaseModel;


/**
 * <pre>
 *
 * </pre>
 * <small> 2019-09-25 16:51:32 | Lgc</small>
 */
@TableName("demo")
public class DemoDO extends BaseModel {
    /*
    CREATE TABLE `demo` (
    `demo_id` int(11) NOT NULL AUTO_INCREMENT,
    `demo_name` varchar(16) DEFAULT NULL COMMENT '主题名称',
    `demo_values` varchar(16) DEFAULT NULL COMMENT '主题对应的模板',
    `demo_icon` varchar(255) DEFAULT NULL COMMENT '主题图标',
    `demo_status` int(11) DEFAULT NULL COMMENT '状态 0 删除 1可用',
    PRIMARY KEY (`demo_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
    */

    @TableField(exist = false)
    public static final int STATUS_DELETE = 0;
    @TableField(exist = false)
    public static final int STATUS_ENABLE = 1;

    @TableId
    private long demoId;
    private String demoValues;
    private String demoName;
    private String demoIcon;
    private int demoStatus;

    public long getDemoId() {
        return demoId;
    }

    public void setDemoId(long demoId) {
        this.demoId = demoId;
    }

    public String getDemoValues() {
        return demoValues;
    }

    public void setDemoValues(String demoValues) {
        this.demoValues = demoValues;
    }

    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    public String getDemoIcon() {
        return demoIcon;
    }

    public void setDemoIcon(String demoIcon) {
        this.demoIcon = demoIcon;
    }

    public int getDemoStatus() {
        return demoStatus;
    }

    public void setDemoStatus(int demoStatus) {
        this.demoStatus = demoStatus;
    }
}

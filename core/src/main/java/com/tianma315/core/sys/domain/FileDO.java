package com.tianma315.core.sys.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tianma315.core.base.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 文件上传
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@TableName("sys_file")
public class FileDO extends BaseModel implements Serializable {
    @TableField(exist = false)
    public static final int STATUS_DELETE = 0;
    @TableField(exist = false)
    public static final int STATUS_ENABLE = 1;

    //
    @TableId
    private long id;
    private long companyId;
    private String fileKey;
    private String originName;
    private String path;
    private String url;
    private String type;
    private Date createDate;
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

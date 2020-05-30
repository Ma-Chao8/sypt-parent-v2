package com.tianma315.core.warehouse.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

@TableName("Warehouse_record_code")
public class WarehouseRecordCodeDO  implements Serializable {

    /**  */
    @TableId
    private Integer id;
    private Integer warehouseRecordId;
    private String bigCode;
    private String smallCode;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWarehouseRecordId() {
        return warehouseRecordId;
    }

    public void setWarehouseRecordId(Integer warehouseRecordId) {
        this.warehouseRecordId = warehouseRecordId;
    }

    public String getBigCode() {
        return bigCode;
    }

    public void setBigCode(String bigCode) {
        this.bigCode = bigCode;
    }

    public String getSmallCode() {
        return smallCode;
    }

    public void setSmallCode(String smallCode) {
        this.smallCode = smallCode;
    }

    @Override
    public String toString() {
        return "WarehouseRecordCodeDO{" +
                "id=" + id +
                ", warehouseRecordId=" + warehouseRecordId +
                ", bigCode='" + bigCode + '\'' +
                ", smallCode='" + smallCode + '\'' +
                '}';
    }
}

package com.tianma315.core.attr.vo;



import com.tianma315.core.attr.domain.AttrFieldDO;
import com.tianma315.core.attr.domain.FieldValueDO;

import java.util.List;

public class AttrFieldAndFieldVO extends AttrFieldDO {
    private List<FieldValueDO> fieldValueDOList;

    public List<FieldValueDO> getFieldValueDOList() {
        return fieldValueDOList;
    }

    public void setFieldValueDOList(List<FieldValueDO> fieldValueDOList) {
        this.fieldValueDOList = fieldValueDOList;
    }
}

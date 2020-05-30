package com.tianma315.core.material.vo;


import com.tianma315.core.material.domain.MaterialDO;

public class MaterialAndTemplateVO extends MaterialDO {
    private String templateName;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}

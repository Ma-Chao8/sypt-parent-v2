package com.tianma315.core.company.domain.pojo;

import java.util.List;

public class CompanyDemoVO {
    private Integer companyId;
    private List<Integer> demoIdList;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<Integer> getDemoIdList() {
        return demoIdList;
    }

    public void setDemoIdList(List<Integer> demoIdList) {
        this.demoIdList = demoIdList;
    }
}

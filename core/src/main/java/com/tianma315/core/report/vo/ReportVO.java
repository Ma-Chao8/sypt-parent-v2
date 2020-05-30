package com.tianma315.core.report.vo;

import com.tianma315.core.report.domain.ReportDO;
import com.tianma315.core.report.domain.ReportImgDO;

import java.util.List;

public class ReportVO extends ReportDO{
//    private ReportDO reportDO;
    private List<ReportImgDO> reportImgDOList;

    private String checkMechanismName;
    private String checkTypeName;
    private String companyName;
    private String tel;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<ReportImgDO> getReportImgDOList() {
        return reportImgDOList;
    }

    public void setReportImgDOList(List<ReportImgDO> reportImgDOList) {
        this.reportImgDOList = reportImgDOList;
    }

    public String getCheckMechanismName() {
        return checkMechanismName;
    }

    public void setCheckMechanismName(String checkMechanismName) {
        this.checkMechanismName = checkMechanismName;
    }

    public String getCheckTypeName() {
        return checkTypeName;
    }

    public void setCheckTypeName(String checkTypeName) {
        this.checkTypeName = checkTypeName;
    }
}

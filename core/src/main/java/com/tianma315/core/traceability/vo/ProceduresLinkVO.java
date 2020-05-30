package com.tianma315.core.traceability.vo;

import com.tianma315.core.procedures.domain.LinkContentDO;
import com.tianma315.core.procedures.domain.ProcedureLinkDO;

import java.util.List;

public class ProceduresLinkVO {

    private ProcedureLinkDO procedureLink;
    private List<LinkContentDO> linkContentList;

    public ProcedureLinkDO getProcedureLink() {
        return procedureLink;
    }

    public void setProcedureLink(ProcedureLinkDO procedureLink) {
        this.procedureLink = procedureLink;
    }

    public List<LinkContentDO> getLinkContentList() {
        return linkContentList;
    }

    public void setLinkContentList(List<LinkContentDO> linkContentList) {
        this.linkContentList = linkContentList;
    }
}

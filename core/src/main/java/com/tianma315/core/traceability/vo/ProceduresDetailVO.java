package com.tianma315.core.traceability.vo;


import com.tianma315.core.procedures.domain.ProceduresDO;

import java.util.List;

public class ProceduresDetailVO {
    private ProceduresDO procedures;

    private List<ProceduresLinkVO> proceduresLinkVOList;

    public ProceduresDO getProcedures() {
        return procedures;
    }

    public void setProcedures(ProceduresDO procedures) {
        this.procedures = procedures;
    }

    public List<ProceduresLinkVO> getProceduresLinkVOList() {
        return proceduresLinkVOList;
    }

    public void setProceduresLinkVOList(List<ProceduresLinkVO> proceduresLinkVOList) {
        this.proceduresLinkVOList = proceduresLinkVOList;
    }
}

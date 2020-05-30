package com.tianma315.core.trace.vo;

import java.util.List;

public class CodeVo {
    private Integer traceabilityFileId;
    private Integer proceduresLinkId;
    private List<ImagesDo> imgs;
    private List<InputDo> inputDos;
    private List<OldImagesDo> oldImgs;

    public List<OldImagesDo> getOldImgs() {
        return oldImgs;
    }

    public void setOldImgs(List<OldImagesDo> oldImgs) {
        this.oldImgs = oldImgs;
    }

    public Integer getProceduresLinkId() {
        return proceduresLinkId;
    }

    public void setProceduresLinkId(Integer proceduresLinkId) {
        this.proceduresLinkId = proceduresLinkId;
    }

    public Integer getTraceabilityFileId() {
        return traceabilityFileId;
    }

    public void setTraceabilityFileId(Integer traceabilityFileId) {
        this.traceabilityFileId = traceabilityFileId;
    }

    public List<ImagesDo> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImagesDo> imgs) {
        this.imgs = imgs;
    }

    public List<InputDo> getInputDos() {
        return inputDos;
    }

    public void setInputDos(List<InputDo> inputDos) {
        this.inputDos = inputDos;
    }
}

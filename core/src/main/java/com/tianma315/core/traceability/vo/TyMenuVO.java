package com.tianma315.core.traceability.vo;


import com.tianma315.core.traceability.domain.TyMenuDO;
import com.tianma315.core.traceability.domain.TyMenuDataDO;

import java.util.List;

public class TyMenuVO extends TyMenuDO{
    private String traceFileName;
    private String username;
    private TyMenuDO tyMenu;
    private List<TyMenuDataVO> dataList;

    public String getTraceFileName() {
        return traceFileName;
    }

    public void setTraceFileName(String traceFileName) {
        this.traceFileName = traceFileName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TyMenuDO getTyMenu() {
        return tyMenu;
    }

    public void setTyMenu(TyMenuDO tyMenu) {
        this.tyMenu = tyMenu;
    }

    public List<TyMenuDataVO> getDataList() {
        return dataList;
    }

    public void setDataList(List<TyMenuDataVO> dataList) {
        this.dataList = dataList;
    }
}

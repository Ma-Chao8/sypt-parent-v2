package com.tianma315.core.procedures.vo;

import com.tianma315.core.procedures.domain.ProcedureLinkDO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProcedureLinkFileVO extends ProcedureLinkDO {
    private String proceduresName;

    private MultipartFile iconFile;


    /**
    * 角色的选择
    */
    private String linkRoles;
    private List<String> roleNames;

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }

    public String getLinkRoles() {
        return linkRoles;
    }

    public void setLinkRoles(String linkRoles) {
        this.linkRoles = linkRoles;
    }

    public String getProceduresName() {
        return proceduresName;
    }

    public void setProceduresName(String proceduresName) {
        this.proceduresName = proceduresName;
    }

    public MultipartFile getIconFile() {
        return iconFile;
    }

    public void setIconFile(MultipartFile iconFile) {
        this.iconFile = iconFile;
    }
}

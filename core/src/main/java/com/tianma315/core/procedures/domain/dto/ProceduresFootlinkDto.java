package com.tianma315.core.procedures.domain.dto;

import com.tianma315.core.procedures.domain.ProceduresFootlink;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 *
 */
public class ProceduresFootlinkDto extends ProceduresFootlink {

    private MultipartFile file;


    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}

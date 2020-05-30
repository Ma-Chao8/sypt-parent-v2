package com.tianma315.core.procedures.domain.dto;

/**
 *
 */

import com.tianma315.core.procedures.domain.ProceduresBanner;
import org.springframework.web.multipart.MultipartFile;

public class ProceduresBannerDto extends ProceduresBanner {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}

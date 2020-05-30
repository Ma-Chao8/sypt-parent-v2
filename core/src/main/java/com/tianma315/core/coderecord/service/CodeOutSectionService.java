package com.tianma315.core.coderecord.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.coderecord.domain.pojo.CodeOutSection;

import java.util.List;

public interface CodeOutSectionService {


    /**
     * @param company_id
     * @return
     */
    List<CodeOutSection> getSectionByCompany(long company_id);


    /**
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page getPage(int pageNumber, int pageSize);

    /**
     *
     * @param section
     * @return
     */
    boolean save(CodeOutSection section);
}

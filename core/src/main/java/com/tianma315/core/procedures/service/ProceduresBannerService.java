package com.tianma315.core.procedures.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.procedures.domain.ProceduresBanner;
import com.tianma315.core.procedures.domain.dto.ProceduresBannerDto;
import com.tianma315.core.traceability.domain.TraceabilityFileDO;

import java.util.List;

/**
 * 档案模板轮播图
 */
public interface ProceduresBannerService extends CoreService<ProceduresBanner> {


    /**
     * 保存轮播图
     *
     * @param userId
     * @param banner
     * @return
     */
    boolean saveBanner(Long userId, ProceduresBannerDto banner);

    /**
     * 删除轮播图
     *
     * @param bannerId
     * @return
     */
    boolean removeBanner(List<Long> bannerId);

    /**
     * 编辑轮播图
     *
     * @param userId
     * @param banner
     * @return
     */
    boolean editBanner(Long userId, ProceduresBannerDto banner);

    /**
     * @param proceduresId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<ProceduresBanner> getPage(long proceduresId, Integer pageNumber, Integer pageSize);

    /**
     * @param banner_id
     * @return
     */
    ProceduresBanner getById(long banner_id);

}

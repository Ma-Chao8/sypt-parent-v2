package com.tianma315.core.procedures.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.procedures.domain.ProceduresFootlink;
import com.tianma315.core.procedures.domain.dto.ProceduresFootlinkDto;

import java.util.List;

/**
 * 模板底部链接
 */
public interface ProceduresFootlinkService extends CoreService<ProceduresFootlink> {

    /**
     * 获取分页数据
     *
     * @param pageNumber
     * @param pageSize
     * @param proceduresId
     * @return
     */
    Page<ProceduresFootlink> getPage(Integer pageNumber, Integer pageSize, long proceduresId);

    /**
     *
     * @param userId
     * @param footlink
     * @return
     */
    boolean addFootlink(Long userId, ProceduresFootlinkDto footlink);

    /**
     * @param footlinkId
     * @return
     */
    ProceduresFootlink getById(long footlinkId);

    /**
     * @param asList
     * @return
     */
    boolean removeBatchIds(List<Long> asList);

    /**
     *
     * @param userId
     * @param footlink
     * @return
     */
    boolean edit(Long userId, ProceduresFootlinkDto footlink);
}

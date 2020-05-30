package com.tianma315.core.company.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.company.domain.pojo.DemoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 * <small> 2019-09-25 16:51:32 | Lgc</small>
 */
public interface DemoDao extends BaseMapper<DemoDO> {

    List<DemoDO> getDemoByCompanyId(Long companyId);

    /**
     * @param pag
     * @param wrapper
     * @return
     */
    List<DemoDO> selectDemoPage(@Param("page") Page pag, @Param("ew") Wrapper wrapper);
}

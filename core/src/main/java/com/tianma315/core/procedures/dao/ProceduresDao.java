package com.tianma315.core.procedures.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.procedures.domain.ProceduresDO;
import com.tianma315.core.procedures.vo.ProceduresVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <pre>
 * 溯源流程模板
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
public interface ProceduresDao extends CoreMapper<ProceduresDO> {

    List<ProceduresVO> selectPage(@Param("page")Page page, @Param("companyId") Long companyId);

}

package com.tianma315.core.procedures.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.procedures.domain.LinkRole;
import com.tianma315.core.procedures.domain.ProcedureLinkDO;
import com.tianma315.core.procedures.vo.ProcedureLinkFileVO;
import com.tianma315.core.procedures.vo.ProceduresVO;
import com.tianma315.core.traceability.vo.ProceduresLinkVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <pre>
 * 环节
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
public interface ProcedureLinkDao extends CoreMapper<ProcedureLinkDO> {

    List<ProcedureLinkFileVO> listProceduresVO(@Param("param")Page page, Long companyId);

    void insertRole(List<LinkRole> list);

}

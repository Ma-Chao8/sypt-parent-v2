package com.tianma315.core.coderecord.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.coderecord.domain.CodeRecordDO;
import com.tianma315.core.coderecord.vo.CodeRecordCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <pre>
 * 溯源码查询记录
 * </pre>
 * <small> 2019-06-26 16:36:01 | Wen</small>
 */
public interface CodeRecordDao extends CoreMapper<CodeRecordDO> {

    Integer  theDayCount(@Param("companyId") Long companyId);

    Integer theMonthCount(@Param("companyId") Long companyId);

    List<CodeRecordCountVO> allDayCount();

    Integer agentTheDayCount(@Param("companyId") Long companyId);

    Integer agentTheMonthCount(@Param("companyId") Long companyId);

    int insertCodeRecordBatch(List<CodeRecordDO> recordList);

    CodeRecordDO findBySerialNumber(long serialNumber);
}

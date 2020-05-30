package com.tianma315.core.coderecord.service;

import com.tianma315.core.base.CoreService;
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
public interface CodeRecordService extends CoreService<CodeRecordDO> {
    Integer  theDayCount(Long companyId);

    Integer theMonthCount(Long companyId);

    List<Integer> allDayCount();

    Integer agentTheDayCount(Long companyId);

    Integer agentTheMonthCount(Long companyId);

    CodeRecordDO findBySerialNumber(long serialNumber);
}

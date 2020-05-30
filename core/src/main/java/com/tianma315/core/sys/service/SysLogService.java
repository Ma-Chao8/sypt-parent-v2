package com.tianma315.core.sys.service;

import com.tianma315.core.base.CoreService;
import com.tianma315.core.sys.domain.LogDO;
import com.tianma315.logger.provider.LogService;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月22日 | Aron</small>
 */
@Service
public interface SysLogService extends CoreService<LogDO>, LogService {

}

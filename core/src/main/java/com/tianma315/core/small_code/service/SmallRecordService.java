package com.tianma315.core.small_code.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.small_code.domain.SmallRecordDO;
import com.tianma315.core.small_code.domain.dto.SmallRecordDto;
import com.tianma315.core.small_code.vo.SmallRecordListVO;
import com.tianma315.core.small_code.vo.SmallRecordVO;

import java.util.List;

/**
 * 
 * <pre>
 * 外包装管理记录
 * </pre>
 * <small> 2019-08-10 10:06:48 | Wen</small>
 */
public interface SmallRecordService extends CoreService<SmallRecordDO> {
    Boolean addSmallRecord(SmallRecordVO smallRecordVO);

    Boolean addSmallRecordAndStock(SmallRecordVO smallRecordVO);

    SmallRecordListVO getSmallRecordListVOBySmallCode(String smallCode,Long companyId);

    SmallRecordListVO getSmallRecordListVOByBigCode(String bigCode,Long companyId);

    Page<SmallRecordVO> getRecordVOPage(Integer pageNumber, Integer pageSize, SmallRecordDto smallRecordDTO);

    Boolean inSmallRecord(SmallRecordVO smallRecordVO);
}

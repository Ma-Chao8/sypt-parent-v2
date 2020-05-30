package com.tianma315.core.invoice.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.invoice.domain.ReturnedDO;
import com.tianma315.core.invoice.domain.dto.ReturnedDto;
import com.tianma315.core.invoice.domain.vo.ReturnedVO;

import java.util.List;


/**
 * 
 * <pre>
 * 退货记录
 * </pre>
 * <small> 2019-08-21 14:57:42 | Lgc</small>
 */
public interface ReturnedDao extends CoreMapper<ReturnedDO> {

    List<ReturnedVO> getPage(Page<ReturnedVO> page, ReturnedDto returnedDTO);
}

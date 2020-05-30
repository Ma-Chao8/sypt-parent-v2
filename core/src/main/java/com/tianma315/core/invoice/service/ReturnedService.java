package com.tianma315.core.invoice.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.invoice.domain.ReturnedDO;
import com.tianma315.core.invoice.domain.dto.ReturnedDto;
import com.tianma315.core.invoice.domain.vo.ReturnedProductVO;
import com.tianma315.core.invoice.domain.vo.ReturnedVO;

import java.util.List;


/**
 * 
 * <pre>
 * 退货记录
 * </pre>
 * <small> 2019-08-21 14:57:42 | Lgc</small>
 */
public interface ReturnedService extends CoreService<ReturnedDO> {

    Page<ReturnedVO> getPage(Page<ReturnedVO> page, ReturnedDto returnedDTO);

    List<ReturnedProductVO> getDetails(Long returnedId);
}

package com.tianma315.core.invoice.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.invoice.domain.ReturnedProductCodeDO;
import com.tianma315.core.invoice.domain.vo.ReturnedProductCodeVO;
import org.apache.ibatis.annotations.Param;


/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-08-22 15:48:53 | Lgc</small>
 */
public interface ReturnedProductCodeDao extends CoreMapper<ReturnedProductCodeDO> {

    ReturnedProductCodeVO selectLastReturnedByCode(@Param("companyId") long companyId,@Param("invoiceId") long invoiceId,@Param("code") String code);
}

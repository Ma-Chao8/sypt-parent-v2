package com.tianma315.core.invoice.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.invoice.domain.InvoiceProductCodeDO;
import com.tianma315.core.invoice.domain.vo.InvoiceProductCodeVO;
import com.tianma315.core.invoice.domain.vo.InvoiceProductCodeValue;
import com.tianma315.core.invoice.vo.InvoiceDateVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 
 * <pre>
 * 货单产品码关联
 * </pre>
 * <small> 2019-08-23 10:03:22 | Lgc</small>
 */
public interface InvoiceProductCodeDao extends CoreMapper<InvoiceProductCodeDO> {

    InvoiceProductCodeVO selectLastDeliverByCode(@Param("companyId") long companyId, @Param("code") String code);

    List<InvoiceProductCodeValue> countCodeByYear(InvoiceDateVO invoiceDateVO);

    List<InvoiceProductCodeValue> countCodeByMonth(InvoiceDateVO invoiceDateVO);
}

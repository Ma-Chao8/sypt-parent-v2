package com.tianma315.core.invoice.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.invoice.domain.InvoiceProductCodeDO;
import com.tianma315.core.invoice.domain.InvoiceProductDO;
import com.tianma315.core.invoice.domain.vo.InvoiceProductVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 
 * <pre>
 * 货单产品
 * </pre>
 * <small> 2019-08-23 10:03:22 | Lgc</small>
 */
public interface InvoiceProductDao extends CoreMapper<InvoiceProductDO> {
    /**
     * 查询货单对应的产品
     *
     * @param invoiceId
     * @return
     */
    List<InvoiceProductVO> selectByInvoiceId(@Param("invoiceId") long invoiceId);
    /**
     * @param company_id
     * @param invoice_id
     * @param code
     * @return
     */
    InvoiceProductCodeDO selectEnableByCode(@Param("company_id") long company_id, @Param("invoice_id") long invoice_id, @Param("code") String code);
}

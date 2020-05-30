package com.tianma315.core.invoice.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.invoice.domain.InvoiceDO;
import com.tianma315.core.invoice.domain.dto.InvoiceDto;
import com.tianma315.core.invoice.domain.vo.DeliverRecordVo;
import com.tianma315.core.invoice.domain.vo.InspectVo;
import com.tianma315.core.invoice.domain.vo.InvoiceVO;
import com.tianma315.core.invoice.vo.DeliverForm;
import com.tianma315.core.invoice.vo.InvoiceDateVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;


/**
 * <pre>
 * 货单
 * </pre>
 * <small> 2019-08-21 14:56:27 | Lgc</small>
 */
public interface InvoiceService extends CoreService<InvoiceDO> {

    InvoiceVO getInvoiceDetails(Long invoiceId);

    Page<InvoiceVO> selectIncoicelist(Integer pageNumber, Integer pageSize, InvoiceDto invoiceDTO);

    InspectVo getInspect(Long companyId, String code, String productIdent);

    /**
     * 发货
     *
     * @param form
     * @param product_ident
     * @return
     */
    boolean deliver(DeliverForm form, long userId, long companyId, String product_ident);

    /**
     * 发货记录
     *
     * @param currentPage
     * @param pageSize
     * @param userId
     * @param searchKey
     * @return
     */
    Page<DeliverRecordVo> deliveredRecord(int currentPage, int pageSize, Long userId, String searchKey);


    /**
     * 退货
     *
     * @param codes
     * @param product_ident
     * @return
     */
    boolean returned(List<String> codes, long userId, long companyId, String product_ident);

    /**
     * 根据年份统计
     *
     * @param invoiceDateVO
     * @return
     */
    JSONObject countByYear(InvoiceDateVO invoiceDateVO);

    /**
     * 根据年月统计
     *
     * @param invoiceDateVO
     * @return
     */
    JSONObject countByMonth(InvoiceDateVO invoiceDateVO);


    JSONObject countCodeByYear(InvoiceDateVO invoiceDateVO);

    JSONObject countCodeByMonth(InvoiceDateVO invoiceDateVO);

    HSSFWorkbook exportInvoice(Long invoiceId);
}

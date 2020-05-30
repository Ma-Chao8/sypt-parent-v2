package com.tianma315.core.invoice.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.model.DataValues;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.invoice.domain.InvoiceDO;
import com.tianma315.core.invoice.domain.dto.InvoiceDto;
import com.tianma315.core.invoice.domain.vo.DeliverRecordVo;
import com.tianma315.core.invoice.domain.vo.InspectVo;
import com.tianma315.core.invoice.domain.vo.InvoiceVO;
import com.tianma315.core.invoice.vo.InvoiceDateVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * <pre>
 * 货单
 * </pre>
 * <small> 2019-08-21 14:56:27 | Lgc</small>
 */
public interface InvoiceDao extends CoreMapper<InvoiceDO> {

    /**
     * @param page
     * @param invoiceDTO
     * @return
     */
    List<InvoiceVO> selectPages(@Param("page") Page<InvoiceVO> page, InvoiceDto invoiceDTO);

    /**
     * @param invoiceId
     * @return
     */
    InvoiceVO selectDetail(Long invoiceId);

    /**
     * @param code
     * @return
     */
    InspectVo selectInspect(String code);

    /**
     * @param ids
     * @return
     */
    List<InvoiceVO> selectByIds(@Param("ids") Collection<Long> ids);

    /**
     * @param userId
     * @return
     */
    List<InvoiceVO> selectInvoicePage(@Param("page") Page page, @Param("userId") Long userId);

    /**
     * @param map
     * @return
     */
    List<String> selectByBigCode(Map map);

    /**
     * @param map
     * @return
     */
    List<String> selectBySmallCode(Map map);

    /**
     * @param invoiceDateVO
     * @return
     */
    List<DataValues> countByYear(InvoiceDateVO invoiceDateVO);

    /**
     * @param invoiceDateVO
     * @return
     */
    List<DataValues> countByMonth(InvoiceDateVO invoiceDateVO);

    /**
     * 大小码查询货单
     *
     * @param code
     * @return
     */
    InvoiceDO selectByCode(@Param("code") String code);

    /**
     * 发货记录
     *
     * @param page
     * @param userId
     * @param searchKey
     * @return
     */
    List<DeliverRecordVo> selectDeliveredRecord(@Param("page") Page<DeliverRecordVo> page, @Param("userId") Long userId, @Param("searchKey") String searchKey);
}

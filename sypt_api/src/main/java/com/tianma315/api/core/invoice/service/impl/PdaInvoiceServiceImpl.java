package com.tianma315.api.core.invoice.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.api.core.invoice.service.PdaInvoiceService;
import com.tianma315.core.invoice.domain.vo.DeliverRecordVo;
import com.tianma315.core.invoice.domain.vo.InspectVo;
import com.tianma315.core.invoice.service.impl.InvoiceServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by TianMa-Android on 2018/8/30.
 */
@Service
@Transactional(rollbackFor = Throwable.class)
public class PdaInvoiceServiceImpl extends InvoiceServiceImpl implements PdaInvoiceService {

    @Override
    public InspectVo getInspect(long merchant_id, String code, String product_ident) {
        return super.getInspect(merchant_id, code, product_ident);
    }


    @Override
    public InspectVo getInspect(Long companyId, String code, String productIdent) {
        return super.getInspect(companyId, code, productIdent);
    }

    @Override
    public Page<DeliverRecordVo> deliveredRecord(int currentPage, int pageSize, Long userId, String searchKey) {
        return super.deliveredRecord(currentPage, pageSize, userId,searchKey);
    }
}

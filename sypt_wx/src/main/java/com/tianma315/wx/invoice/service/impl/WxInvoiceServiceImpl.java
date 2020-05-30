package com.tianma315.wx.invoice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.tianma315.core.invoice.dao.InvoiceDao;
import com.tianma315.core.invoice.domain.InvoiceDO;
import com.tianma315.core.invoice.domain.vo.InspectVo;
import com.tianma315.core.invoice.domain.vo.InvoiceVO;
import com.tianma315.core.invoice.service.impl.InvoiceServiceImpl;
import com.tianma315.core.invoice.vo.DeliverForm;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.service.UserService;
import com.tianma315.wx.invoice.service.WxInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by TianMa-Android on 2018/8/30.
 */
@Service
public class WxInvoiceServiceImpl extends InvoiceServiceImpl implements WxInvoiceService {
    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    UserService userService;
    @Override
    public List<InvoiceVO> deliverList(Long userId, int page) {
        Page<InvoiceVO> pages = new Page<>(page, 10);
        List<InvoiceVO> list = invoiceDao.selectInvoicePage(pages,userId);
        return list;
    }

    @Override
    public InspectVo getInspect(long companyId, String code, String productIdent) {
        return super.getInspect(companyId,code,productIdent);
    }

    @Override
    public boolean returned(List<String> codes, Long id, long companyId, String productIdent) {
        return super.returned(codes,id,companyId,productIdent);
    }

    @Override
    public boolean deliver(DeliverForm form, Long userId, long companyId, String productIdent) {
        return super.deliver(form,userId,companyId,productIdent);
    }

}

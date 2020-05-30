package com.tianma315.core.invoice.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.invoice.dao.ReturnedProductDao;
import com.tianma315.core.invoice.domain.dto.ReturnedDto;
import com.tianma315.core.invoice.domain.vo.ReturnedProductVO;
import com.tianma315.core.invoice.domain.vo.ReturnedVO;
import com.tianma315.core.utils.BeanHump;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.invoice.dao.ReturnedDao;
import com.tianma315.core.invoice.domain.ReturnedDO;
import com.tianma315.core.invoice.service.ReturnedService;

import java.util.List;


/**
 * 
 * <pre>
 * 退货记录
 * </pre>
 * <small> 2019-08-21 14:57:42 | Lgc</small>
 */
@Service
public class ReturnedServiceImpl extends CoreServiceImpl<ReturnedDao, ReturnedDO> implements ReturnedService {

    @Autowired
    ReturnedDao returnedDao;
    @Autowired
    ReturnedProductDao returnedProductDao;

    @Override
    public Page<ReturnedVO> getPage(Page<ReturnedVO> page, ReturnedDto returnedDTO) {
        //System.out.println("returnedDTO======getBeginDate======="+returnedDTO.getBeginDate());
        //System.out.println("returnedDTO======getEndDate======="+returnedDTO.getEndDate());
        returnedDTO.setSortName(BeanHump.camelToUnderline(returnedDTO.getSortName()));
       List<ReturnedVO> list=returnedDao.getPage(page,returnedDTO);
       page.setRecords(list);
        return page;
    }

    @Override
    public List<ReturnedProductVO> getDetails(Long returnedId) {
        List<ReturnedProductVO> returnedProductVOS = returnedProductDao.selectByReturnedId(returnedId);
        return returnedProductVOS;
    }
}

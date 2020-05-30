package com.tianma315.core.procedures.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.procedures.dao.ProceduresProductDao;
import com.tianma315.core.procedures.domain.ProceduresProductDO;
import com.tianma315.core.procedures.service.ProceduresProductService;
import com.tianma315.core.procedures.vo.ProceduresProductVO;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
@Service
public class ProceduresProductServiceImpl extends CoreServiceImpl<ProceduresProductDao, ProceduresProductDO> implements ProceduresProductService {

    @Override
    public List<ProceduresProductDO> getProceduresProductDOlist(ProceduresProductDO proceduresProductDO) {
        EntityWrapper<ProceduresProductDO> entityWrapper = new EntityWrapper<>(proceduresProductDO);
        entityWrapper.eq("procedure_id",proceduresProductDO.getProcedureId());
        List<ProceduresProductDO> list = selectList(entityWrapper);
        return list;
    }

    @Override
    public void addProceduresProduct(ProceduresProductVO proceduresProductVO) {
        Integer procedureId = proceduresProductVO.getProcedureId();
        EntityWrapper<ProceduresProductDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("procedure_id",procedureId);
        //每次操作都要删除旧数据，保证数据最新
        delete(entityWrapper);
        List<Integer> list = proceduresProductVO.getProductIdList();
        for (Integer productId:list){
            ProceduresProductDO proceduresProductDO = new ProceduresProductDO();
            proceduresProductDO.setProcedureId(procedureId);
            proceduresProductDO.setProductId(productId);
            insert(proceduresProductDO);
        }
    }
}

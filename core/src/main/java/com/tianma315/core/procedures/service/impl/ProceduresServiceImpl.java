package com.tianma315.core.procedures.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.procedures.dao.ProceduresDao;
import com.tianma315.core.procedures.domain.ProceduresDO;
import com.tianma315.core.procedures.service.ProceduresService;
import com.tianma315.core.procedures.vo.ProceduresVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 
 * <pre>
 * 溯源流程模板
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
@Service
public class ProceduresServiceImpl extends CoreServiceImpl<ProceduresDao, ProceduresDO> implements ProceduresService {

    @Autowired
    private ProceduresDao proceduresDao;

    @Override
    public Page<ProceduresVO> selectPage(Integer pageNumber, Integer pageSize, ProceduresDO proceduresDTO) {
        // 查询列表数据
        Page<ProceduresVO> page = new Page<>(pageNumber, pageSize);
        List<ProceduresVO> proceduresVOList = proceduresDao.selectPage(page,proceduresDTO.getCompanyId());
        page.setRecords(proceduresVOList);
        return page;
    }

    @Override
    public Boolean addProcedures(ProceduresDO proceduresDO) {
        proceduresDO.setCreateDate(new Date());
        proceduresDO.setState(StateEnum.not_del.getIndex());
        Boolean result = insert(proceduresDO);
        return result;
    }

    @Override
    public Boolean updateProcedures(ProceduresDO proceduresDO) {
        return null;
    }

    @Override
    public Boolean deleteProcedures(Integer id) {
        ProceduresDO proceduresDO = new ProceduresDO();
        proceduresDO.setState(StateEnum.del.getIndex());
        proceduresDO.setProcedureId(id);
        Boolean result = updateById(proceduresDO);
        return result;
    }

    @Override
    public List<ProceduresDO> selectList(ProceduresDO proceduresDTO) {
        EntityWrapper<ProceduresDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("state",StateEnum.not_del.getIndex());
        entityWrapper.eq("company_id",proceduresDTO.getCompanyId());
        List<ProceduresDO> list = selectList(entityWrapper);
        return list;
    }

    @Override
    public ProceduresDO getProcedureDO(Integer id) {
        ProceduresDO procedureDO  = selectById(id);
        return procedureDO;
    }
}

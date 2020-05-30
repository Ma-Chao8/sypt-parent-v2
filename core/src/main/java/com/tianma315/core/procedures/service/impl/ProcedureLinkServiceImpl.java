package com.tianma315.core.procedures.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.procedures.dao.LinkRoleDao;
import com.tianma315.core.procedures.dao.ProcedureLinkDao;
import com.tianma315.core.procedures.domain.LinkRole;
import com.tianma315.core.procedures.domain.ProcedureLinkDO;
import com.tianma315.core.procedures.service.ProcedureLinkService;
import com.tianma315.core.procedures.vo.ProcedureLinkFileVO;
import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * <pre>
 * 环节
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
@Service
public class ProcedureLinkServiceImpl extends CoreServiceImpl<ProcedureLinkDao, ProcedureLinkDO> implements ProcedureLinkService {
    @Autowired
    private FileService fileService;

    @Autowired
    private ProcedureLinkDao procedureLinkDao;

    @Autowired
    private LinkRoleDao linkRoleDao;

    @Override
    public Page<ProcedureLinkFileVO> getProcedureLinkDOPage(Integer pageNumber, Integer pageSize, ProcedureLinkDO procedureLinkDTO) {
        Page<ProcedureLinkFileVO> page = new Page<>(pageNumber,pageSize);
        List<ProcedureLinkFileVO> list = procedureLinkDao.listProceduresVO(page,procedureLinkDTO.getCompanyId());
        page.setRecords(list);
        return page;
    }

    @Override
    public Boolean addProcedureLink(ProcedureLinkFileVO procedureLinkFileVO) {

        FileDO fileDO = new FileDO();

        if (!procedureLinkFileVO.getIconFile().isEmpty()){
            try {
                fileDO = fileService.upload(procedureLinkFileVO.getCreateBy(),procedureLinkFileVO.getIconFile().getBytes(),procedureLinkFileVO.getIconFile().getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        procedureLinkFileVO.setCreateDate(new Date());
        procedureLinkFileVO.setState(StateEnum.not_del.getIndex());
        procedureLinkFileVO.setIcon(fileDO.getUrl());
        Boolean result = insert(procedureLinkFileVO);


        Integer isAll = procedureLinkFileVO.getIsAll();
        String selectData = procedureLinkFileVO.getLinkRoles();
        //判断条件 添加角色权限
        if (isAll==0 && !selectData.isEmpty()){
            String[] split = selectData.split(",");
            List<LinkRole> linkRoleList = new ArrayList<>();
            for (int i = 0; i < split.length ; i++) {
                LinkRole linkRole = new LinkRole();
                linkRole.setProcedureLinkId(procedureLinkFileVO.getProcedureLinkId());
                linkRole.setRoleId(Integer.parseInt(split[i]));
                linkRole.setStatus(1);
                linkRoleList.add(linkRole);
            }
            procedureLinkDao.insertRole(linkRoleList);
        }
        return result;
    }

    @Override
    public Boolean deleteProcedureLink(Integer id) {
        ProcedureLinkDO procedureLinkDO = new ProcedureLinkDO();
        procedureLinkDO.setState(StateEnum.del.getIndex());
        procedureLinkDO.setProcedureLinkId(id);
        Boolean result = updateById(procedureLinkDO);
        return result;
    }

    @Override
    public List<ProcedureLinkDO> checkProcedureLinkSort(ProcedureLinkDO procedureLinkDO) {
        EntityWrapper<ProcedureLinkDO> entityWrapper = new EntityWrapper();
        entityWrapper.eq("company_id",procedureLinkDO.getCompanyId());
        entityWrapper.eq("sort",procedureLinkDO.getSort());
        entityWrapper.eq("state",StateEnum.not_del);
        entityWrapper.eq("procedures_id",procedureLinkDO.getProceduresId());
        if (procedureLinkDO.getProcedureLinkId() != null){
            entityWrapper.ne("procedure_link_id",procedureLinkDO.getProcedureLinkId());
        }
        List<ProcedureLinkDO> list = selectList(entityWrapper);
        return list;
    }

    @Override
    public List<ProcedureLinkDO> getProcedureLinkDOByProcedureId(Integer procedureId) {
        EntityWrapper<ProcedureLinkDO> wrapper = new EntityWrapper();
        wrapper.eq("state",StateEnum.not_del.getIndex());
        wrapper.eq("procedures_id",procedureId);
        wrapper.orderBy("sort");
        List<ProcedureLinkDO> linkDOS= selectList(wrapper);
        return linkDOS;
    }

    @Override
    public Boolean updateLinkById(ProcedureLinkFileVO procedureLinkFileVO) {
        updateById(procedureLinkFileVO);

        Integer isAll = procedureLinkFileVO.getIsAll();
        String selectData = procedureLinkFileVO.getLinkRoles();

        //修改该环节的所有的角色状态
        linkRoleDao.updateStatusByLinkId(procedureLinkFileVO.getProcedureLinkId());

        //判断条件 添加角色权限
        if (isAll==0 && !selectData.isEmpty()){
            String[] split = selectData.split(",");
            List<LinkRole> linkRoleList = new ArrayList<>();
            for (int i = 0; i < split.length ; i++) {
                LinkRole linkRole = new LinkRole();
                linkRole.setProcedureLinkId(procedureLinkFileVO.getProcedureLinkId());
                linkRole.setRoleId(Integer.parseInt(split[i]));
                linkRole.setStatus(1);
                linkRoleList.add(linkRole);
            }
            procedureLinkDao.insertRole(linkRoleList);
        }

        return null;
    }
}

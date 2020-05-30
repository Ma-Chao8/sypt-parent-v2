package com.tianma315.core.traceability.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.traceability.domain.TyMenuDataDO;
import com.tianma315.core.traceability.service.TyMenuDataService;
import com.tianma315.core.traceability.vo.TyMenuDataKyVO;
import com.tianma315.core.traceability.vo.TyMenuDataVO;
import com.tianma315.core.traceability.vo.TyMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.traceability.dao.TyMenuDao;
import com.tianma315.core.traceability.domain.TyMenuDO;
import com.tianma315.core.traceability.service.TyMenuService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 溯源档案菜单(环节)
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
@Service
public class TyMenuServiceImpl extends CoreServiceImpl<TyMenuDao, TyMenuDO> implements TyMenuService {

    @Autowired
    private TyMenuDao tyMenuDao;

    @Autowired
    private TyMenuDataService tyMenuDataService;

//    @Override
//    public Boolean deleteTyMenu(Integer traceabilityFileId) {
//        TyMenuDO tyMenu = new TyMenuDO();
//        tyMenu.setTraceabilityFileId(traceabilityFileId);
//        tyMenu.setIsDelete(StateEnum.del.getIndex());
//        EntityWrapper<TyMenuDO> tyMenuDOEntityWrapper = new EntityWrapper<>();
//        tyMenuDOEntityWrapper.eq("traceability_file_id",traceabilityFileId);
//        Boolean result = update(tyMenu,tyMenuDOEntityWrapper);
//        return result;
//    }

    @Override
    public List<TyMenuVO> getTyMenuByTraceabilityFileId(Integer traceabilityFileId) {
        List<TyMenuVO> tyMenuVOList = new ArrayList<>();
       /* EntityWrapper<TyMenuDO> wrapper = new EntityWrapper<>();
        wrapper.eq("is_delete", StateEnum.not_del.getIndex());
        wrapper.eq("traceability_file_id", traceabilityFileId);
        List<TyMenuDO> tyMenuList = selectList(wrapper);
        //根据menuId查找TyMenuData
        for (TyMenuDO tyMenu : tyMenuList) {
            TyMenuVO tyMenuVO = new TyMenuVO();
            EntityWrapper<TyMenuDataDO> tyMenuDataDOEntityWrapper = new EntityWrapper<>();
            tyMenuDataDOEntityWrapper.eq("is_delete", StateEnum.not_del.getIndex());
            tyMenuDataDOEntityWrapper.eq("menu_id", tyMenu.getMenuId());
            List<TyMenuDataDO> tyMenuDataList = tyMenuDataService.selectList(tyMenuDataDOEntityWrapper);
            tyMenuVO.setTyMenu(tyMenu);
            tyMenuVO.setDataList(tyMenuDataList);
            tyMenuVOList.add(tyMenuVO);
        }*/
        return tyMenuVOList;
    }

//    @Override
//    public TyMenuVO addTyMenuVO(TyMenuDataVO[] menuVO,Integer traceabilityFileId,Long userId) {
//        //每次编辑前，先将旧数据isDelete设置为1，保证每次插入是新数据
//        deleteTyMenu(traceabilityFileId);
//
//        for (TyMenuDataVO tv : menuVO){
//            TyMenuDO tyMenu = new TyMenuDO();
//            tyMenu.setMenuName(tv.getMenuName());
//            tyMenu.setCreateBy(userId);
//            tyMenu.setIsDelete(0);
//            tyMenu.setCreateDate(new Date());
//            tyMenu.setTraceabilityFileId(traceabilityFileId);
//            tyMenu.setMenuLevel(0);
//            tyMenu.setParentMenuId(0);
//            insert(tyMenu);
//
//            List<TyMenuDataKyVO> tyMenuDataKyVOS =  tv.getTyMenuDataList();
//            for (int i = 0; i < tyMenuDataKyVOS.size(); i++) {
//                TyMenuDataKyVO tkv = tyMenuDataKyVOS.get(i);
//                TyMenuDataDO tmd = new TyMenuDataDO();
//                tmd.setCreateBy(userId);
//                tmd.setCreateDate(new Date());
//                tmd.setDataKey(tkv.getDataKey());
//                tmd.setDataValue(tkv.getDataValue());
//                tmd.setMenuDataSort(i+1);
//                tmd.setMenuId(tyMenu.getMenuId());
//                tmd.setIsDelete(0);
//                tyMenuDataService.insert(tmd);
//            }
//        }
//
//        return null;
//    }
}

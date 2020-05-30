package com.tianma315.core.traceability.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.procedures.domain.LinkContentDO;
import com.tianma315.core.procedures.service.LinkContentService;
import com.tianma315.core.sys.service.FileService;
import com.tianma315.core.trace.vo.*;
import com.tianma315.core.traceability.vo.TyMenuDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.traceability.dao.TyMenuDataDao;
import com.tianma315.core.traceability.domain.TyMenuDataDO;
import com.tianma315.core.traceability.service.TyMenuDataService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * <pre>
 * 菜单数据
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
@Service
public class TyMenuDataServiceImpl extends CoreServiceImpl<TyMenuDataDao, TyMenuDataDO> implements TyMenuDataService {

    @Autowired
    LinkContentService linkContentService;
    @Autowired
    FileService fileService;
    @Autowired
    TyMenuDataDao tyMenuDataDao;

    @Override
    @Transactional
    public Boolean insertList(CodeVo codeVo, Integer menuId , Long userId) {

        //添加细节内容
        List<InputDo> inputDos = codeVo.getInputDos();
        for (int i = 0; i < inputDos.size(); i++) {
            InputDo inputDo = inputDos.get(i);
            TyMenuDataDO tyMenuDataDO = new TyMenuDataDO();
            tyMenuDataDO.setCreateBy(userId);
            tyMenuDataDO.setCreateDate(Calendar.getInstance().getTime());
            tyMenuDataDO.setDataValue(inputDo.getValue());
            tyMenuDataDO.setLinkContentId(inputDo.getId());
            LinkContentDO linkContentDO = linkContentService.selectById(inputDo.getId());
            tyMenuDataDO.setDataKey(linkContentDO.getContentName());
            tyMenuDataDO.setMenuId(menuId);
            tyMenuDataDO.setType(0);
            tyMenuDataDO.setMenuDataSort(linkContentDO.getSort());
            insert(tyMenuDataDO);
        }

        //是否有默认图片上传
        List<OldImagesDo> oldImgs = codeVo.getOldImgs();
        if ( !oldImgs.isEmpty()){
            for (int i = 0; i < oldImgs.size(); i++) {
                String url = "";
                Integer id = oldImgs.get(i).getId();
                LinkContentDO linkContentDO = linkContentService.selectById(id);
                List<String> srcList = oldImgs.get(i).getSrcList();
                if (!srcList.isEmpty()){
                    for (int j = 0; j < srcList.size(); j++) {
                        String s = srcList.get(j);
                        url += s +",";
                    }
                    insertData(menuId, userId, url, id, linkContentDO);
                }
            }
        }

        //以base64位形式图片上传
        List<ImagesDo> imgs = codeVo.getImgs();
        if (imgs != null && !imgs.isEmpty()){
            for (int i = 0; i < imgs.size(); i++) {
                String urls = "";
                Integer id = imgs.get(i).getId();
                LinkContentDO linkContentDO = linkContentService.selectById(id);
                List<ImgValueDo> value = imgs.get(i).getValue();
                for (int j = 0; j < value.size(); j++) {
                    ImgValueDo imgValueDo = value.get(j);
                    urls += fileService.uploadBase64(imgValueDo.getUrl(),imgValueDo.getSize())+",";
                }

                //查看是否有添加过
                Wrapper<TyMenuDataDO> wrapper = new EntityWrapper<>();
                //根据menuID和环节细节ID判断
                wrapper.eq("menu_id",menuId).eq("link_content_id",id);
                List<TyMenuDataDO> tyMenuDataDOS = selectList(wrapper);
                if (tyMenuDataDOS.isEmpty()) {
                    //为空，就说明没有默认图片
                    insertData(menuId, userId, urls, id, linkContentDO);
                }else{
                    //不为空，就说明默认图已添加
                    TyMenuDataDO tyMenuDataDO = tyMenuDataDOS.get(0);
                    tyMenuDataDO.setDataValue(urls+","+tyMenuDataDO.getDataValue());
                    updateById(tyMenuDataDO);
                }
            }
        }

        return true;
    }

    //添加数据
    private void insertData(Integer menuId, Long userId, String urls, Integer id, LinkContentDO linkContentDO) {
        TyMenuDataDO tyMenuDataDO = new TyMenuDataDO();
        tyMenuDataDO.setType(1);
        tyMenuDataDO.setMenuId(menuId);
        tyMenuDataDO.setLinkContentId(id);
        tyMenuDataDO.setDataValue(urls.substring(0, urls.length() - 1));
        tyMenuDataDO.setDataKey(linkContentDO.getContentName());
        tyMenuDataDO.setCreateBy(userId);
        tyMenuDataDO.setMenuDataSort(linkContentDO.getSort());
        tyMenuDataDO.setCreateDate(Calendar.getInstance().getTime());
        insert(tyMenuDataDO);
    }

    @Override
    public List<TyMenuDataVO> selectVOList(Integer menuId) {
        List<TyMenuDataVO> tyMenuDataList = tyMenuDataDao.selectVOList(menuId);
        return tyMenuDataList;
    }

    /**
     * 更新图片
     * @param codeVo
     * @return
     */
    @Override
    public boolean updateUrlById(CodeVo codeVo) {
        boolean isSuccess = false;

        List<OldImagesDo> oldImgs = codeVo.getOldImgs();
        if (oldImgs.size()>0){
            for (int i = 0; i < oldImgs.size(); i++) {
                String url = "";
                OldImagesDo oldImagesDo = oldImgs.get(i);
                List<String> srcList = oldImagesDo.getSrcList();
                if (srcList.size()==0){
                    TyMenuDataDO tyMenuDataDO = new TyMenuDataDO();
                    tyMenuDataDO.setMenuDataId(oldImagesDo.getId());
                    tyMenuDataDO.setDataValue("");
                    isSuccess = tyMenuDataDao.updateUrlById(tyMenuDataDO);
                }else if(srcList.size()>=1) {
                    for (int j = 0; j < srcList.size(); j++) {
                        url += srcList.get(j)+",";
                    }
                    TyMenuDataDO tyMenuDataDO = new TyMenuDataDO();
                    tyMenuDataDO.setMenuDataId(oldImagesDo.getId());
                    tyMenuDataDO.setDataValue(url.substring(0,url.length()-1));
                    isSuccess = updateById(tyMenuDataDO);
                }
            }
        }
        //是否有添加新图片上传
        List<ImagesDo> imgs = codeVo.getImgs();
        if (imgs != null && !imgs.isEmpty()) {
            for (int i = 0; i < imgs.size(); i++) {
                String urls = "";
                ImagesDo imagesDo = imgs.get(i);
                List<ImgValueDo> value = imagesDo.getValue();
                if (value == null || value.isEmpty()){
                    continue;
                }
                Integer id = imagesDo.getId();
//                LinkContentDO linkContentDO = linkContentService.selectById(id);
                for (int j = 0; j < value.size(); j++) {
                    ImgValueDo imgValueDo = value.get(j);
                    urls += fileService.uploadBase64(imgValueDo.getUrl(), imgValueDo.getSize()) + ",";
                }
                TyMenuDataDO tyMenuData = selectById(id);
                TyMenuDataDO tyMenuDataDO = new TyMenuDataDO();
                tyMenuDataDO.setMenuDataId(id);
                if (tyMenuData!=null){
                    String dataValue = tyMenuData.getDataValue();
                    if (dataValue.equals("") ||dataValue.isEmpty()){
                        tyMenuDataDO.setDataValue(urls.substring(0, urls.length() - 1));
                    }else{
                        tyMenuDataDO.setDataValue(dataValue+","+urls.substring(0, urls.length() - 1));
                    }
                }

                isSuccess = updateById(tyMenuDataDO);

            }
        }
        return isSuccess;
    }

    /**
     * 更新数据
     * @param codeVo
     * @return
     */
    @Override
    public boolean updateDataById(CodeVo codeVo) {
        boolean isYes = false;
        List<InputDo> inputDos = codeVo.getInputDos();
        List<TyMenuDataDO> tyMenuDataList = new ArrayList<>();
        if (inputDos.size()>0){
            for (int i = 0; i <inputDos.size() ; i++) {
                TyMenuDataDO tyMenuDataDO = new TyMenuDataDO();
                tyMenuDataDO.setMenuDataId(inputDos.get(i).getId());
                tyMenuDataDO.setDataValue(inputDos.get(i).getValue());
                tyMenuDataList.add(tyMenuDataDO);
            }
            if (tyMenuDataList!=null || tyMenuDataList.size()>0){
                for (int i = 0; i < tyMenuDataList.size(); i++) {
                    isYes = updateById(tyMenuDataList.get(i));
                }
            }
        }
        return isYes;
    }


}

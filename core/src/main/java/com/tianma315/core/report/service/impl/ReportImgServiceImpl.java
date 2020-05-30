package com.tianma315.core.report.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.report.domain.ReportDO;
import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.report.dao.ReportImgDao;
import com.tianma315.core.report.domain.ReportImgDO;
import com.tianma315.core.report.service.ReportImgService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-06-25 13:58:06 | Wen</small>
 */
@Service
public class ReportImgServiceImpl extends CoreServiceImpl<ReportImgDao, ReportImgDO> implements ReportImgService {

    @Autowired
    private FileService fileService;

    @Autowired
    private ReportImgDao reportImgDao;

    @Override
    public void insertImg(HttpServletRequest request, ReportImgDO reportImgDO,Long userId){
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
        Set<Map.Entry<String, MultipartFile>> eSet = fileMap.entrySet();
        for (Map.Entry<String, MultipartFile> entry : eSet) {
            MultipartFile mFile = entry.getValue();
            FileDO fileDO = null;
            try {
                fileDO = fileService.upload(userId,mFile.getBytes(),mFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
            reportImgDO.setImgSrc(fileDO.getUrl());
            reportImgDO.setState(StateEnum.not_del.getIndex());
            insert(reportImgDO);
        }

    }

    @Override
    public Boolean deleteReportImg(Integer id) {
        ReportImgDO reportImgDO = new ReportImgDO();
        reportImgDO.setState(StateEnum.del.getIndex());
        reportImgDO.setReportImgId(id);
        Boolean result = updateById(reportImgDO);
        return result;
    }

    @Override
    public List<ReportImgDO> getReportImgByReportId(Integer reportId) {
        EntityWrapper<ReportImgDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("report_id",reportId);
        entityWrapper.eq("state",StateEnum.not_del.getIndex());
        List<ReportImgDO> list = selectList(entityWrapper);
        return list;
    }

    @Override
    public Integer countAll() {
        return reportImgDao.countAll();
    }
}

//package com.tianma315.core.traceability.service.impl;
//
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.tianma315.commons.enumutil.StateEnum;
//import com.tianma315.core.base.CoreServiceImpl;
//import com.tianma315.core.sys.domain.FileDO;
//import com.tianma315.core.sys.service.FileService;
//import com.tianma315.core.traceability.dao.ReportDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.tianma315.core.traceability.domain.ReportDO;
//import com.tianma315.core.traceability.service.ReportService;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// *
// * <pre>
// * 检测报告
// * </pre>
// * <small> 2019-06-20 13:49:53 | Wen</small>
// */
//@Service
//public class ReportServiceImpl extends CoreServiceImpl<ReportDao, ReportDO> implements ReportService {
//
//    @Autowired
//    private FileService fileService;
//
//    @Override
//    public Map uploadPictures(HttpServletRequest request, ReportDO report) throws Exception {
//        report.setState(StateEnum.not_del.getIndex());
//        //上传文件
//        Map cr = new HashMap();
//        cr.put("message","上传成功");
//        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
//        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
//        Set<Map.Entry<String, MultipartFile>> eSet = fileMap.entrySet();
//        for (Map.Entry<String, MultipartFile> entry : eSet) {
//            MultipartFile mFile = entry.getValue();
//            FileDO fileDO = fileService.upload(mFile.getBytes(),mFile.getOriginalFilename());
//            report.setImgUrl(fileDO.getUrl());
//            insert(report);
//        }
//
//        return cr;
//    }
//
//    @Override
//    public Page<ReportDO> getReportDOList(Page page, Integer traceabilityFileId) {
//        EntityWrapper entityWrapper = new EntityWrapper();
//        entityWrapper.eq("state", StateEnum.not_del.getIndex());
//        entityWrapper.eq("traceability_file_id",traceabilityFileId);
//        page = selectPage(page,entityWrapper);
//        return page;
//    }
//}

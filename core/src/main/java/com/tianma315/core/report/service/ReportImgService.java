package com.tianma315.core.report.service;

import com.tianma315.core.base.CoreService;
import com.tianma315.core.report.domain.ReportImgDO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <pre>
 * 
 * </pre>
 * <small> 2019-06-25 13:58:06 | Wen</small>
 */
public interface ReportImgService extends CoreService<ReportImgDO> {

    void insertImg(HttpServletRequest request,ReportImgDO reportImgDO,Long userId);

    Boolean deleteReportImg(Integer id);

    List<ReportImgDO> getReportImgByReportId(Integer reportId);

    Integer countAll();
    
}

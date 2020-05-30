package com.tianma315.core.report.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.report.domain.ReportDO;
import com.tianma315.core.report.dto.ReportDto;
import com.tianma315.core.report.vo.ReportAnyDayCountVO;
import com.tianma315.core.report.vo.ReportCountVO;
import com.tianma315.core.report.vo.ReportVO;

import java.util.Date;
import java.util.List;

/**
 * 
 * <pre>
 * 检测报告
 * </pre>
 * <small> 2019-06-25 13:58:06 | Wen</small>
 */
public interface ReportService extends CoreService<ReportDO> {

    Page<ReportVO> getReportDOPage(Integer pageNumber, Integer pageSize, ReportDto reportDO, Date beginDate, Date endDate);

    Boolean addReportDO(ReportDO reportDO);

    Boolean deleteReportDO(Integer id);

    Integer countDay();
    Integer countMonth();
    Integer countAll();
    List<String> newestRecord();
    List<ReportCountVO> checkRank();
    List<ReportCountVO> checkTypeProportion();
    List<ReportCountVO> checkMechanismProportion();
    List<Integer> reportAnyDayCount();

    List<ReportDO> getReportDOList(Long companyId);

    ReportVO getReportDO(Long reportDOId);

}

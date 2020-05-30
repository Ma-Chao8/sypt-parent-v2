package com.tianma315.core.report.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.report.domain.ReportDO;
import com.tianma315.core.report.vo.ReportAnyDayCountVO;
import com.tianma315.core.report.vo.ReportCountVO;
import com.tianma315.core.report.vo.ReportVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 
 * <pre>
 * 检测报告
 * </pre>
 * <small> 2019-06-25 13:58:06 | Wen</small>
 */
public interface ReportDao extends CoreMapper<ReportDO> {

    List<ReportVO> getReportDO(@Param("page")Page page, @Param("report") ReportDO reportDO,@Param("beginDate")Date beginDate,@Param("endDate") Date endDate);
//    List<ReportVO> getReportDO( ReportDO reportDO);
    Integer countDay();
    Integer countMonth();
    Integer countAll();
    List<String> newestRecord();
    List<ReportCountVO> checkRank();
    List<ReportCountVO> checkTypeProportion();
    List<ReportCountVO> checkMechanismProportion();
    List<ReportAnyDayCountVO> reportAnyDayCount();
}

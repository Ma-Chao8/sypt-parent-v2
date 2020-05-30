package com.tianma315.core.report.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.coderecord.vo.CodeRecordCountVO;
import com.tianma315.core.company.domain.dto.CompanyDto;
import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.report.domain.CheckMechanismDO;
import com.tianma315.core.report.domain.CheckTypeDO;
import com.tianma315.core.report.domain.ReportImgDO;
import com.tianma315.core.report.dto.ReportDto;
import com.tianma315.core.report.service.CheckMechanismService;
import com.tianma315.core.report.service.CheckTypeService;
import com.tianma315.core.report.service.ReportImgService;
import com.tianma315.core.report.vo.ReportAnyDayCountVO;
import com.tianma315.core.report.vo.ReportCountVO;
import com.tianma315.core.report.vo.ReportVO;
import com.tianma315.core.utils.BeanHump;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.report.dao.ReportDao;
import com.tianma315.core.report.domain.ReportDO;
import com.tianma315.core.report.service.ReportService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * <pre>
 * 检测报告
 * </pre>
 * <small> 2019-06-25 13:58:06 | Wen</small>
 */
@Service
public class ReportServiceImpl extends CoreServiceImpl<ReportDao, ReportDO> implements ReportService {
    @Autowired
    private ReportImgService reportImgService;

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private CheckMechanismService checkMechanismService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CheckTypeService checkTypeService;

    @Override
    public Page<ReportVO> getReportDOPage(Integer pageNumber, Integer pageSize, ReportDto reportDO, Date beginDate, Date endDate) {
        reportDO.setSortName(BeanHump.camelToUnderline(reportDO.getSortName()));
        Page<ReportVO> page = new Page(pageNumber,pageSize,reportDO.getSortName());
        if (reportDO.getSortOrder().equals("desc"))
        {
            page.setAsc(false);
        }
        List<ReportVO> reportVOS = reportDao.getReportDO(page,reportDO,beginDate,endDate);
        page.setRecords(reportVOS);
//        EntityWrapper<ReportDO> entityWrapper = new EntityWrapper<>();
//        entityWrapper.eq("state", StateEnum.not_del.getIndex());
//        entityWrapper.eq("company_id",reportDO.getCompanyId());
//        page = selectPage(page,entityWrapper);
//
//        List<ReportDO> list = page.getRecords();
//        List<Integer> reportIdList = list.stream().map(item ->{return item.getReportId();}).collect(Collectors.toList());
//
//        //根据reportId查找reportImg
//        EntityWrapper<ReportImgDO> imgWrapper = new EntityWrapper<>();
//        imgWrapper.eq("report_id",reportIdList);
//        List<ReportImgDO> reportImgDOS = reportImgService.selectList(imgWrapper);
//        //取出reportId的值
//        List<Integer> reportImgReportIdList = reportImgDOS.stream()
//                .map(item -> {return item.getReportId();}).collect(Collectors.toList());
//
//        List<ReportVO> resultList = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            ReportDO report = list.get(i);
//            ReportVO reportVO = new ReportVO();
//            if (reportImgReportIdList.contains(report.getReportId())){
////                report.setIsBind(1);//如果有绑定图片设置为1
//                BeanUtils.copyProperties(report,reportVO);
//                reportVO.setIsBind(1);
//                resultList.add(reportVO);
//            }
//        }
//        page.setRecords(resultList);
        return page;
    }

    @Override
    public Boolean addReportDO(ReportDO reportDO) {
        reportDO.setState(StateEnum.not_del.getIndex());
        Boolean result = insert(reportDO);
        return result;
    }

    @Override
    public Boolean deleteReportDO(Integer id) {
        ReportDO reportDO = new ReportDO();
        reportDO.setReportId(id);
        reportDO.setState(StateEnum.del.getIndex());
        Boolean result = updateById(reportDO);
        return result;
    }

    @Override
    public Integer countDay() {
        return reportDao.countDay();
    }

    @Override
    public Integer countMonth() {
        return reportDao.countMonth();
    }

    @Override
    public Integer countAll() {
        return reportDao.countAll();
    }

    @Override
    public List<String> newestRecord() {
        return reportDao.newestRecord();
    }

    @Override
    public List<ReportCountVO> checkRank() {
        List<ReportCountVO> list = reportDao.checkRank();
        return list;
    }

    @Override
    public List<ReportCountVO> checkTypeProportion() {
        List<ReportCountVO> list = reportDao.checkTypeProportion();
        return list;
    }

    @Override
    public List<ReportCountVO> checkMechanismProportion() {
        List<ReportCountVO> list = reportDao.checkMechanismProportion();
        return list;
    }

    @Override
    public List<Integer> reportAnyDayCount() {
        List<ReportAnyDayCountVO> list = reportDao.reportAnyDayCount();
        List<Integer> dayList = list.stream()
                .map(item -> item.getNowDay())
                .collect(Collectors.toList());
        List<ReportAnyDayCountVO> resultList = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            ReportAnyDayCountVO result = new ReportAnyDayCountVO();
            if (!dayList.contains(i)){
                result.setNowDay(i);
                result.setNum(0);
                resultList.add(result);
            }
        }
        resultList.addAll(list);
        List<ReportAnyDayCountVO> count = resultList.stream().sorted((a,b) -> a.getNowDay()-b.getNowDay()).collect(Collectors.toList());
        count.stream().forEach(System.out::println);
        List<Integer> resultCount = count.stream().map(item -> item.getNum()).collect(Collectors.toList());
        return resultCount;
    }

    @Override
    public List<ReportDO> getReportDOList(Long companyId) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("state",StateEnum.not_del.getIndex());
        entityWrapper.eq("company_id",companyId);
        List<ReportDO> reportDOList = selectList(entityWrapper);
        return reportDOList;
    }

    @Override
    public ReportVO getReportDO(Long reportDOId) {
        ReportDO reportDO = selectById(reportDOId);
        ReportVO reportVO = new ReportVO();
        if (reportDO!=null){
            List<ReportImgDO> reportImgDOS = reportImgService.getReportImgByReportId(reportDOId.intValue());
            CheckMechanismDO checkMechanismDO = checkMechanismService.selectById(reportDO.getCheckMechanismId());
            CheckTypeDO checkTypeDO = checkTypeService.selectById(reportDO.getCheckTypeId());
            Company company = companyService.getById(reportDO.getCompanyId());
            BeanUtils.copyProperties(reportDO,reportVO);
            reportVO.setReportImgDOList(reportImgDOS);
            reportVO.setCompanyName(company.getCompanyName());
            reportVO.setCheckTypeName(checkTypeDO.getCheckTypeName());
            if (checkMechanismDO!=null){
                reportVO.setCheckMechanismName(checkMechanismDO.getCheckMechanismName());
            }
        }

        return reportVO;
    }

}

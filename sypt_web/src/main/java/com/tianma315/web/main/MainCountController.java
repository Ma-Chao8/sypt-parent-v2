package com.tianma315.web.main;

import com.tianma315.core.base.Result;
import com.tianma315.core.coderecord.service.CodeRecordService;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.company.domain.vo.CompanyCountLevelVO;
import com.tianma315.core.company.domain.vo.CompanyCountRankVO;
import com.tianma315.core.report.service.CheckMechanismService;
import com.tianma315.core.report.service.CheckTypeService;
import com.tianma315.core.report.service.ReportImgService;
import com.tianma315.core.report.service.ReportService;
import com.tianma315.core.report.vo.ReportCountVO;
import com.tianma315.core.wxuserinfo.service.WxUserInfoService;
import com.tianma315.core.wxuserinfo.vo.WxUserInfoVO;
import com.tianma315.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/main")
public class MainCountController extends BaseController{
    @Autowired
    private CodeRecordService codeRecordService;
    @Autowired
    private CompanyService companyService;
//    @Autowired
//    private TraceFileBindService traceFileBindService;
    @Autowired
    private WxUserInfoService wxUserInfoService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private ReportImgService reportImgService;
    @Autowired
    private CheckTypeService checkTypeService;
    @Autowired
    private CheckMechanismService checkMechanismService;

    @GetMapping("/count")
    public Result count(){
        Map map = new HashMap<>();
        Integer dayCount = codeRecordService.theDayCount(getCompanyId());
        Integer monthCount = codeRecordService.theMonthCount(getCompanyId());
        List<Integer> anyDayList = codeRecordService.allDayCount();
        List<CompanyCountLevelVO> companyCountLevelVOS = companyService.countLevel();
        List<CompanyCountRankVO> companyCountRankVOS = companyService.countRank();
        Integer countNum = 0;//traceFileBindService.countNum();
        Integer countMonthByUser = wxUserInfoService.countMonthNum();
        if(dayCount == null) dayCount=0;
        if (monthCount == null) monthCount=0;
        if(countNum == null) countNum =0;
        map.put("dayCount",dayCount);
        map.put("monthCount",monthCount);
        map.put("anyDayList",anyDayList);
        map.put("companyCountLevelVOS",companyCountLevelVOS);
        map.put("companyCountRankVOS",companyCountRankVOS);
        map.put("countNum",countNum);
        map.put("countMonthByUser",countMonthByUser);

        return Result.ok(map);
    }

    @GetMapping("/reportCount")
    public Result reportCount(){
        Integer countDay = reportService.countDay();
        Integer countMonth = reportService.countMonth();
        Integer countAll = reportService.countAll();
        List<String> newestRecord = reportService.newestRecord();
        List<ReportCountVO> checkMechanismProportion = reportService.checkMechanismProportion();
        List<ReportCountVO> checkRank = reportService.checkRank();
        List<ReportCountVO> checkTypeProportion = reportService.checkTypeProportion();
        List<Integer> reportAnyDayCount = reportService.reportAnyDayCount();
        List<String> checkTypeDOS = checkTypeService.getAllName();
        List<String> checkMechanismDOS = checkMechanismService.getAllName();


        Integer reportImgCountAll = reportImgService.countAll();

        if(countDay == null) countDay=0;
        if(countMonth == null) countMonth=0;
        Map map = new HashMap();
        map.put("countDay",countDay);
        map.put("countMonth",countMonth);
        map.put("countAll",countAll);
        map.put("checkMechanismProportion",checkMechanismProportion);
        map.put("checkRank",checkRank);
        map.put("newestRecord",newestRecord);
        map.put("checkTypeProportion",checkTypeProportion);
        map.put("reportAnyDayCount",reportAnyDayCount);
        map.put("reportImgCountAll",reportImgCountAll);
        map.put("checkTypeDOS",checkTypeDOS);
        map.put("checkMechanismDOS",checkMechanismDOS);

        return Result.ok(map);
    }

    @GetMapping("/agentMainCount")
    public Result agentMainCount(){
        Integer dayCount = codeRecordService.agentTheDayCount(getCompanyId());
        Integer monthCount = codeRecordService.agentTheMonthCount(getCompanyId());
        Integer traceFileBindCountDay =0;// traceFileBindService.countDay(getCompanyId());
        Integer traceFileBindCountCount =0;// traceFileBindService.countMonth(getCompanyId());

        List<WxUserInfoVO> wxUserInfoVOList = wxUserInfoService.countProvinceUser(getCompanyId());

        if(dayCount == null) dayCount=0;
        if(monthCount == null) monthCount=0;
        Map map = new HashMap();
        map.put("agentDayCount",dayCount);
        map.put("agentMonthCount",monthCount);
        map.put("traceFileBindCountDay",traceFileBindCountDay);
        map.put("traceFileBindCountCount",traceFileBindCountCount);
        map.put("wxUserInfoVOList",wxUserInfoVOList);

        return Result.ok(map);
    }

}

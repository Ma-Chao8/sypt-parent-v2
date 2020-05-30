package com.tianma315.core.coderecord.service.impl;

import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.coderecord.vo.CodeRecordCountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.coderecord.dao.CodeRecordDao;
import com.tianma315.core.coderecord.domain.CodeRecordDO;
import com.tianma315.core.coderecord.service.CodeRecordService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * <pre>
 * 溯源码查询记录
 * </pre>
 * <small> 2019-06-26 16:36:01 | Wen</small>
 */
@Service
public class CodeRecordServiceImpl extends CoreServiceImpl<CodeRecordDao, CodeRecordDO> implements CodeRecordService {

    @Autowired
    private CodeRecordDao codeRecordDao;

    @Override
    public Integer theDayCount(Long companyId) {
        return codeRecordDao.theDayCount(companyId);
    }

    @Override
    public Integer theMonthCount(Long companyId) {
        return codeRecordDao.theMonthCount(companyId);
    }

    @Override
    public List<Integer> allDayCount() {
        List<CodeRecordCountVO> codeRecordCountVOS = codeRecordDao.allDayCount();
        List<Integer> dayList = codeRecordCountVOS.stream()
                .map(item -> item.getNowDay())
                .collect(Collectors.toList());
        List<CodeRecordCountVO> resultList = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            CodeRecordCountVO result = new CodeRecordCountVO();
            if (!dayList.contains(i)){
                result.setNowDay(i);
                result.setNum(0);
                resultList.add(result);
            }
        }
        resultList.addAll(codeRecordCountVOS);
        List<CodeRecordCountVO> count = resultList.stream().sorted((a,b) -> a.getNowDay()-b.getNowDay()).collect(Collectors.toList());
        count.stream().forEach(System.out::println);
        List<Integer> resultCount = count.stream().map(item -> item.getNum()).collect(Collectors.toList());
        return resultCount;
    }

    @Override
    public Integer agentTheDayCount(Long companyId) {
        Integer dayCount = 0;
        dayCount = codeRecordDao.agentTheDayCount(companyId);
        return dayCount;
    }

    @Override
    public Integer agentTheMonthCount(Long companyId) {
        return codeRecordDao.agentTheMonthCount(companyId);

    }

    @Override
    public CodeRecordDO findBySerialNumber(long serialNumber) {
        return codeRecordDao.findBySerialNumber(serialNumber);
    }
}

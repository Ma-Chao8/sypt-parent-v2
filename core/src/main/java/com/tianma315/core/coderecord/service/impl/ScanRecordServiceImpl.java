package com.tianma315.core.coderecord.service.impl;

import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.coderecord.dao.ScanRecordMapper;
import com.tianma315.core.coderecord.domain.pojo.ScanRecord;
import com.tianma315.core.coderecord.service.ScanRecordService;
import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.wxuserinfo.domain.WxUserInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//                   _ooOoo_
//                  o8888888o
//                  88" . "88
//                  (| -_- |)
//                  O\  =  /O
//               ____/`---'\____
//             .'  \\|     |//  `.
//            /  \\|||  :  |||//  \
//           /  _||||| -:- |||||-  \
//           |   | \\\  -  /// |   |
//           | \_|  ''\---/''  |   |
//           \  .-\__  `-`  ___/-. /
//         ___`. .'  /--.--\  `. . __
//      ."" '<  `.___\_<|>_/___.'  >'"".
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//     \  \ `-.   \_ __\ /__ _/   .-` /  /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//         佛祖保佑       永无BUG

/**
 * Description
 * <p>
 * Created by zcm on 2019/7/4.
 */
@Service
public class ScanRecordServiceImpl extends CoreServiceImpl<ScanRecordMapper, ScanRecord> implements ScanRecordService {

    @Autowired
    private CompanyService companyService;
//    @Autowired
//    private TraceFileMapper traceFileMapper;


    @Override
    public Map<String, String> getCountByDay(String date, Long userId) {
        Company company = companyService.getByUserId(userId);
        if (company == null) {
            throw new MessageException("商户信息获取失败");
        }

        Calendar targetDate = parseDate(date, "yyyy-MM");
        List<Map> maps = baseMapper.selectCountByDay(company.getCompanyId(), targetDate.get(Calendar.YEAR), targetDate.get(Calendar.MONTH) + 1);
        //创建当前月数据模型
        Map<String, String> data = new TreeMap<>();
        for (int i = 1; i <= targetDate.getMaximum(Calendar.DAY_OF_MONTH) + 1; i++) {
            data.put(String.valueOf(i), "0");
        }
        maps.forEach(item -> {
            data.put(String.valueOf(item.get("day")), String.valueOf(item.get("count")));
        });
        return data;
    }

    @Override
    public Map<String, String> getCountByMonth(String date, Long userId) {
        Company company = companyService.getByUserId(userId);
        if (company == null) {
            throw new MessageException("商户信息获取失败");
        }

        Calendar targetDate = parseDate(date, "yyyy");
        List<Map> maps = baseMapper.selectCountByMonth(company.getCompanyId(), targetDate.get(Calendar.YEAR));

        //创建十二个月的数据模型
        Map<String, String> data = new TreeMap<>();
        for (int i = 1; i <= 12; i++) {
            data.put(String.valueOf(i), "0");
        }

        maps.forEach(item -> {
            data.put(String.valueOf(item.get("month")), String.valueOf(item.get("count")));
        });
        return data;
    }

    @Override
    public Map<String, String> getCountByYear(int year, Long userId) {
        Company company = companyService.getByUserId(userId);
        if (company == null) {
            throw new MessageException("商户信息获取失败");
        }

        Calendar calendar = getCalendar(new Date());
        int startYear = calendar.get(Calendar.YEAR) - year;
        int endYear = calendar.get(Calendar.YEAR);
        List<Map> maps = baseMapper.selectCountByYear(company.getCompanyId(), startYear, endYear);
        //创建十二个月的数据模型
        Map<String, String> data = new TreeMap<>();
        for (int i = 0; i < year; i++) {
            data.put(String.valueOf(calendar.get(Calendar.YEAR) - i), "0");
        }

        maps.forEach(item -> {
            data.put(String.valueOf(item.get("year")), String.valueOf(item.get("count")));
        });
        return data;
    }

    @Override
    public boolean editLocation(WxUserInfoDO wxUserInfo, String code) {
//        Wrapper<TraceFile> traceWrapper = new EntityWrapper<>();
//        traceWrapper.eq("", "");
//        List<TraceFile> traceFiles = traceFileMapper.selectList(traceWrapper);
//        if (traceFiles == null || traceFiles.isEmpty()) {
//            throw new MessageException(String.format("%s 尚未出库", code));
//        }
//        TraceFile traceFile = traceFiles.get(0);

        ScanRecord scanRecord;// = baseMapper.selectByCode(traceFile.getTraceFileId(), code);
        scanRecord = null;

        if (scanRecord == null) {
            //添加扫码记录
            scanRecord = new ScanRecord();
            scanRecord.setCode(code);
//            scanRecord.setTraceFileId(traceFile.getTraceFileId());
            scanRecord.setCreatedDate(new Date());
            scanRecord.setScanNumber(1);
            scanRecord.setOpenId(wxUserInfo.getOpenid());
//            scanRecord.setCompanyId(traceFile.getCompanyId());
//            scanRecord.setProvinceId(wxUserInfo.getProvince());
            baseMapper.insert(scanRecord);
        } else {
            //更新扫码信息
            scanRecord.setScanNumber(scanRecord.getScanNumber() + 1);
            baseMapper.updateById(scanRecord);
        }

        return true;
    }


    /**
     * 格式化日期
     *
     * @param date
     * @param format
     * @return
     */
    private Calendar parseDate(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return getCalendar(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new MessageException(String.format("日期格式错误，正确格式为%s", format));
        }
    }

    /**
     * @param date
     * @return
     */
    private Calendar getCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
}

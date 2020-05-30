package com.tianma315.core.coderecord.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.coderecord.domain.pojo.ScanRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
public interface ScanRecordMapper extends CoreMapper<ScanRecord> {

    /**
     * 按月查询每天扫码量
     *
     * @param companyId
     * @param year
     * @param month
     * @return
     */
    List<Map> selectCountByDay(@Param("companyId") long companyId, @Param("year") int year, @Param("month") int month);

    /**
     * 按年查询每月扫码量
     *
     * @param companyId
     * @param date
     * @return
     */
    List<Map> selectCountByMonth(@Param("companyId") long companyId, @Param("date") int date);

    /**
     * 查询连续几年的扫码量
     *
     * @param companyId
     * @param startYear
     * @param endYear
     * @return
     */
    List<Map> selectCountByYear(@Param("companyId") long companyId, @Param("startYear") int startYear, @Param("endYear") int endYear);

    /**
     * 查询扫码记录
     *
     * @param traceFileId
     * @param code
     * @return
     */
    ScanRecord selectByCode(@Param("traceFileId") long traceFileId, @Param("code") String code);

    /**
     * 查询扫码记录
     *
     * @param code
     * @return
     */
    ScanRecord selectRecordByCode(@Param("code") String code);
}

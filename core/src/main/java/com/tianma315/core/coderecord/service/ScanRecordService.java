package com.tianma315.core.coderecord.service;

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

import com.tianma315.core.wxuserinfo.domain.WxUserInfoDO;

import java.util.Map;

/**
 * Description
 * <p>
 * Created by zcm on 2019/7/4.
 */
public interface ScanRecordService {

    /**
     * @param date
     * @param userId
     * @return
     */
    Map<String, String> getCountByDay(String date, Long userId);

    /**
     * @param date
     * @param userId
     * @return
     */
    Map<String, String> getCountByMonth(String date, Long userId);

    /**
     * @param year
     * @param userId
     * @return
     */
    Map<String, String> getCountByYear(int year, Long userId);

    /**
     * @param wxUserInfo
     * @param code
     * @return
     */
    boolean editLocation(WxUserInfoDO wxUserInfo, String code);
}

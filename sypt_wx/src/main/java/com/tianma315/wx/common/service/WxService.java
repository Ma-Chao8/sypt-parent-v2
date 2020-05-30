package com.tianma315.wx.common.service;

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
import com.tianma315.wx.common.model.WxConfig;

/**
 * Description
 * <p>
 * Created by zcm on 2019/8/10.
 */
public interface WxService {

    String getAppId();

    String getAppSecret();

    String getRedirectUrl();


    /**
     * @param url
     * @return
     */
    WxConfig getWxConfig(String url);


    /**
     * @param code
     * @return
     */
    WxUserInfoDO getWxUserInfo(String code);
}

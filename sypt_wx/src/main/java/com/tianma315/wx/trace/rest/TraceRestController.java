package com.tianma315.wx.trace.rest;

import com.tianma315.core.base.Result;
import com.tianma315.core.coderecord.service.ScanRecordService;
import com.tianma315.core.social.domain.WxLocation;
import com.tianma315.core.wxuserinfo.domain.WxUserInfoDO;
import com.tianma315.core.wxuserinfo.service.WxUserInfoService;
import com.tianma315.wx.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * Created by zcm on 2019/8/16.
 */
@RestController
@RequestMapping("/trace")
public class TraceRestController extends BaseController {
    @Autowired
    private WxUserInfoService wxUserInfoService;

    @Autowired
    private ScanRecordService scanRecordService;


    @PostMapping("/update/location")
    public Result updateLocation(WxLocation location) {
        WxUserInfoDO wxUserInfo = getWxUser();
        wxUserInfo.setLatitude(location.getLatitude());
        wxUserInfo.setLongitude(location.getLongitude());



        if (scanRecordService.editLocation(wxUserInfo, location.getCode())) {
            return Result.ok();
        }
        return Result.fail();
    }

}

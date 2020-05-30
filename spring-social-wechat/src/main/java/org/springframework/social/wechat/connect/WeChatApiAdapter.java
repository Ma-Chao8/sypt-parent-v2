package org.springframework.social.wechat.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.wechat.api.model.UserInfo;
import org.springframework.social.wechat.api.WeChatApi;


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
 * @version 1.0
 * @Description: 接口适配器
 * @author: zcm
 * @date: 2018/3/26
 */
public class WeChatApiAdapter implements ApiAdapter<WeChatApi> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String openId;


    public WeChatApiAdapter() {
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    private String getOpenId() {
        return openId;
    }

    @Override
    public boolean test(WeChatApi api) {
        return api instanceof WeChatApi;
    }

    @Override
    public void setConnectionValues(WeChatApi api, ConnectionValues values) {
        logger.info("{}", api);
        logger.info("{}",values);
        UserInfo userInfo = api.getUserInfo(getOpenId());
        logger.info("{}", userInfo);
        if (userInfo!= null && values!= null){
            values.setProviderUserId(userInfo.getOpenid());
            values.setDisplayName(userInfo.getNickname());
            values.setImageUrl(userInfo.getHeadimgurl());
        }
    }

    @Override
    public UserProfile fetchUserProfile(WeChatApi api) {
        return null;
    }

    @Override
    public void updateStatus(WeChatApi api, String message) {

    }
}

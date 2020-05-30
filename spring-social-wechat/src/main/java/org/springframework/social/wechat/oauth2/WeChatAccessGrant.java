package org.springframework.social.wechat.oauth2;

import org.springframework.social.oauth2.AccessGrant;


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
 * @Description: 这是因为微信文档中在OAuth2.0的认证，微信的openid 同access_token一起返回。
 * 而Spring Social获取access_token的类AccessGrant.java中没有openid。
 * 因此我们自己需要扩展一下Spring Social获取令牌的类（AccessGrant.java）；
 * @author: zcm
 * @date: 2018/3/27
 */
public class WeChatAccessGrant extends AccessGrant {

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public WeChatAccessGrant(String accessToken) {
        this(accessToken, null, null, null);
    }

    public WeChatAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
        super(accessToken, scope, refreshToken, expiresIn);
    }
}

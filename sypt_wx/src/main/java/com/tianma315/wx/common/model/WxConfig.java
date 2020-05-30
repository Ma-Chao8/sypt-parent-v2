package com.tianma315.wx.common.model;

import com.tianma315.core.base.BaseModel;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Created by zcm on 2019/8/10.
 */
public class WxConfig extends BaseModel {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String url;
    private String jsapi_ticket;
    private String appId;
    private long timestamp = -1;
    private String nonceStr;
    private String signature;
    private List<String> jsApiList;


    public WxConfig() {
    }

    public WxConfig(String url, String jsapi_ticket) {
        this.url = url;
        this.jsapi_ticket = jsapi_ticket;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getTimestamp() {
        if (this.timestamp == -1) {
            this.timestamp = new Date().getTime();
        }
        return timestamp;
    }


    public String getNonceStr() {
        if (this.nonceStr == null) {
            this.nonceStr = generatorNonceStr();
        }
        return nonceStr;
    }


    public String getSignature() {
        if (this.signature == null) {
            this.signature = builderSignature();
        }
        return signature;
    }


    public List<String> getJsApiList() {
        return jsApiList;
    }

    public void setJsApiList(List<String> jsApiList) {
        this.jsApiList = jsApiList;
    }


    private String builderSignature() {
        Map<String, Object> params = new TreeMap<String, Object>() {{
            put("noncestr", getNonceStr());
            put("jsapi_ticket", jsapi_ticket);
            put("timestamp", getTimestamp());
            put("url", url);
        }};
        StringBuffer buffer = new StringBuffer();
        Iterator<String> iterator = params.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            buffer.append(String.format("%s=%s", key, params.get(key)));
            if (iterator.hasNext()) {
                buffer.append("&");
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("{} signature string = {}", this.hashCode(), buffer);
        }

        String sign = DigestUtils.sha1Hex(buffer.toString().getBytes());
        if (logger.isDebugEnabled()) {
            logger.debug("{} signature = {}", this.hashCode(), sign);
        }
        return sign;
    }

    /**
     * @return
     */
    private String generatorNonceStr() {
        StringBuffer buffer = new StringBuffer();
        String str = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 32; i++) {
            int index = (int) (Math.random() * str.length());
            buffer.append(str.charAt(index));
        }
        return buffer.toString();
    }


}

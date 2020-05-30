package com.tianma315.wx.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tianma315.core.sys.domain.ConfigDO;
import com.tianma315.core.sys.service.ConfigService;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by 廖师兄
 * 2017-07-03 01:25
 */
@Component
public class WechatMpConfig {

	@Autowired
    private ConfigService configService;

	@Value("${spring.social.wechat.app-id}")
	private String appid;
    @Value("${spring.social.wechat.app-secret}")
    private String appsecret;

    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
//        ConfigDO configDO = configService.getByKey("wx_param");
//        String v = configDO.getV();
//        JSONObject json = JSON.parseObject(v);
//        String appid = (String) json.get("appid");
//        String appsecret = (String) json.get("appsecret");
        wxMpConfigStorage.setAppId(appid);
        wxMpConfigStorage.setSecret(appsecret);
        return wxMpConfigStorage;
    }
}

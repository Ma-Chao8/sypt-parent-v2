package com.tianma315.core.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.qiniu.common.QiniuException;


import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import com.tianma315.core.base.CoreServiceImpl;

import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.sys.dao.FileMapper;
import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.service.ConfigService;
import com.tianma315.core.sys.service.FileService;
import com.tianma315.core.sys.service.UserService;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicParseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
 * 七牛云文件上传
 * <p>
 * Created by zcm on 2019/6/5.
 */
@Service
public class QiniuFileServiceImpl extends CoreServiceImpl<FileMapper, FileDO> implements FileService {
    private final static String ACCESS_KEY = "accesskey";
    private final static String SECRET_KEY = "secretkey";
    private final static String BUCKET = "bucket";
    private final static String ACCESS_URL = "accessurl";

    private Map<Integer, String> responseCode = new HashMap<Integer, String>() {{
        //200	更新成功
        //400	请求报文格式错误
        //401	管理凭证无效
        //599	服务端操作失败
        //612	待设置生命周期的资源不存在
        put(200, "更新成功");
        put(400, "请求报文格式错误");
        put(401, "管理凭证无效");
        put(599, "服务端操作失败");
        put(612, "待设置生命周期的资源不存在");
    }};

    @Autowired
    private ConfigService configService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;

    @Override
    public FileDO upload(Long userId, byte[] uploadBytes, String originalFilename) {
        try {
            MagicMatch match = null;
            try {
                match = Magic.getMagicMatch(uploadBytes, false);
            } catch (MagicParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                throw new MessageException("文件解析失败");
            }

            if (match.getMimeType() == null) {
                throw new MessageException("系统无法识别当前文件格式");
            }

            String extension = match.getExtension();
            String key = String.format("%s%s%s", generateKey(), ".", extension);
            EntityWrapper<FileDO> wrapper = new EntityWrapper<>();
            wrapper.eq("file_key", key);
            //避免key重复
            while (selectCount(wrapper) > 0) {
                key = String.format("%s%s%s", generateKey(), ".", extension);
                wrapper = new EntityWrapper<>();
                wrapper.eq("file_key", key);
            }
            //
            UploadManager uploadManager = new UploadManager();
            String token = getQiniuToken();
            //System.out.println(token);
            Response response = uploadManager.put(uploadBytes, key, token);
            PutRet ret = response.jsonToObject(PutRet.class);
            if (StringUtils.isBlank(ret.key)) {
                throw new MessageException("文件上传结果解析失败");
            }

            //Company company = companyService.getByUserId(userId);
            UserDO userDO = userService.selectById(userId);
            FileDO fileDO = new FileDO();
            fileDO.setCompanyId(userDO.getCompanyId());
            fileDO.setCreateDate(new Date());
            fileDO.setType(match.getMimeType());
            fileDO.setFileKey(key);
            fileDO.setOriginName(originalFilename);
            fileDO.setStatus(FileDO.STATUS_ENABLE);

            String host = qiniuConfig().getString(ACCESS_URL);
            if (!host.endsWith("/")) {
                host = String.format("%s%s", host, "/");
            }
            fileDO.setUrl(String.format("%s%s", host, key));
            if (!insert(fileDO)) {
                throw new MessageException("文件存档失败");
            }

            return fileDO;
        } catch (QiniuException e) {
            e.printStackTrace();
            throw new MessageException(String.format("文件上传失败: %s", responseCode.get(e.response.statusCode)), e);
        } catch (Throwable e) {
            throw e;
        }
    }

    @Override
    public String uploadBase64(String baseStr, Integer size) {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String uploadBase64 = null;

        String url = "https://upload.qiniup.com/putb64/" + size + "/key/" + UrlSafeBase64.encodeToString(uuid);
        //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody rb = RequestBody.create(null, baseStr);
        String token = Auth.create(qiniuConfig().getString(ACCESS_KEY), qiniuConfig().getString(SECRET_KEY))
                .uploadToken(qiniuConfig().getString(BUCKET),null,3600, new StringMap().put("insertOnly", 1));
        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + token)
                .post(rb).build();
        System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
       System.out.println(response);
        if (response.code() == 200) {
            return qiniuConfig().getString(ACCESS_URL) + uuid;
        }
        //            uploadBase64 = qiNiuOSS.uploadBase64(baseStr, size, uuid);
        return uploadBase64;
    }



    @Override
    public FileDO upload(long userId, byte[] uploadBytes) {
        return this.upload(userId, uploadBytes, "");
    }

    @Override
    public FileDO selectById(Long id) {
        return super.selectById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        FileDO fileDO = selectById(id);
        fileDO.setStatus(FileDO.STATUS_DELETE);
        return updateById(fileDO);
    }

    @Override
    public boolean deleteBatchIds(List<Long> asList) {
        List<FileDO> list = selectBatchIds(asList);
        list.forEach(fileDO -> {
            fileDO.setStatus(FileDO.STATUS_DELETE);
        });
        return updateBatchById(list);
    }

    /**
     * 文件key
     *
     * @return
     */
    private String generateKey() {
        String key = UUID.randomUUID().toString().replace("-", "");
        return key;
    }

    /**
     * 七牛云token
     *
     * @return
     */
    private String getQiniuToken() {
        JSONObject config = qiniuConfig();
        if (StringUtils.isBlank(config.getString(ACCESS_KEY))) {
            throw new MessageException(String.format("七牛云%s不能为空", ACCESS_KEY));
        }
        if (StringUtils.isBlank(config.getString(SECRET_KEY))) {
            throw new MessageException(String.format("七牛云%s不能为空", SECRET_KEY));
        }
        if (StringUtils.isBlank(config.getString(BUCKET))) {
            throw new MessageException(String.format("七牛云%s不能为空", BUCKET));
        }

        if (StringUtils.isBlank(config.getString(ACCESS_URL))) {
            throw new MessageException(String.format("七牛云%s不能为空", ACCESS_URL));
        }
        // 密钥配置
        Auth auth = Auth.create(config.getString(ACCESS_KEY), config.getString(SECRET_KEY));
        // <bucket>:<key>，表示只允许用户上传指定key的文件。在这种格式下文件默认允许“修改”，已存在同名资源则会被本次覆盖。
        // 如果希望只能上传指定key的文件，并且不允许修改，那么可以将下面的 insertOnly 属性值设为 1。
        // 第三个参数是token的过期时间
        return auth.uploadToken(config.getString(BUCKET));
    }

    /**
     * @return
     */
    private JSONObject qiniuConfig() {
        String qiniuConfig = configService.getValuByKey("oss_qiniu");
        if (StringUtils.isBlank(qiniuConfig)) {
            throw new MessageException("七牛云配置的值不能为空");
        }
        JSONObject jsonObject = JSON.parseObject(qiniuConfig, JSONObject.class);
        if (jsonObject == null) {
            throw new MessageException("七牛云配置解析失败");
        }
        JSONObject data = new JSONObject();
        for (String key : jsonObject.keySet()) {
            data.put(key.toLowerCase(), jsonObject.get(key));
        }
        return data;
    }

    /**
     * 上传结果封装
     */
    static class PutRet {
        public long fsize;
        public String key;
        public String hash;
        public String bucket;

        @Override
        public String toString() {
            return "PutRet{" +
                    "fsize=" + fsize +
                    ", key='" + key + '\'' +
                    ", hash='" + hash + '\'' +
                    ", bucket='" + bucket + '\'' +
                    '}';
        }
    }


}

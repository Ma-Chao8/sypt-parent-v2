package com.tianma315.web.main;

import com.alibaba.fastjson.JSONObject;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.sys.service.ConfigService;
import com.tianma315.core.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@RestController
@RequestMapping("/version")
public class VersionController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/getVersion")
//    @ResponseBody
    public Result<JSONObject> getVersion(){
        String version = configService.getValuByKey("version");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(version);
        jsonObject.remove("filePath");
        return Result.ok(jsonObject);
    }

    @GetMapping("/download")
    public String download(HttpServletResponse response){
        String version = configService.getValuByKey("version");
        JSONObject jsonObject = (JSONObject) JSONObject.parse(version);
        String filePath = jsonObject.getString("filePath");
//        filePath = "G:/s3Demo.zip";
        File file = new File(filePath);
        if (file.exists()){
        // 设置缓冲区大小
        int bufferSize = 4096;
        int readSize = 0;
        int writeSize = 0;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/force-download");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition","attachment;fileName="+file.getName());

        ByteBuffer buff = ByteBuffer.allocateDirect(bufferSize);
        try(
//                ServletOutputStream op = response.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(file);
                FileChannel fileChannel = fileInputStream.getChannel();
        ) {

            while ((readSize = fileChannel.read(buff)) != -1) {
                if (readSize == 0) {
                    continue;
                }
                buff.position(0);
                buff.limit(readSize);
                while (buff.hasRemaining()) {
                    writeSize = Math.min(buff.remaining(), bufferSize);
                    byte[] byteArr = new byte[writeSize];
                    buff.get(byteArr, 0, writeSize);
                    response.getOutputStream().write(byteArr);
                }
                buff.clear();
            }
            return "下载成功";
        }catch (IOException e) {
            System.out.println(e.getMessage());
            throw new MessageException("文件下载错误");
        }finally {
            buff.clear();
        }
        }else{
            throw new MessageException("文件不存在");
        }
    }
}

package com.tianma315.api.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.tianma315.api.util.JWTUtil;
import com.tianma315.core.base.Result;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.sys.service.UserService;
import com.tianma315.core.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController  {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<JSONObject> login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        password = MD5Utils.encrypt(username, password);
        String realPassword = userService.getPassword(username);
        if (realPassword == null) {
            return Result.fail("用户名错误",null);
        } else if (!realPassword.equals(password)) {
            return Result.fail("密码错误",null);
        } else {
            String token = JWTUtil.createToken(username);
            String refreshToken = JWTUtil.createRefreshToken(username);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token",token);
            jsonObject.put("refreshToken",refreshToken);
            return Result.ok(jsonObject);
        }
    }


    @PostMapping("/token/refresh")
    public Result<JSONObject> refresh(String refreshToken) {
        String username = JWTUtil.getUsername(refreshToken);
        if (username == null || !JWTUtil.verify(refreshToken, username)) {
            throw new MessageException("refreshToken已超时，请重新登陆");
        }else{
            String token = JWTUtil.createToken(username);
            String newRefreshToken = JWTUtil.createRefreshToken(username);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token",token);
            jsonObject.put("refreshToken",newRefreshToken);
            return Result.ok(jsonObject);
        }
    }

}

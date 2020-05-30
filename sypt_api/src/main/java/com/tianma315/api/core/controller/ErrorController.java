package com.tianma315.api.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.tianma315.core.base.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/error")
@RestController
public class ErrorController {
    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<JSONObject> unauthorized() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",401);
        jsonObject.put("msg","认证错误");
        return Result.ok(jsonObject);
    }
}

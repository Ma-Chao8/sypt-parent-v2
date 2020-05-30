package com.tianma315.web.sys.view;

import java.util.Arrays;

import com.tianma315.core.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.sys.domain.LogDO;
import com.tianma315.core.base.Result;

/**
 * <pre>
 * 日志
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@RequestMapping("/common/log")
@Controller
public class LogController {
    @Autowired
    SysLogService sysLogService;
    String prefix = "common/log";

    @GetMapping()
    String log() {
        return prefix + "/log";
    }

    @ResponseBody
    @GetMapping("/list")
    public Result<Page<LogDO>> list(Integer pageNumber, Integer pageSize, LogDO logDTO) {
        // 查询列表数据
        Page<LogDO> page = new Page<>(pageNumber, pageSize);

        Wrapper<LogDO> wrapper = new EntityWrapper<LogDO>(logDTO);
        page = sysLogService.selectPage(page, wrapper);
        int total = sysLogService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
    }

    @ResponseBody
    @PostMapping("/remove")
    Result<String> remove(Long id) {
        sysLogService.deleteById(id);
        return Result.ok();
    }

    @ResponseBody
    @PostMapping("/batchRemove")
    Result<String> batchRemove(@RequestParam("ids[]") Long[] ids) {
        sysLogService.deleteBatchIds(Arrays.asList(ids));
        return Result.fail();
    }
}

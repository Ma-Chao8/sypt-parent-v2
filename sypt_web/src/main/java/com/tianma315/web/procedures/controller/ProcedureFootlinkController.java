package com.tianma315.web.procedures.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.Result;
import com.tianma315.core.procedures.domain.ProcedureLinkDO;
import com.tianma315.core.procedures.domain.ProceduresFootlink;
import com.tianma315.core.procedures.domain.dto.ProceduresFootlinkDto;
import com.tianma315.core.procedures.service.ProcedureLinkService;
import com.tianma315.core.procedures.service.ProceduresFootlinkService;
import com.tianma315.core.procedures.vo.ProcedureLinkFileVO;
import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.service.FileService;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * <pre>
 * 环节
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
@Controller
@RequestMapping("/procedures/footlink")
public class ProcedureFootlinkController extends BaseController {
    @Autowired
    private ProceduresFootlinkService proceduresFootlinkService;


    @GetMapping(value = "/{proceduresId}")
    @RequiresPermissions("procedures:footlink:query")
    ModelAndView ProcedureLink(@PathVariable long proceduresId, ModelAndView view) {
        view.setViewName("procedures/footlink/footlink");
        view.addObject("proceduresId", proceduresId);
        return view;
    }

    @ResponseBody
    @GetMapping("/{proceduresId}/list")
    @RequiresPermissions("procedures:footlink:query")
    public Result<Page<ProceduresFootlink>> list(Integer pageNumber, Integer pageSize, @PathVariable long proceduresId) {
        // 查询列表数据
        Page<ProceduresFootlink> page = proceduresFootlinkService.getPage(pageNumber, pageSize, proceduresId);
        return Result.ok(page);
    }

    @GetMapping("/add/{proceduresId}")
    @RequiresPermissions("procedures:footlink:add")
    ModelAndView add(ModelAndView view, @PathVariable long proceduresId) {
        view.setViewName("procedures/footlink/footlink_add");
        view.addObject("proceduresId", proceduresId);
        return view;
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/add")
    @RequiresPermissions("procedures:footlink:add")
    public Result<String> save(ProceduresFootlinkDto footlink) {
        if (proceduresFootlinkService.addFootlink(getUserId(), footlink)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @GetMapping("/edit/{footlinkId}")
    @RequiresPermissions("procedures:footlink:edit")
    ModelAndView edit(@PathVariable long footlinkId, ModelAndView view) {
        ProceduresFootlink footlink = proceduresFootlinkService.getById(footlinkId);
        view.setViewName("procedures/footlink/footlink_edit");
        view.addObject("footlink", footlink);
        return view;
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/edit")
    @RequiresPermissions("procedures:footlink:edit")
    public Result<String> update(ProceduresFootlinkDto footlink) {
        if (proceduresFootlinkService.edit(getUserId(), footlink)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 删除
     */
    @PostMapping("/del")
    @ResponseBody
    @RequiresPermissions("procedures:footlink:del")
    public Result<String> del(Long[] footlinkId) {
        if (proceduresFootlinkService.removeBatchIds(Arrays.asList(footlinkId))) {
            return Result.ok();
        }
        return Result.fail();
    }


}
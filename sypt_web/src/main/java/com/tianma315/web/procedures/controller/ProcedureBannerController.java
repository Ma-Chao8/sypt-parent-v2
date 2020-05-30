package com.tianma315.web.procedures.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.Result;
import com.tianma315.core.procedures.domain.ProceduresBanner;
import com.tianma315.core.procedures.domain.dto.ProceduresBannerDto;
import com.tianma315.core.procedures.service.ProceduresBannerService;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;


/**
 * <pre>
 * 环节
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
@Controller
@RequestMapping("/procedures/banner")
public class ProcedureBannerController extends BaseController {
    @Autowired
    private ProceduresBannerService proceduresBannerService;


    /**
     * 添加视图
     *
     * @return
     */
    @GetMapping("/{proceduresId}/add")
    @RequiresPermissions("procedures:banner:add")
    ModelAndView add(@PathVariable long proceduresId, ModelAndView view) {
        view.setViewName("procedures/banner/banner_add");
        view.addObject("proceduresId", proceduresId);
        return view;
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/add")
    @RequiresPermissions("procedures:banner:add")
    public Result<String> save(ProceduresBannerDto banner) {
        if (proceduresBannerService.saveBanner(getUserId(), banner)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @GetMapping("/edit/{banner_id}")
    @RequiresPermissions("procedures:banner:edit")
    ModelAndView edit(@PathVariable long banner_id, ModelAndView view) {
        ProceduresBanner banner = proceduresBannerService.getById(banner_id);
        view.setViewName("procedures/banner/banner_edit");
        view.addObject("banner", banner);
        return view;
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/edit")
    @RequiresPermissions("procedures:banner:edit")
    public Result<String> update(ProceduresBannerDto banner) {
        if (proceduresBannerService.editBanner(getUserId(), banner)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 删除
     */
    @PostMapping("/del")
    @ResponseBody
    @RequiresPermissions("procedures:banner:del")
    public Result<String> remove(Long[] bannerId) {
        if (proceduresBannerService.removeBanner(Arrays.asList(bannerId))) {
            return Result.ok();
        }
        return Result.fail();
    }

    @GetMapping(value = "/{proceduresId}")
    @RequiresPermissions("procedures:banner:query")
    ModelAndView ProcedureLink(@PathVariable Long proceduresId, ModelAndView view) {
        view.setViewName("procedures/banner/banner");
        view.addObject("proceduresId", proceduresId);
        return view;
    }

    @ResponseBody
    @GetMapping("/{proceduresId}/list")
    @RequiresPermissions("procedures:banner:query")
    public Result<Page<ProceduresBanner>> list(@PathVariable long proceduresId, Integer pageNumber, Integer pageSize) {
        Page<ProceduresBanner> page = proceduresBannerService.getPage(proceduresId, pageNumber, pageSize);
        return Result.ok(page);
    }

}
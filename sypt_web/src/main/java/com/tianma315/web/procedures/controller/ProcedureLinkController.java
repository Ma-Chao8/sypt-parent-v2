package com.tianma315.web.procedures.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.procedures.domain.ProcedureLinkDO;
import com.tianma315.core.procedures.service.ProcedureLinkService;
import com.tianma315.core.procedures.vo.ProcedureLinkFileVO;
import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.service.FileService;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;


/**
 * <pre>
 * 环节
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
@Controller
@RequestMapping("/procedures/procedureLink")
public class ProcedureLinkController extends BaseController {
    @Autowired
    private ProcedureLinkService procedureLinkService;

    @Autowired
    private FileService fileService;

    @GetMapping(value = "/{proceduresId}")
    @RequiresPermissions("procedures:procedureLink:procedureLink")
	ModelAndView ProcedureLink(@PathVariable("proceduresId") Long proceduresId, ModelAndView view) {
    	view.setViewName("procedures/procedureLink/procedureLink");
    	view.addObject("proceduresId",proceduresId);
        return view;
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("procedures:procedureLink:procedureLink")
    public Result<Page<ProcedureLinkFileVO>> list(Integer pageNumber, Integer pageSize, ProcedureLinkDO procedureLinkDTO) {
        procedureLinkDTO.setCompanyId(getCompanyId());
        // 查询列表数据
        Page<ProcedureLinkFileVO> page = procedureLinkService.getProcedureLinkDOPage(pageNumber, pageSize, procedureLinkDTO);
        return Result.ok(page);
    }

    @GetMapping("/add/{proceduresId}")
    @RequiresPermissions("procedures:procedureLink:add")
    String add(@PathVariable("proceduresId") Integer proceduresId,Model model) {
        model.addAttribute("proceduresId",proceduresId);
        return "procedures/procedureLink/add";
    }

    @GetMapping("/edit/{procedureLinkId}")
    @RequiresPermissions("procedures:procedureLink:edit")
    String edit(@PathVariable("procedureLinkId") Integer procedureLinkId, Model model) {
        ProcedureLinkDO procedureLink = procedureLinkService.selectById(procedureLinkId);
        model.addAttribute("procedureLink", procedureLink);
        return "procedures/procedureLink/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("procedures:procedureLink:add")
    public Result<String> save(ProcedureLinkFileVO procedureLinkFileVO) {
        procedureLinkFileVO.setCompanyId(getCompanyId());
        procedureLinkFileVO.setCreateBy(getUserId());
        List<ProcedureLinkDO> list = procedureLinkService.checkProcedureLinkSort(procedureLinkFileVO);
        if (list != null && list.size() > 0) {
            return Result.fail("排序值不能重复", null);
        }

        if (procedureLinkService.addProcedureLink(procedureLinkFileVO)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("procedures:procedureLink:edit")
    public Result<String> update(ProcedureLinkFileVO procedureLinkFileVO) {
        FileDO fileDO = new FileDO();
        try {
            if (!procedureLinkFileVO.getIconFile().isEmpty()) {
                fileDO = fileService.upload(getUserId(), procedureLinkFileVO.getIconFile().getBytes(), procedureLinkFileVO.getIconFile().getOriginalFilename());
                procedureLinkFileVO.setIcon(fileDO.getUrl());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ProcedureLinkDO> list = procedureLinkService.checkProcedureLinkSort(procedureLinkFileVO);
        if (list != null && list.size() > 0) {
            return Result.fail("排序值不能重复");
        }
        procedureLinkService.updateLinkById(procedureLinkFileVO);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("procedures:procedureLink:remove")
    public Result<String> remove(Integer procedureLinkId) {
        if (procedureLinkService.deleteProcedureLink(procedureLinkId)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("procedures:procedureLink:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Integer[] procedureLinkIds) {
        for (Integer id : procedureLinkIds) {
            procedureLinkService.deleteProcedureLink(id);
        }
        return Result.ok();
    }

}
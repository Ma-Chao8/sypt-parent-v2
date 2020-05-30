package com.tianma315.web.procedures.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.company.domain.pojo.DemoDO;
import com.tianma315.core.company.service.DemoService;
import com.tianma315.core.procedures.domain.ProceduresDO;
import com.tianma315.core.procedures.service.ProceduresService;
import com.tianma315.core.procedures.vo.ProceduresVO;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * <pre>
 * 溯源流程模板
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
@Controller
@RequestMapping("/procedures/procedures")
public class ProceduresController extends BaseController {
    @Autowired
    private ProceduresService proceduresService;
    @Autowired
    private DemoService demoService;


    @GetMapping()
    @RequiresPermissions("procedures:procedures:procedures")
    String Procedures() {
        return "procedures/procedures/procedures";
    }

    @GetMapping("/bindProduct/{proceduresId}")
    String bindProduct(@PathVariable Integer proceduresId, Model model) {
        model.addAttribute("proceduresId", proceduresId);
        return "procedures/procedures/procedures_product";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("procedures:procedures:procedures")
    public Result<Page<ProceduresVO>> list(Integer pageNumber, Integer pageSize, ProceduresDO proceduresDTO) {
        proceduresDTO.setCompanyId(getCompanyId());
        // 查询列表数据
        Page<ProceduresVO> page = proceduresService.selectPage(pageNumber, pageSize, proceduresDTO);
        return Result.ok(page);
    }


    @GetMapping("/theme")
    ModelAndView theme(ModelAndView view) {
        view.setViewName("procedures/procedures/theme_list");
        return view;
    }

    @ResponseBody
    @GetMapping("/theme/list")
    Result themeList(int pageNumber, int pageSize) {
        Page<DemoDO> page = demoService.getPage(getCompanyId(), pageNumber, pageSize);
        return Result.ok(page);
    }


    @ResponseBody
    @GetMapping("/all")
    public Result<List<ProceduresDO>> list(ProceduresDO proceduresDTO) {
        proceduresDTO.setCompanyId(getCompanyId());
        // 查询列表数据
        List<ProceduresDO> list = proceduresService.selectList(proceduresDTO);
        return Result.ok(list);
    }

    @GetMapping("/add")
    @RequiresPermissions("procedures:procedures:add")
    String add() {
        return "procedures/procedures/add";
    }

    @GetMapping("/edit/{procedureId}")
    @RequiresPermissions("procedures:procedures:edit")
    String edit(@PathVariable("procedureId") Integer procedureId, Model model) {
        ProceduresDO procedures = proceduresService.selectById(procedureId);
        model.addAttribute("procedures", procedures);
        return "procedures/procedures/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("procedures:procedures:add")
    public Result<String> save(ProceduresDO procedures) {
        procedures.setCompanyId(getCompanyId());
        procedures.setCreateBy(getUserId());
        if (proceduresService.addProcedures(procedures)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("procedures:procedures:edit")
    public Result<String> update(ProceduresDO procedures) {
        proceduresService.updateById(procedures);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("procedures:procedures:remove")
    public Result<String> remove(Integer procedureId) {
        if (proceduresService.deleteProcedures(procedureId)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("procedures:procedures:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Integer[] procedureIds) {
        for (Integer id : procedureIds) {
            proceduresService.deleteProcedures(id);
        }

//		proceduresService.deleteBatchIds(Arrays.asList(procedureIds));
        return Result.ok();
    }

}

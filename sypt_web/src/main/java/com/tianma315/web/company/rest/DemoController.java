package com.tianma315.web.company.rest;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.Result;
import com.tianma315.core.company.domain.pojo.CompanyDemoVO;
import com.tianma315.core.company.domain.pojo.DemoDO;
import com.tianma315.core.company.service.CompanyDemoService;
import com.tianma315.core.company.service.DemoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;


/**
 * <pre>
 *
 * </pre>
 * <small> 2019-09-25 16:51:32 | Lgc</small>
 */
@Controller
@RequestMapping("/company/demo")
public class DemoController {
    @Autowired
    private DemoService demoService;
    @Autowired
    private CompanyDemoService companyDemoService;

    @GetMapping()
    @RequiresPermissions("company:demo:demo")
    ModelAndView Demo(ModelAndView view) {
        view.setViewName("company/demo/demo");
        return view;
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("company:demo:demo")
    public Result<Page<DemoDO>> list(Integer pageNumber, Integer pageSize, DemoDO demoDTO) {
        // 查询列表数据
        Page<DemoDO> page = new Page<>(pageNumber, pageSize);

        Wrapper<DemoDO> wrapper = new EntityWrapper<DemoDO>();
        wrapper.eq("demo_status",1);
        wrapper.like("demo_name", demoDTO.getDemoName());
        page = demoService.selectPage(page, wrapper);
        int total = demoService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
    }

    @GetMapping("/add")
    @RequiresPermissions("company:demo:add")
    String add() {
        return "company/demo/add";
    }

    @GetMapping("/edit/{demoId}")
    @RequiresPermissions("company:demo:edit")
    String edit(@PathVariable("demoId") Integer demoId, Model model) {
        DemoDO demo = demoService.selectById(demoId);
        model.addAttribute("demo", demo);
        return "company/demo/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("company:demo:add")
    public Result<String> save(DemoDO demo) {
        if (demoService.insert(demo)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("company:demo:edit")
    public Result<String> update(DemoDO demo) {
        //System.out.println("demoID==============="+demo.getDemoId());
        demoService.updateById(demo);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("company:demo:remove")
    public Result<String> remove(Integer demoId) {
        if (demoService.deleteById(demoId)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("company:demo:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Integer[] demoIds) {
        demoService.deleteBatchIds(Arrays.asList(demoIds));
        return Result.ok();
    }

    /**
     * 查询所有
     */
    @ResponseBody
    @GetMapping("/all")
    @RequiresPermissions("company:demo:demo")
    public Result<List<DemoDO>> all() {
        Wrapper<DemoDO> wrapper = new EntityWrapper<DemoDO>();
        wrapper.eq("demo_status",1);
        List<DemoDO> demoList = demoService.selectList(wrapper);
        return Result.ok(demoList);
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/bind")
    @RequiresPermissions("company:demo:add")
    public Result<String> bindSave(CompanyDemoVO cdemo) {
        companyDemoService.addCompanyDemo(cdemo);
        return Result.ok();
    }
}

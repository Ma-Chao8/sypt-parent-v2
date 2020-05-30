package com.tianma315.web.traceability.controller;


import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.procedures.domain.ProcedureLinkDO;
import com.tianma315.core.trace.service.TraceOutService;
import com.tianma315.core.traceability.domain.TyMenuDO;
import com.tianma315.core.traceability.domain.TyMenuDataDO;
import com.tianma315.core.traceability.service.TyMenuDataService;
import com.tianma315.core.traceability.service.TyMenuService;
import com.tianma315.core.traceability.vo.TraceabilityFileVO;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.traceability.domain.TraceabilityFileDO;
import com.tianma315.core.traceability.service.TraceabilityFileService;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 溯源档案
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
@Controller
@RequestMapping("/traceability/traceabilityFile")
public class TraceabilityFileController extends BaseController {
    @Autowired
    private TraceabilityFileService traceabilityFileService;
    @Autowired
    private TraceOutService traceOutService;
    @Autowired
    private TyMenuService tyMenuService;
    @Autowired
    private TyMenuDataService tyMenuDataService;


    @GetMapping()
    @RequiresPermissions("traceability:traceabilityFile:traceabilityFile")
    String TraceabilityFile() {
        return "traceability/traceabilityFile/traceabilityFile";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("traceability:traceabilityFile:traceabilityFile")
    public Result<Page<TraceabilityFileVO>> list(Integer pageNumber, Integer pageSize, TraceabilityFileVO traceabilityFileDTO) {
        traceabilityFileDTO.setCompanyId(getCompanyId());
        // 查询列表数据
        Page<TraceabilityFileVO> page = traceabilityFileService.getTraceabilityFilePage(pageNumber, pageSize, traceabilityFileDTO);
        return Result.ok(page);
    }

    @GetMapping("/add")
    @RequiresPermissions("traceability:traceabilityFile:add")
    ModelAndView add(ModelAndView view) {
        view.setViewName("traceability/traceabilityFile/add");
        return  view;
    }

    @GetMapping("/edit/{traceabilityFileId}")
    @RequiresPermissions("traceability:traceabilityFile:edit")
    String edit(@PathVariable("traceabilityFileId") Integer traceabilityFileId, Model model) {
        TraceabilityFileDO traceabilityFile = traceabilityFileService.selectById(traceabilityFileId);
        model.addAttribute("traceabilityFile", traceabilityFile);
        return "traceability/traceabilityFile/edit";
    }

    /**
     * 保存
     * 创建档案时判断是否动态录入
     *     非动态：将默认值添加到表中去
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("traceability:traceabilityFile:add")
    public Result<String> save(TraceabilityFileDO traceabilityFile) {
        traceabilityFile.setCompanyId(getCompanyId());
        traceabilityFile.setCreateBy(getUserId());
        traceabilityFile.setCreateDate(Calendar.getInstance().getTime());
        traceabilityFile.setState(0);
        //创建档案时判断是否动态录入
        if (traceabilityFileService.inserts(traceabilityFile)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("traceability:traceabilityFile:edit")
    public Result<String> update(TraceabilityFileDO traceabilityFile) {
        traceabilityFileService.updateById(traceabilityFile);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("traceability:traceabilityFile:remove")
    public Result<String> remove(Integer traceabilityFileId) {
        if (traceabilityFileService.updateStateById(traceabilityFileId)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("traceability:traceabilityFile:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Integer[] traceabilityFileIds) {
        for (Integer id : traceabilityFileIds) {
            traceabilityFileService.updateStateById(id);
        }
        return Result.ok();
    }

    @GetMapping("/all")
    @ResponseBody
    public Result<List<TraceabilityFileDO>> all() {
        List<TraceabilityFileDO> TraceabilityFileDOs = traceabilityFileService.getTraceabilityFileDOList();
        return Result.ok(TraceabilityFileDOs);
    }

    /**
     * 保存
     */
    @GetMapping("/addTraceOut/{TraceabilityFileId}/{productId}")
    @RequiresPermissions("trace:traceOut:add")
    public String save(@PathVariable("productId") Integer productId, @PathVariable("TraceabilityFileId") Integer traceabilityFileId, Model model) {

        List<ProcedureLinkDO> contentList = traceOutService.selectContent(getUserId(),productId, traceabilityFileId);

        EntityWrapper<TyMenuDO> wrapper = new EntityWrapper<>();
        wrapper.eq("traceability_file_id", traceabilityFileId);
        List<TyMenuDO> tyMenuDOS = tyMenuService.selectList(wrapper);
        model.addAttribute("traceabilityFileId", traceabilityFileId);
        model.addAttribute("productId", productId);
        if (tyMenuDOS.size() == 0 || contentList.size() != tyMenuDOS.size()) {
            model.addAttribute("contentList", contentList);
            model.addAttribute("msg", "请完善未录入的信息");
            return "trace/traceOut/toOutContent";
        }



        return "trace/traceOut/add";
    }

    @GetMapping("/getDataByTraceabilityFileId/{TraceabilityFileId}/{productId}")
    public ModelAndView getDataByTraceabilityFileId(@PathVariable("productId") Integer productId, @PathVariable("TraceabilityFileId") Integer traceabilityFileId) {
        ModelAndView modelAndView = new ModelAndView("traceability/traceabilityFile/result");

        List<ProcedureLinkDO> contentList = traceOutService.selectContent(getUserId(),productId, traceabilityFileId);

        EntityWrapper<TyMenuDO> wrapper = new EntityWrapper<>();
        wrapper.eq("traceability_file_id", traceabilityFileId);
        List<TyMenuDO> tyMenuDOS = tyMenuService.selectList(wrapper);
        if (tyMenuDOS.size() == 0 || contentList.size() != tyMenuDOS.size()) {
            modelAndView.addObject("contentList",contentList);
            modelAndView.addObject("traceabilityFileId", traceabilityFileId);
            modelAndView.addObject("msg","请完善未录入的信息");
            modelAndView.setViewName("trace/traceOut/toOutContent");
            return modelAndView;
        }

        JSONObject result = traceabilityFileService.getDataByTraceabilityFileId(traceabilityFileId);
        modelAndView.addObject("result", result);
        return modelAndView;
    }


    @GetMapping("/preview/{TraceabilityFileId}/{productId}")
    ModelAndView preview(@PathVariable("productId") Integer productId, @PathVariable("TraceabilityFileId") Integer traceabilityFileId, ModelAndView view) {
        List<ProcedureLinkDO> contentList = traceOutService.selectContent(getUserId(),productId, traceabilityFileId);

        EntityWrapper<TyMenuDO> wrapper = new EntityWrapper<>();
        wrapper.eq("traceability_file_id", traceabilityFileId);
        List<TyMenuDO> tyMenuDOS = tyMenuService.selectList(wrapper);
        if (tyMenuDOS.size() == 0 || contentList.size() != tyMenuDOS.size()) {
            view.addObject("contentList",contentList);
            view.addObject("traceabilityFileId", traceabilityFileId);
            view.addObject("msg","请完善未录入的信息");
            view.setViewName("trace/traceOut/toOutContent");
            return view;
        }

        Company company = getCompany();
        TraceabilityFileVO traceabilityFile = traceabilityFileService.getTraceabilityFileInfo(traceabilityFileId);
        traceabilityFile.setCompany(company);
        view.setViewName(String.format("traceability/mould/%s", traceabilityFile.getTheme()));
        view.addObject("tf", traceabilityFile);
        return view;
    }


    @GetMapping("/getTyMenuDataById/{menuDataId}")
    String getTyMenuDataById(@PathVariable("menuDataId") Integer menuDataId, Model model) {
        TyMenuDataDO tyMenuData = tyMenuDataService.selectById(menuDataId);
        String[] imgList = tyMenuData.getDataValue().split(",");
        model.addAttribute("tyMenuData", tyMenuData);
        model.addAttribute("imgList", imgList);
        return "traceability/traceabilityFile/trace-report-img";
    }

    @GetMapping("/checkCode")
    @ResponseBody
    JSONObject checkCode(@RequestParam("code") String code, Model model) {
//		JSONObject result = tyMenuDataService.checkCode(code);
        JSONObject result = new JSONObject();
        result.put("msg", "防伪码首次查询");
        return result;
    }
}

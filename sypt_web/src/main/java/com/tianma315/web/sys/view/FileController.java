package com.tianma315.web.sys.view;

import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.service.FileService;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * <pre>
 * 文件上传
 * </pre>
 *
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/common/sysFile")
public class FileController extends BaseController {

    @Autowired
    private FileService sysFileService;

    @GetMapping()
    @RequiresPermissions("common:sysFile:sysFile")
    String sysFile(Model model) {
        return "common/file/file";
    }


    @GetMapping("/add")
        // @RequiresPermissions("common:bComments")
    String add() {
        return "common/sysFile/add";
    }

    @GetMapping("/edit")
        // @RequiresPermissions("common:bComments")
    String edit(Long id, Model model) {
        FileDO sysFile = sysFileService.selectById(id);
        model.addAttribute("sysFile", sysFile);
        return "common/sysFile/edit";
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("common:info")
    public Result<FileDO> info(@PathVariable("id") Long id) {
        FileDO sysFile = sysFileService.selectById(id);
        return Result.ok(sysFile);
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("common:save")
    public Result<String> save(FileDO sysFile) {
        sysFileService.insert(sysFile);
        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("common:update")
    public Result<String> update(@RequestBody FileDO sysFile) {
        sysFileService.updateById(sysFile);

        return Result.ok();
    }


    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("common:remove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        sysFileService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }

    /**
     * 图片列表
     *
     * @return
     */
    @GetMapping("/img/list")
    ModelAndView imgList() {
        return new ModelAndView("common/file/img-list");
    }


    @ResponseBody
    @PostMapping("/uploadOss")
    Map uploadOss(HttpServletRequest request) {
        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
        Set<Map.Entry<String, MultipartFile>> eSet = fileMap.entrySet();
        Map<String,Object> result = new HashMap<>();
        List<String> urlArr = new ArrayList<>();
        try {
            for (Map.Entry<String, MultipartFile> entry : eSet) {
                MultipartFile mFile = entry.getValue();
                FileDO fileDO =  sysFileService.upload(getUserId(),mFile.getBytes(), mFile.getOriginalFilename());
                urlArr.add(fileDO.getUrl());
            }
            result.put("errno",0);
            result.put("data",urlArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

package com.tianma315.web.sys.rest;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.service.FileService;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//                   _ooOoo_
//                  o8888888o
//                  88" . "88
//                  (| -_- |)
//                  O\  =  /O
//               ____/`---'\____
//             .'  \\|     |//  `.
//            /  \\|||  :  |||//  \
//           /  _||||| -:- |||||-  \
//           |   | \\\  -  /// |   |
//           | \_|  ''\---/''  |   |
//           \  .-\__  `-`  ___/-. /
//         ___`. .'  /--.--\  `. . __
//      ."" '<  `.___\_<|>_/___.'  >'"".
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//     \  \ `-.   \_ __\ /__ _/   .-` /  /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//         佛祖保佑       永无BUG

/**
 * Description
 * <p>
 * Created by zcm on 2019/6/5.
 */
@RestController
@RequestMapping("/file")
public class FileRestController extends BaseController {
    @Autowired
    private FileService fileService;
    @Autowired
    private CompanyService companyService;

    /**
     * @param pageNumber
     * @param pageSize
     * @param type
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("common:sysFile:sysFile")
    public Result<Page<FileDO>> list(Integer pageNumber, Integer pageSize, String type) {
        Company company = companyService.getByUserId(getUserId());
        if (company == null) {
            throw new MessageException("商户信息获取失败");
        }
        // 查询列表数据
        Page<FileDO> page = new Page<>(pageNumber, pageSize);
        Wrapper<FileDO> wrapper = new EntityWrapper<FileDO>()
                .eq("company_id", company.getCompanyId())
                .eq("status", FileDO.STATUS_ENABLE)
                .like("type", StringUtils.isBlank(type) ? "" : type);
        page = fileService.selectPage(page, wrapper);
        return Result.ok(page);
    }

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    Result<FileDO> upload(MultipartFile file) {
        if (file == null) {
            return fail("上传文件不能为空", null);
        }

        try {
            FileDO fileDO = fileService.upload(getUserId(), file.getBytes(), file.getOriginalFilename());
            if (fileDO != null) {
                return success(fileDO);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return fail("文件解获取败", null);
        }
        return fail();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @PostMapping("/remove")
    @ResponseBody
    // @RequiresPermissions("common:remove")
    public Result<String> remove(Long id) {
        fileService.deleteById(id);
        return Result.ok();
    }


}

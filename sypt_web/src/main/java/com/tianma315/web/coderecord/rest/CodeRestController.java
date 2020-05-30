package com.tianma315.web.coderecord.rest;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.Result;
import com.tianma315.core.coderecord.domain.CodeRecordDO;
import com.tianma315.core.coderecord.domain.pojo.CodeOutSection;
import com.tianma315.core.coderecord.service.CodeOutSectionService;
import com.tianma315.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
 * Created by zcm on 2019/9/24.
 */
@RestController
@RequestMapping("/code")
public class CodeRestController extends BaseController {
    @Autowired
    private CodeOutSectionService codeOutSectionService;


    @RequestMapping("/out/record")
    Result<Page<CodeRecordDO>> codeOutRecord(int pageNumber, int pageSize) {
        Page page = codeOutSectionService.getPage(pageNumber, pageSize);
        return Result.ok(page);
    }


    @RequestMapping("/out/record/add")
    Result<Page<CodeRecordDO>> codeOutRecordAdd(CodeOutSection section) {
        section.setCreateUserId(getUserId());
        section.setCreateUsername(getUsername());
        section.setCreatDate(new Date());
        if (codeOutSectionService.save(section)) {
            return Result.ok();
        }
        return fail();
    }

}

package com.tianma315.web.company.rest;

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

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.company.domain.dto.CompanyDto;
import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.domain.pojo.DemoDO;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description
 * <p>
 * Created by zcm on 2019/6/24.
 */
@RestController
@RequestMapping("/company")
public class CompanyRestController extends BaseController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/list")
    Result list(int pageNumber, int pageSize, String searchKey) {
        Page<?> page = companyService.getPage(pageNumber, pageSize, searchKey);
        if (page != null)
            return success(page);
        return fail();
    }

    @PostMapping("/add")
    Result add(CompanyDto company) {
        company.setCreateBy(getUserId());
        if (companyService.save(company)) {
            return success();
        }
        return fail();
    }

    /**
     * @param company
     * @return
     */
    @PostMapping("/edit")
    Result edit(Company company) {
        if (companyService.edit(company)) {
            return success();
        }
        return fail();
    }

    /**
     * @param industryId
     * @return
     */
    @GetMapping("/all/{industryId}")
    Result all(@PathVariable Integer industryId) {
        List<Company> companyList = companyService.getCompanyByIndustryId(industryId);
        return Result.ok(companyList);
    }

    /**根据id去查找相关模版
     * @param companyId
     * @return
     */
    @GetMapping("/getDemoListByCompanyId/{companyId}")
    Result bind(@PathVariable Integer companyId) {
        List<DemoDO> demoDOList = companyService.getDemoListByCompanyId(companyId);
        //demoDOList.forEach(s -> System.out.println("demoid========="+s.getDemoId()));
        return Result.ok(demoDOList);
    }
}

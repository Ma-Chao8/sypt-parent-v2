package com.tianma315.core.company.service;

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
import com.tianma315.core.company.domain.vo.CompanyCountLevelVO;
import com.tianma315.core.company.domain.vo.CompanyCountRankVO;

import java.util.List;

/**
 * Description
 * <p>
 * Created by zcm on 2019/6/24.
 */
public interface CompanyService {

    /**
     * 获取商户
     *
     * @param user_id
     * @return
     */
    Company getByUserId(long user_id);


    /**
     * @param pageNumber
     * @param pageSize
     * @param searchKey
     * @return
     */
    Page getPage(int pageNumber, int pageSize, String searchKey);

    /**
     * @param companyId
     * @return
     */
    Company getById(long companyId);

    /**
     * 添加商户
     *
     * @param company
     * @return
     */
    boolean save(CompanyDto company);

    /**
     * 修改商户信息
     *
     * @param company
     * @return
     */
    boolean edit(Company company);

    List<CompanyCountLevelVO> countLevel();

    List<CompanyCountRankVO> countRank();

    List<Company> getCompanyByIndustryId(Integer industryId);

    /**
     * 查询所有商户
     * @return
     */
    List<Company> getAll();

    List<DemoDO> getDemoListByCompanyId(Integer companyId);
}

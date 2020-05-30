package com.tianma315.core.company.dao;

import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.domain.pojo.DemoDO;
import com.tianma315.core.company.domain.vo.CompanyCountLevelVO;
import com.tianma315.core.company.domain.vo.CompanyCountRankVO;

import java.util.List;

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
 * Created by zcm on 2019/6/24.
 */
public interface CompanyMapper extends CoreMapper<Company> {
    List<CompanyCountLevelVO> countLevel();

    List<CompanyCountRankVO> countRank();

    List<DemoDO> getDemoListByCompanyId(Integer companyId);
}

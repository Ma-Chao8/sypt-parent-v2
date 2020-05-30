package com.tianma315.core.company.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.company.dao.CompanyMapper;
import com.tianma315.core.company.domain.dto.CompanyDto;
import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.domain.pojo.DemoDO;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.company.domain.vo.CompanyCountLevelVO;
import com.tianma315.core.company.domain.vo.CompanyCountRankVO;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.sys.dao.DeptMapper;
import com.tianma315.core.sys.dao.UserMapper;
import com.tianma315.core.sys.dao.UserRoleMapper;
import com.tianma315.core.sys.domain.DeptDO;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.domain.UserRoleDO;
import com.tianma315.core.sys.service.ConfigService;
import com.tianma315.core.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
@Service
public class CompanyServiceImpl extends CoreServiceImpl<CompanyMapper, Company> implements CompanyService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    ConfigService configService;

    @Autowired
    DeptMapper deptMapper;

    @Override
    public Company getByUserId(long user_id) {
        Wrapper wrapper = new EntityWrapper<Company>().eq("user_id", user_id);
        List<Company> companies = selectList(wrapper);

        if (companies == null || companies.isEmpty())
            return null;

        return companies.get(0);
    }

    @Override
    public Page getPage(int pageNumber, int pageSize, String searchKey) {
        Page page = new Page(pageNumber, pageSize);
        Wrapper wrapper = new EntityWrapper()
                .like("company_name", searchKey);
        selectPage(page, wrapper);
        return page;
    }

    @Override
    public Company getById(long companyId) {
        return selectById(companyId);
    }

    @Override
    @Transactional
    public boolean save(CompanyDto company) {

        Wrapper<UserDO> wrapper = new EntityWrapper<UserDO>().eq("username", company.getUsername());

        if (userMapper.selectCount(wrapper) > 0) {
            throw new MessageException(String.format("用户名 %s 已被注册，请更换用户名", company.getUsername()));
        }
        //创建部门
        DeptDO deptDO = new DeptDO();
        deptDO.setName(company.getCompanyName());
        deptMapper.insert(deptDO);
        UserDO user = new UserDO();
        user.setUsername(company.getUsername());
        user.setName(company.getCompanyName());
        user.setStatus(1);
        user.setPassword(MD5Utils.encrypt(company.getUsername(), company.getPassword()));
        user.setGmtCreate(new Date());
        user.setDeptId(deptDO.getId());
        if (userMapper.insert(user) != 1) {
            throw new MessageException(String.format("用户 %s 创建失败", company.getUsername()));
        }

        UserRoleDO userRole = new UserRoleDO();
        userRole.setUserId(user.getId());
        String company_role = configService.getValuByKey("company_role");
        userRole.setRoleId(Long.parseLong(company_role));
        if (userRoleMapper.insert(userRole) != 1) {
            throw new MessageException("角色绑定失败");
        }

        company.setUserId(user.getId());
        company.setCreateDate(new Date());
        if (!insert(company)) {
            throw new MessageException("商户信息注册失败");
        }
        deptDO.setCompanyId(company.getCompanyId());
        deptMapper.updateById(deptDO);

        return true;
    }

    @Override
    public boolean edit(Company company) {
        Company target = selectById(company.getCompanyId());
        target.setCompanyAddress(company.getCompanyAddress());
        target.setIntroduce(company.getIntroduce());
        target.setLatitude(company.getLatitude());
        target.setLongitude(company.getLongitude());
        target.setLinkman(company.getLinkman());
        target.setCompanyName(company.getCompanyName());
        target.setLevel(company.getLevel());
        target.setLogo(company.getLogo());
        target.setRank(company.getRank());
        target.setTel(company.getTel());
        target.setProductIdent(company.getProductIdent());
        target.setCompanyLegalPerson(company.getCompanyLegalPerson());
        target.setIndustryId(company.getIndustryId());
        return updateById(target);
    }

    @Override
    public List<CompanyCountLevelVO> countLevel() {

        return companyMapper.countLevel();
    }

    @Override
    public List<CompanyCountRankVO> countRank() {
        return companyMapper.countRank();
    }

    @Override
    public List<Company> getCompanyByIndustryId(Integer industryId) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("industry_id",industryId);
        List<Company> companyList = selectList(entityWrapper);
        return companyList;
    }

    @Override
    public List<Company> getAll() {
        return selectList(new EntityWrapper<>());
    }

    @Override
    public List<DemoDO> getDemoListByCompanyId(Integer companyId) {
        List<DemoDO> DemoList=companyMapper.getDemoListByCompanyId(companyId);
        return DemoList;
    }


}

package com.tianma315.core.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.sys.dao.UserMapper;
import com.tianma315.core.sys.dao.UserRoleMapper;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.domain.UserRoleDO;
import com.tianma315.core.sys.service.ConfigService;
import com.tianma315.core.utils.MD5Utils;
import com.tianma315.core.warehouse.domain.vo.WarehouseManagerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import com.tianma315.core.warehouse.dao.WarehouseManagerDao;
import com.tianma315.core.warehouse.domain.WarehouseManagerDO;
import com.tianma315.core.warehouse.service.WarehouseManagerService;

import java.util.Date;
import java.util.List;


/**
 * 
 * <pre>
 * 产品员工关联
 * </pre>
 * <small> 2019-08-20 15:11:51 | Lgc</small>
 */
@Service
public class WarehouseManagerServiceImpl extends CoreServiceImpl<WarehouseManagerDao, WarehouseManagerDO> implements WarehouseManagerService {

    @Autowired
    WarehouseManagerDao warehouseManagerDao;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ConfigService configService;

    @Autowired
    UserRoleMapper userRoleMapper;


    @Override
    public Page<WarehouseManagerVo> getPage(Integer pageNumber, Integer pageSize, WarehouseManagerDO warehouseManagerDTO) {
        Page<WarehouseManagerVo> warePage = new Page<>(pageNumber,pageSize);
        List<WarehouseManagerVo> list=warehouseManagerDao.getPagelist(warehouseManagerDTO);
        warePage.setRecords(list);
        return warePage;
    }

    @Override
    public boolean deleteUserStatus(Long id, Integer status) {
        WarehouseManagerVo vo = warehouseManagerDao.selectVoById(id);

        UserDO userDO = userMapper.selectById(vo.getUserId());
        userDO.setStatus(status);

        if (userMapper.updateById(userDO) != 1)
            throw new MessageException("禁用失败");

        return true;

    }

    @Override
    public boolean save(WarehouseManagerVo warehouseManagervo) {
        EntityWrapper<UserDO> wrapper = new EntityWrapper<>();
        wrapper.eq("username",warehouseManagervo.getUsername());
        Integer target = userMapper.selectCount(wrapper);
        if (target>0)
            throw new MessageException("该用户名已被占用");

        UserDO user = new UserDO();
        user.setUsername(warehouseManagervo.getUsername());
        //设置默认密码
        String managerPassword = configService.getValuByKey("warehouse_manager_password");
        user.setPassword(MD5Utils.encrypt(warehouseManagervo.getUsername(),managerPassword));
        user.setStatus(StateEnum.usable.getIndex());
        user.setName(warehouseManagervo.getRealName());
        user.setCompanyId(warehouseManagervo.getCompanyId());
        user.setUserIdCreate(warehouseManagervo.getUserId());
        user.setGmtCreate(new Date());
        if (userMapper.insert(user) != 1)
            throw new MessageException("用户创建失败");

        //添加权限
        UserRoleDO userRole = new UserRoleDO();
        userRole.setUserId(user.getId());

        //获取仓库管理员权限id固定值
        String managerKey = configService.getValuByKey("warehouse_manager");
        userRole.setRoleId(Long.parseLong(managerKey));

        if (userRoleMapper.insert(userRole) != 1)
            throw new MessageException("账户权限赋值失败");

        //获取user的id返回
        warehouseManagervo.setUserId(user.getId());
        if (warehouseManagerDao.insert(warehouseManagervo) != 1)
            throw new MessageException("添加失败");

        return true;
    }

    @Override
    public WarehouseManagerVo selectVoById(Long id) {
        WarehouseManagerVo warehouseManagerVo = warehouseManagerDao.selectVoById(id);
        return warehouseManagerVo;
    }

    @Override
    public WarehouseManagerVo selectVoByUserId(Long userId) {
        WarehouseManagerVo warehouseManagerVo = warehouseManagerDao.selectVoByUserId(userId);
        return warehouseManagerVo;
    }


}

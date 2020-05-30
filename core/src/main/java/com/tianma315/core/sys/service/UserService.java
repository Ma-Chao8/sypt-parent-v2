package com.tianma315.core.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.tianma315.core.sys.domain.DeptDO;
import com.tianma315.core.sys.domain.Tree;
import com.tianma315.core.sys.domain.UserDO;
import com.tianma315.core.sys.domain.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Service
public interface UserService extends IService<UserDO> {

    boolean exit(Map<String, Object> params);

    Set<String> listRoles(Long userId);

    int resetPwd(UserVo userVO, UserDO userDO);

    int adminResetPwd(UserVo userVO);

    Tree<DeptDO> getTree();

    /**
     * 更新个人信息
     * 
     * @param userDO
     * @return
     */
    int updatePersonal(UserDO userDO);

    /**
     * 更新个人图片
     * 
     * @param file
     *            图片
     * @param avatar_data
     *            裁剪信息
     * @param userId
     *            用户ID
     * @throws Exception
     */
    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;

    String getPassword(String username);

    UserDO getUserDOByUserName(String userName);

    int warehouseManagerResetPwd(UserVo userVO);
}

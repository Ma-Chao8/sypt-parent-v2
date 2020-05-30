package com.tianma315.wx.user.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.tianma315.core.sys.dao.RoleMapper;
import com.tianma315.core.sys.dao.UserMapper;
import com.tianma315.core.sys.domain.RoleDO;
import com.tianma315.core.sys.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;

import java.util.ArrayList;
import java.util.Collection;
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
 * Created by zcm on 2019/8/21.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Wrapper<UserDO> wrapper = new EntityWrapper<>();
        wrapper.eq("username", username);
        List<UserDO> list = userMapper.selectList(wrapper);

        if (list == null || list.isEmpty()) {
            throw new UsernameNotFoundException(String.format("系统找不到用户名为%s的用户", username));
        }
        UserDO userDO = list.get(0);
        Collection<GrantedAuthority> authorities = new ArrayList<>();


        Wrapper<RoleDO> roleWrapper = new EntityWrapper<>();
        List<RoleDO> roles = roleMapper.selectList(roleWrapper);
        if (roles != null) {
            for (RoleDO role : roles) {
                authorities.add(role::getRoleName);
            }
        }

        return new SocialUser(userDO.getUsername(), userDO.getPassword(), authorities);
    }
}

package com.tianma315.core.social.service.impl;

import com.tianma315.core.social.dao.UserConnectionMapper;
import com.tianma315.core.social.domain.UserConnection;
import com.tianma315.core.social.service.UserConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * Created by zcm on 2019/8/16.
 */
@Service
public class UserConnectionServiceImpl implements UserConnectionService {

    @Autowired
    private UserConnectionMapper userConnectionMapper;


    @Override
    public UserConnection getById(String userId) {
        UserConnection userConnection = userConnectionMapper.selectById(userId);
        return userConnection;
    }
}

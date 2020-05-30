package com.tianma315.core.sys.service;

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

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.sys.domain.FileDO;

import java.util.List;

/**
 * 文件上传
 * <p>
 * Created by zcm on 2019/5/31.
 */
public interface FileService {

    /**
     * 文件上传
     *
     * @param userId
     * @param uploadBytes
     * @param originalFilename 原始文件名
     * @return
     */
    FileDO upload(Long userId, byte[] uploadBytes, String originalFilename);

    /**
     * 文件上传
     *
     * @param userId
     * @param uploadBytes
     * @return
     */
    FileDO upload(long userId, byte[] uploadBytes);

    Page<FileDO> selectPage(Page<FileDO> page, Wrapper<FileDO> wrapper);

    FileDO selectById(Long id);

    boolean insert(FileDO sysFile);

    boolean updateById(FileDO sysFile);

    boolean deleteById(Long id);

    boolean deleteBatchIds(List<Long> asList);

    String uploadBase64(String url, Integer size);
}

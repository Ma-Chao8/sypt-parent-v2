package com.tianma315.core.product.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.product.domain.dto.ProductDto;
import com.tianma315.core.product.domain.pojo.Product;

import com.tianma315.core.product.domain.vo.ProductVo;
import com.tianma315.core.product.vo.ProductFileVO;

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
 * Created by zcm on 2019/6/3.
 */
public interface ProductMapper extends CoreMapper<Product> {
    List<ProductVo> selectListPage(Page<ProductVo> page, ProductDto product);
}

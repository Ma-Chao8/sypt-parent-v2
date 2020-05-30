package com.tianma315.api.core.product.rest;


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
import com.tianma315.api.core.controller.BaseRest;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.product.domain.dto.ProductDto;
import com.tianma315.core.product.domain.vo.ProductVo;
import com.tianma315.core.product.service.ProductService;
import com.tianma315.core.base.Result;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品
 * <p>
 * Created by zcm on 2018/5/14.
 */
@RestController
@RequestMapping("/product")
public class ProductRest extends BaseRest {

    @Autowired
    private ProductService productService;

    /**
     * 分页
     *
     * @return
     */
    @GetMapping("/page")
    @RequiresAuthentication
    Result<Page<ProductVo>> page(int pageNumber, int pageSize, ProductDto product) {
        product.setCompanyId(getCompanyId());
        try {
            Page<ProductVo> page =  productService.getPage(pageNumber, pageSize, product);
            return Result.ok(page);
        } catch (MessageException e) {
            return Result.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }


}

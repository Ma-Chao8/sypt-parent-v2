package com.tianma315.wx.product.rest;


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
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.Result;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.product.service.ProductService;
import com.tianma315.wx.common.BaseController;
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
public class ProductRestController extends BaseController {

    @Autowired
    private ProductService productService;

    /**
     * 分页
     *
     * 
     * @param productName
     * @return
     */
    @GetMapping("/page")
    Result<Page<Product>> page(Integer start, Integer pageSize, String productName) {
        Page<Product> page = new Page<>(start,pageSize);
        Wrapper<Product> wrapper = new EntityWrapper<>();
        wrapper.eq("company_id", getCompany().getCompanyId());
        wrapper.like("product_name",productName);
        try {
            page = productService.selectPage(page,wrapper);
            return Result.ok(page);
        } catch (MessageException e) {
            return Result.fail(e.getMessage(), page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail(page);
    }


}

package com.tianma315.web.product.view;

import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.product.service.ProductService;
import com.tianma315.web.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
 * 产品视图
 * <p>
 * Created by zcm on 2019/6/3.
 */
@Controller
@RequestMapping("/product/view")
public class ProductViewController extends BaseController {

    @Autowired
    private ProductService productService;

    /**
     * @return
     */
    @GetMapping("/list")
    ModelAndView listView() {
        ModelAndView view = new ModelAndView("product/list");
        return view;
    }

    /**
     * @return
     */
    @GetMapping("/add")
    ModelAndView addView() {
        ModelAndView view = new ModelAndView("product/add");
        return view;
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    ModelAndView editView(@PathVariable long id) {
        Product product = productService.getProductById(id);
        return new ModelAndView("product/edit"){{
            addObject("product",product);
        }};
    }
}

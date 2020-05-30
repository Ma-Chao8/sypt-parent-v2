package com.tianma315.core.product.service;

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
import com.tianma315.core.base.CoreService;
import com.tianma315.core.product.domain.dto.ProductDto;
import com.tianma315.core.product.domain.pojo.Product;

import com.tianma315.core.product.domain.vo.ProductVo;
import com.tianma315.core.product.vo.ProductFileVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Description
 * <p>
 * Created by zcm on 2019/6/3.
 */
public interface ProductService extends CoreService<Product> {

    /**
     *
     * @param product
     * @return
     */
    List<Product> getAll(Product product);

    /**
     * @param pageNumber
     * @param pageSize
     * @param product
     * @return
     */
    Page<ProductVo> getPage(int pageNumber, int pageSize, ProductDto product);

    /**
     * @param product_id
     * @return
     */
    ProductFileVO getById(long product_id);

    /**
     * 添加产品
     *
     * @param product
     * @return
     */
    boolean save(ProductFileVO product);

    /**
     * 修改产品信息
     *
     * @param product
     * @return
     */
    boolean modify(ProductFileVO product);

    /**
     * 删除产品
     *
     * @param id
     * @return
     */
    boolean remove(long id);

    boolean batchRemove(Long[] ids);

    HSSFWorkbook exportProduct(Long companyId);


    void importProduct(MultipartFile file, Long companyId, Long userId);

    Boolean addProduct(ProductFileVO productFileVO);

    ProductFileVO getProductById(Long id);

    Boolean updateProduct(ProductFileVO productFileVO);
}

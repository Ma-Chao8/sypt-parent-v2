package com.tianma315.core.invoice.vo;


import com.tianma315.core.product.domain.pojo.Product;

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
 *
 *
 * Created by zcm on 2018/5/14.
 */
public class ProductVo extends Product {

    private List<ScanCodeVo> codes;

    public List<ScanCodeVo> getCodes() {
        return codes;
    }

    public void setCodes(List<ScanCodeVo> codes) {
        this.codes = codes;
    }
}

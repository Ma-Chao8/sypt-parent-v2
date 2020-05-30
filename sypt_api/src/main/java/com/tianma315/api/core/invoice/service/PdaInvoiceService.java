package com.tianma315.api.core.invoice.service;


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
import com.tianma315.core.invoice.domain.vo.DeliverRecordVo;
import com.tianma315.core.invoice.domain.vo.InspectVo;
import com.tianma315.core.invoice.vo.DeliverForm;

import java.util.List;

/**
 * 货单业务
 * <p>
 * Created by zcm on 2018/5/14.
 */
public interface PdaInvoiceService {

    /**
     * 发货
     *
     * @param form
     * @param merchant_id
     * @param product_ident
     * @return
     */
    boolean deliver(DeliverForm form, long user_id, long merchant_id, String product_ident);

    /**
     * 发货记录
     *
     * @param currentPage
     * @param pageSize
     * @param userId
     * @param searchKey
     * @return
     */
    Page<DeliverRecordVo> deliveredRecord(int currentPage, int pageSize, Long userId, String searchKey);


    /**
     * 退货
     *
     * @param codes
     * @param product_ident
     * @return
     */
    boolean returned(List<String> codes, long user_id, long merchant_id, String product_ident);

    /**
     * 稽查
     *
     * @param merchant_id
     * @param code
     * @return
     */
    InspectVo getInspect(long merchant_id, String code, String product_ident);


}

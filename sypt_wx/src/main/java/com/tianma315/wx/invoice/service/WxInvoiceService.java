package com.tianma315.wx.invoice.service;


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

import com.tianma315.core.invoice.domain.vo.InspectVo;
import com.tianma315.core.invoice.domain.vo.InvoiceVO;
import com.tianma315.core.invoice.vo.DeliverForm;


import java.util.List;

/**
 * 货单业务
 * <p>
 * Created by zcm on 2018/5/14.
 */
public interface WxInvoiceService  {

    /**
     * 发货列表
     *
     * @param userId
     * @param page
     * @return
     */
    List<InvoiceVO> deliverList(Long userId, int page);

    InspectVo getInspect(long companyId, String code, String productIdent);

    boolean returned(List<String> codes, Long id, long companyId, String productIdent);

    boolean deliver(DeliverForm form, Long userId, long companyId, String productIdent);
}

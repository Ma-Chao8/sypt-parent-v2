package com.tianma315.core.invoice.vo;

import com.tianma315.core.small_code.domain.SmallRecordDO;


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
 * 扫码实体
 * <p>
 * Created by zcm on 2018/5/14.
 */
public class ScanCodeVo extends SmallRecordDO {
    public static final int TYPE_BIG_CODE = 1; //大码
    public static final int TYPE_SMALL_CODE = 2; //小码

    /* 额外字段 */
    private int type;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public boolean isBig_code() {
        return getType() == TYPE_BIG_CODE;
    }

    public boolean isSmall_code() {
        return getType() == TYPE_SMALL_CODE;
    }
}

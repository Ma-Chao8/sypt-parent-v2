package com.tianma315.core.invoice.domain.vo;


import java.io.Serializable;
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
 * 套标数据模型
 * <p>
 * Created by zcm on 2018/5/14.
 */
public class PackCode implements Serializable {

    private String bigCode;
    private List<String> smallCodes;

    @Override
    public String toString() {
        return "PackCode{" +
                "bigCode='" + bigCode + '\'' +
                ", smallCodes=" + smallCodes.get(0) +"=====2="+smallCodes.get(1) +
                '}';
    }

    public String getBigCode() {
        return bigCode;
    }

    public void setBigCode(String bigCode) {
        this.bigCode = bigCode;
    }

    public List<String> getSmallCodes() {
        return smallCodes;
    }

    public void setSmallCodes(List<String> smallCodes) {
        this.smallCodes = smallCodes;
    }
}

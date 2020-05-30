package com.tianma315.core.utils;


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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 货单编号生成工具类,
 * 生成策略不适合分布式，暂且使用，到分布式系统须重构工具类
 * <p>
 * Created by zcm on 2018/5/14.
 */
public final class InvoiceNumberUtils {
    /*
     * 货单编号生成策列年月日+4位随机数
     *
     */
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyMMddSS");

    private InvoiceNumberUtils() {

    }

    /**
     * 获取当前时间   东八区时间
     *
     * @return
     */
    private static String getCurrentTime() {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(new Date());
    }

    /**
     * 获取随机四位数字符串（数字格式）
     *
     * @return
     */
    private static String randomString() {
        StringBuffer buffer = new StringBuffer();
        String str = "0123456789";
        for (int i = 0; i < 4; i++) {
            int index = (int) (Math.random() * str.length());
            buffer.append(str.charAt(index));
        }
        return buffer.toString();
    }


    /**
     * 生成货单号
     *
     * @return
     */
    public static String generateInvoiceNumber() {
        return getCurrentTime() + randomString();
    }

    public static void main(String[] args) {
        int i = 0;
        while (i <= 10) {
            i++;
            System.out.println(generateInvoiceNumber());
        }
    }


}

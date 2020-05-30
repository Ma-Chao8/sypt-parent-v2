package com.tianma315.core.exception;

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
 * 系统自定义运行时异常，方便控制事物回滚
 * <p>
 * Created by zcm on 2019/5/31.
 */
public class MessageViewException extends MessageException {

    private Object data;


    public MessageViewException(String message) {
        super(message);
    }

    public MessageViewException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageViewException(Throwable cause) {
        super(cause);
    }

    public MessageViewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MessageViewException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public MessageViewException(String message, Object data, Throwable cause) {
        super(message, cause);
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}

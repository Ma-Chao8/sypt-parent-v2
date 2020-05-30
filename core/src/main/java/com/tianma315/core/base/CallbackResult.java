package com.tianma315.core.base;

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
 * 接口返回数据结构
 * <p>
 * Created by zcm on 2019/7/30.
 */
public class CallbackResult extends BaseModel {
    private boolean success = false;//是否成功
    private String message;//返回消息
    private Object details;//返回对象

    public CallbackResult() {
    }

    public CallbackResult(boolean success) {
        this.success = success;
    }

    public CallbackResult(boolean success, Object details) {
        super();
        this.success = success;
        this.details = details;
    }

    public CallbackResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CallbackResult(boolean success, String message, Object details) {
        this.success = success;
        this.message = message;
        this.details = details;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
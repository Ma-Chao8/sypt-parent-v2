package com.tianma315.core.company.domain.pojo;

import com.tianma315.core.base.BaseModel;

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
 * 扫码规则配置
 * <p>
 * Created by Lgc on 2019/9/06.
 */
public class CodeConfig extends BaseModel {

    private List<String> bigCodeConfigs; //大码规则
    private List<String> smallCodeConfig; //小码规则

    public List<String> getBigCodeConfigs() {
        return bigCodeConfigs;
    }

    public void setBigCodeConfigs(List<String> bigCodeConfigs) {
        this.bigCodeConfigs = bigCodeConfigs;
    }

    public List<String> getSmallCodeConfig() {
        return smallCodeConfig;
    }

    public void setSmallCodeConfig(List<String> smallCodeConfig) {
        this.smallCodeConfig = smallCodeConfig;
    }
}

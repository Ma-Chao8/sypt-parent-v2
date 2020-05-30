package com.tianma315.core.coderecord.domain.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tianma315.core.base.BaseModel;

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
 * Description
 * <p>
 * Created by zcm on 2019/9/27.
 */
@TableName(value = "codes")
public class Code extends BaseModel {
    public static final int TYPE_TRACE = 0; //溯源系统使用的码
    public static final int TYPE_INVOICE = 1; //防串货使用的码


    @TableId
    private long id;//` int(11) NOT NULL AUTO_INCREMENT,
    private String bigCode;//` varchar(64) DEFAULT NULL COMMENT '大码',
    private String smallCode;//` varchar(64) DEFAULT NULL COMMENT '小码',
    private String fwCode;//` varchar(255) DEFAULT NULL COMMENT '防伪码',
    private String sequence;//` varchar(255) DEFAULT NULL COMMENT '序号',
    private long companyId;//` bigint(20) DEFAULT '0' COMMENT '商户id',
    private String qrColour;//` varchar(32) DEFAULT NULL COMMENT '二维码彩码色值',
    private String sqColour;//` varchar(32) DEFAULT NULL COMMENT '序号色值',
    private int type; //

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBigCode() {
        return bigCode;
    }

    public void setBigCode(String bigCode) {
        this.bigCode = bigCode;
    }

    public String getSmallCode() {
        return smallCode;
    }

    public void setSmallCode(String smallCode) {
        this.smallCode = smallCode;
    }

    public String getFwCode() {
        return fwCode;
    }

    public void setFwCode(String fwCode) {
        this.fwCode = fwCode;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getQrColour() {
        return qrColour;
    }

    public void setQrColour(String qrColour) {
        this.qrColour = qrColour;
    }

    public String getSqColour() {
        return sqColour;
    }

    public void setSqColour(String sqColour) {
        this.sqColour = sqColour;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

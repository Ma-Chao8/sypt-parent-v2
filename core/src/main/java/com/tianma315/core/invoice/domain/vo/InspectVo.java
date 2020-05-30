package com.tianma315.core.invoice.domain.vo;
import java.io.Serializable;
import java.util.Date;


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
 * 稽查信息
 * <p>
 * Created by Lgc on 2019/8/24.
 */
public class InspectVo implements Serializable {

    private String bigCode; //大码
    private String smallCode; //小码
    private String agentName; //经销商
    private String linkman; //联系人
    private String tel; //电话
    private String invoiceId;
    private String productName; //产品;
    private Date deliverDate; //发货时间
    private String productIdent;

    //
    private String inspectCode; //稽查码


    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getProductIdent() {
        return productIdent;
    }

    public void setProductIdent(String productIdent) {
        this.productIdent = productIdent;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }
}

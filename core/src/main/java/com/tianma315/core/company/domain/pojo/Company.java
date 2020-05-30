package com.tianma315.core.company.domain.pojo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tianma315.core.base.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

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
 * 商户/公司表数据模型
 * <p>
 * Created by zcm on 2019/6/24.
 */
@TableName("company")
public class Company extends BaseModel {
    @TableId
    private long companyId;//              bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    private long userId;//                 bigint(20) DEFAULT NULL COMMENT '所属用户',
    private String productIdent;//           varchar(255) DEFAULT NULL COMMENT '产品标识',
    @NotBlank(message = "商户名称不能为空")
    private String companyName;//           varchar(100) DEFAULT NULL COMMENT '商户名称',
    private String companyLegalPerson;//   varchar(50) DEFAULT NULL COMMENT '企业法人',
    private String linkman;//                 varchar(50) DEFAULT NULL COMMENT '联系人',
    private String tel;//                     varchar(100) DEFAULT NULL COMMENT '电话',
    private String companyAddress;//        varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '公司地址',
    private Date createDate;//             datetime DEFAULT NULL COMMENT '注册时间',
    private long createBy; //创建人id
    private String introduce;//               text COMMENT '公司介绍',
    private String logo;//                    varchar(255) DEFAULT NULL COMMENT 'logo',
    private String level;//                   varchar(64) DEFAULT NULL COMMENT '公司等级:1，3，5星级企业',
    private String rank;//                    varchar(64) DEFAULT NULL COMMENT '省评级，省龙头企业，市龙头企业',
    private String longitude;//               varchar(64) DEFAULT NULL COMMENT '经度',
    private String latitude;//                varchar(64) DEFAULT NULL COMMENT '纬度',
    private Integer industryId;
    private String bigCodeConfig;
    private String smallCodeConfig;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getProductIdent() {
        return productIdent;
    }

    public void setProductIdent(String productIdent) {
        this.productIdent = productIdent;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLegalPerson() {
        return companyLegalPerson;
    }

    public void setCompanyLegalPerson(String companyLegalPerson) {
        this.companyLegalPerson = companyLegalPerson;
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

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public String getBigCodeConfig() {
        return bigCodeConfig;
    }

    public void setBigCodeConfig(String bigCodeConfig) {
        this.bigCodeConfig = bigCodeConfig;
    }

    public String getSmallCodeConfig() {
        return smallCodeConfig;
    }

    public void setSmallCodeConfig(String smallCodeConfig) {
        this.smallCodeConfig = smallCodeConfig;
    }
}

package com.tianma315.core.traceability.vo;


import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.procedures.domain.ProceduresBanner;
import com.tianma315.core.procedures.domain.ProceduresFootlink;
import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.product.vo.ProductFileVO;
import com.tianma315.core.traceability.domain.TraceabilityFileDO;

import java.util.List;

public class TraceabilityFileVO extends TraceabilityFileDO {

    private List<TyMenuVO> menuList;
    private String productName;
    private String name;
    private Integer isBindReport;
    private String theme; //主题
    private String themeName;//主题名称
    private Product product;
    private Company company;
    /**
    * 主题编号
    */
    private Integer themeId;
    /**
    * 模版名称
    */
    private String procedureName;
    /**
    * 模版id
    */
    private Integer proceduresId;

    /**
     * 轮播图列表
     */
    private List<ProceduresBanner> proBanners;
    /**
     * 底部链接
     */
    private List<ProceduresFootlink> proFootLinks;


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ProceduresFootlink> getProFootLinks() {
        return proFootLinks;
    }

    public void setProFootLinks(List<ProceduresFootlink> proFootLinks) {
        this.proFootLinks = proFootLinks;
    }

    public Integer getProceduresId() {
        return proceduresId;
    }

    public void setProceduresId(Integer proceduresId) {
        this.proceduresId = proceduresId;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public List<ProceduresBanner> getProBanners() {
        return proBanners;
    }

    public void setProBanners(List<ProceduresBanner> proBanners) {
        this.proBanners = proBanners;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getIsBindReport() {
        return isBindReport;
    }

    public void setIsBindReport(Integer isBindReport) {
        this.isBindReport = isBindReport;
    }

    public List<TyMenuVO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<TyMenuVO> menuList) {
        this.menuList = menuList;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
}

package com.tianma315.core.product.vo;

import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.product.domain.pojo.ProductFieldDO;
import com.tianma315.core.product.domain.vo.ProductFieldVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ProductFileVO extends Product {
    private MultipartFile file;
    private List<ProductFieldDO> productFieldDOList;
    private List<ProductFieldVO> productFieldVOS;

    public List<ProductFieldDO> getProductFieldDOList() {
        return productFieldDOList;
    }

    public void setProductFieldDOList(List<ProductFieldDO> productFieldDOList) {
        this.productFieldDOList = productFieldDOList;
    }

    public List<ProductFieldVO> getProductFieldVOS() {
        return productFieldVOS;
    }

    public void setProductFieldVOS(List<ProductFieldVO> productFieldVOS) {
        this.productFieldVOS = productFieldVOS;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}

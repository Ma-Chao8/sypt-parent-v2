package com.tianma315.core.trace.vo;



import java.util.List;

/**
 * 图片json接收
 *
 * @author gwl
 * @create 2018-07-29 16:28
 **/
public class ImagesDo {
    private Integer id;
    private List<ImgValueDo> value;
    private Integer size;

    private Long invoiceId;


    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ImgValueDo> getValue() {
        return value;
    }

    public void setValue(List<ImgValueDo> value) {
        this.value = value;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

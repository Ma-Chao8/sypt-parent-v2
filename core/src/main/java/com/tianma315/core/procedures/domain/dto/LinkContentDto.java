package com.tianma315.core.procedures.domain.dto;

import com.tianma315.core.procedures.domain.LinkContentDO;
import com.tianma315.core.trace.vo.ImgValueDo;

/**
 * @author lgc
 * @createDate 2020/5/14 - 11:35
 */
public class LinkContentDto extends LinkContentDO {
    private ImgValueDo[] imgValues;

    public ImgValueDo[] getImgValues() {
        return imgValues;
    }

    public void setImgValues(ImgValueDo[] imgValues) {
        this.imgValues = imgValues;
    }
}

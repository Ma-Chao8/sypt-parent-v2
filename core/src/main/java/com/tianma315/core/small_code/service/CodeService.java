package com.tianma315.core.small_code.service;

import com.tianma315.core.base.CoreService;
import com.tianma315.core.coderecord.domain.pojo.Code;
import com.tianma315.core.invoice.vo.ScanCodeVo;

import java.util.List;

public interface CodeService extends CoreService<Code> {

    /**
     * 校验码
     *
     * @param companyId
     * @param productIdent
     * @param codes
     * @return
     */
    boolean validate(Long companyId, String productIdent, List<ScanCodeVo> codes);

    /**
     * 是否允许发货
     *
     * @param merchant_id
     * @param product_ident
     * @param codes
     * @return
     */
    boolean allowDelivered(long merchant_id, String product_ident, List<ScanCodeVo> codes);

    /**
     * 可退货
     *
     * @param companyId
     * @param productIdent
     * @param codes
     * @return
     */
    boolean returnable(Long companyId, String productIdent, List<String> codes);

    /**
     * 可稽查
     *
     * @param companyId
     * @param productIdent
     * @param codes
     * @return
     */
    boolean inspectable(Long companyId, String productIdent, List<String> codes);
}

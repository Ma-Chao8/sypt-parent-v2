package com.tianma315.core.material.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.material.domain.PurchaseDO;
import com.tianma315.core.material.dto.PurchaseDto;
import com.tianma315.core.material.vo.PurchaseVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 
 * <pre>
 * 原材料进库表
 * </pre>
 * <small> 2019-06-24 11:23:31 | Wen</small>
 */
public interface PurchaseService extends CoreService<PurchaseDO> {
    Page<PurchaseVO> getPurchaseVOPage(Integer pageNumber, Integer pageSize, PurchaseDto purchaseDTO);

    Boolean addPurchase(PurchaseDO purchaseDO);

    Boolean deletePurchase(Integer id);

    HSSFWorkbook exportPurchase(Long companyId);

    void importPurchase(MultipartFile file,Long companyId,Long userId);

    List<PurchaseDO> getPurchaseDOList(Long companyId);

    PurchaseVO gerPurchaseVO(Long purchaseId);
}

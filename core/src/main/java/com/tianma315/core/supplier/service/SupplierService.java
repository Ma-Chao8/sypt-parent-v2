package com.tianma315.core.supplier.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.supplier.VO.SupplierAndTypeVO;
import com.tianma315.core.supplier.domain.SupplierDO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 
 * <pre>
 * 供应商
 * </pre>
 * <small> 2019-06-24 09:58:51 | Wen</small>
 */
public interface SupplierService extends CoreService<SupplierDO> {
    Page<SupplierAndTypeVO> getSupplierDOPage(Integer pageNumber, Integer pageSize, SupplierDO supplierDTO);

    boolean addSupplierDO(SupplierDO supplierDTO);

    boolean deleteSupplierDOById(Integer id);

    List<SupplierDO> getSupplierList(Long companyId);

    HSSFWorkbook exportSupplier(Long companyId, Long userId);

    void importSupplier(MultipartFile file, Long companyId, Long userId);


}

package com.tianma315.core.material.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.material.domain.MaterialDO;
import com.tianma315.core.material.vo.MaterialAndTemplateVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 
 * <pre>
 * 原材料
 * </pre>
 * <small> 2019-06-24 11:23:31 | Wen</small>
 */
public interface MaterialService extends CoreService<MaterialDO> {
    Page<MaterialDO> getMaterialAndTemplateVOPage(Integer pageNumber, Integer pageSize, MaterialDO materialDTO);

    Boolean addMaterialAndTemplate(MaterialDO materialDO);

    Boolean deleteMaterial(Integer id);

    List<MaterialDO> getMaterialList(Long companyId);

    HSSFWorkbook exportMaterial(Long companyId);

    void importMaterial(MultipartFile file, Long companyId, Long userId);

}

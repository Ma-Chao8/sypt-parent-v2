package com.tianma315.core.material.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.CompanyId;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.material.domain.PurchaseDO;
import com.tianma315.core.material.vo.MaterialAndTemplateVO;
import com.tianma315.core.supplier.domain.SupplierDO;
import com.tianma315.core.utils.CellUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.material.dao.MaterialDao;
import com.tianma315.core.material.domain.MaterialDO;
import com.tianma315.core.material.service.MaterialService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * <pre>
 * 原材料
 * </pre>
 * <small> 2019-06-24 11:23:31 | Wen</small>
 */
@Service
public class MaterialServiceImpl extends CoreServiceImpl<MaterialDao, MaterialDO> implements MaterialService {
    @Autowired
    private MaterialDao materialDao;

    @Override
    public Page<MaterialDO> getMaterialAndTemplateVOPage(Integer pageNumber, Integer pageSize, MaterialDO materialDTO) {
        Page<MaterialDO> page = new Page<>(pageNumber,pageSize);
//        List<MaterialAndTemplateVO> materialAndTemplateVOList = materialDao.getMaterialAndTemplateVOPage();
        EntityWrapper<MaterialDO> wrapper = new EntityWrapper<>();
        wrapper.eq("state", StateEnum.not_del.getIndex());
        if (materialDTO.getCompanyId() !=null){
            wrapper.eq("company_id", materialDTO.getCompanyId());
        }
        page = selectPage(page,wrapper);
//        page.setRecords(list);
        return page;
    }

    @Override
    public Boolean addMaterialAndTemplate(MaterialDO materialDO) {
        materialDO.setState(StateEnum.not_del.getIndex());
        Boolean result = insert(materialDO);
        return result;
    }

    @Override
    public Boolean deleteMaterial(Integer id) {
        MaterialDO materialDO = new MaterialDO();
        materialDO.setMaterialId(id);
        materialDO.setState(StateEnum.del.getIndex());
        return updateById(materialDO);
    }

    @Override
    public List<MaterialDO> getMaterialList(Long companyId) {
        EntityWrapper<MaterialDO> wrapper = new EntityWrapper<>();
        wrapper.eq("state", StateEnum.not_del.getIndex());
        wrapper.eq("company_id", companyId);
        List<MaterialDO> materialDOS = selectList(wrapper);
        return materialDOS;
    }

    @Override
    public HSSFWorkbook exportMaterial(Long companyId) {
        String[] title = {"名称","规格","保质期","计量单位","品牌","单价","备注"};
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("原材料记录模板");

//        sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));//第一行第一列开始，跨8列
        HSSFRow titleRow = sheet.createRow(0);//第0行

        //标题样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)18);
        titleStyle.setFont(font);

        //生成标题
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("原材料模板（红色标识列为必填）");
        titleCell.setCellStyle(titleStyle);

        //红色字体
        HSSFCellStyle redStyle = wb.createCellStyle();
        redStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont redFont = wb.createFont();
        redFont.setBold(true);
        redFont.setColor(Font.COLOR_RED);
        redStyle.setFont(redFont);

        //默认字体样式
        HSSFCellStyle defaultStyle = wb.createCellStyle();
        defaultStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont defaultFont = wb.createFont();
        defaultFont.setBold(true);
        defaultStyle.setFont(defaultFont);

        //声明列对象
        HSSFCell cell = null;
        //创建表头
        HSSFRow headRow = sheet.createRow(1);
        for(int i=0;i<title.length;i++){
            //最后一个不用填，备注是选填
            if (i != title.length-1){
                cell = headRow.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(redStyle);
            }else{
                cell = headRow.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(defaultStyle);
            }
        }

        return wb;
    }

    @Override
    public void importMaterial(MultipartFile file, Long companyId, Long userId) {
        Boolean flag = CellUtil.isExcel(file.getOriginalFilename());
        if (!flag){
            throw new MessageException("只支持Excel文件");
        }

        try {
            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum(); //返回行数下标 从0开始
            //前两行都不是内容，所以从2开始
            for (int i = 2; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                short num = row.getLastCellNum();
                MaterialDO materialDO = new MaterialDO();
                materialDO.setCompanyId(companyId.intValue());
                materialDO.setCreateBy(userId);
                materialDO.setCreateDate(Calendar.getInstance().getTime());
                materialDO.setState(StateEnum.not_del.getIndex());


                //设置名称
                Cell materialNameCell = row.getCell(0);
                if (CellUtil.isCellEmpty(materialNameCell)){
                    throw new MessageException("名称不能为空");
                }
                materialDO.setMaterialName(materialNameCell.getStringCellValue());

                //设置规格
                Cell specsCell = row.getCell(1);
                if (CellUtil.isCellEmpty(specsCell)){
                    throw new MessageException("规格不能为空");
                }
                materialDO.setSpecs(specsCell.getStringCellValue());



                //设置保质期
                Cell shelflifeCell = row.getCell(2);
                if (CellUtil.isCellEmpty(shelflifeCell)){
                    throw new MessageException("保质期不能为空");
                }
                materialDO.setShelflife(shelflifeCell.getStringCellValue());

                //设置计量单位
                Cell unitCell = row.getCell(3);
                if (CellUtil.isCellEmpty(unitCell)){
                    throw new MessageException("计量单位不能为空");
                }
                materialDO.setUnit(unitCell.getStringCellValue());

                //设置品牌
                Cell brandCell = row.getCell(4);
                if (CellUtil.isCellEmpty(brandCell)){
                    throw new MessageException("品牌不能为空");
                }
                materialDO.setBrand(brandCell.getStringCellValue());

                //设置单价
                Cell priceCell = row.getCell(5);
                if (CellUtil.isCellEmpty(priceCell) ){
                    throw new MessageException("单价不能为空");
                }
                BigDecimal bigDecimal = new BigDecimal(priceCell.getStringCellValue());
                materialDO.setPrice(bigDecimal);

                //设置备注
                Cell markCell = row.getCell(6);
                if (markCell !=null){
                    materialDO.setMark(markCell.getStringCellValue());
                }
                insert(materialDO);

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

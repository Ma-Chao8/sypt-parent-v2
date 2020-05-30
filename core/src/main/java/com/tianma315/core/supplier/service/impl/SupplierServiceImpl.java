package com.tianma315.core.supplier.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.CompanyId;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.supplier.VO.SupplierAndTypeVO;
import com.tianma315.core.supplier.domain.SupplierTypeDO;
import com.tianma315.core.supplier.service.SupplierTypeService;
import com.tianma315.core.utils.CellUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.supplier.dao.SupplierDao;
import com.tianma315.core.supplier.domain.SupplierDO;
import com.tianma315.core.supplier.service.SupplierService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * <pre>
 * 供应商
 * </pre>
 * <small> 2019-06-24 09:58:51 | Wen</small>
 */
@Service
public class SupplierServiceImpl extends CoreServiceImpl<SupplierDao, SupplierDO> implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;


    @Autowired
    private SupplierTypeService supplierTypeService;

    @Override
    public Page<SupplierAndTypeVO> getSupplierDOPage(Integer pageNumber, Integer pageSize, SupplierDO supplierDTO) {
        Page<SupplierAndTypeVO> page = new Page<>(pageNumber, pageSize);
//        Wrapper<SupplierDO> wrapper = new EntityWrapper<SupplierDO>(supplierDTO);
//        wrapper.eq("state",0);
        List<SupplierAndTypeVO> supplierAndTypeVOList = supplierDao.getSupplierAndTypeVO(page,supplierDTO);
//        page = selectPage(page, wrapper);
//        int total = selectCount(wrapper);
        page.setRecords(supplierAndTypeVOList);
//        page.setTotal(supplierAndTypeVOList.size());
        return page;
    }

    @Override
    public boolean addSupplierDO(SupplierDO supplierDTO) {
        supplierDTO.setState(StateEnum.not_del.getIndex());
        boolean flag = insert(supplierDTO);
        return flag;
    }

    @Override
    public boolean deleteSupplierDOById(Integer id) {
        SupplierDO supplier = new SupplierDO();
        supplier.setSupplierId(id);
        supplier.setState(StateEnum.del.getIndex());
        boolean flag = updateById(supplier);
        return flag;
    }

    @Override
    public List<SupplierDO> getSupplierList(Long companyId) {
        Wrapper<SupplierDO> wrapper = new EntityWrapper<>();
        wrapper.eq("state", StateEnum.not_del.getIndex())
                .eq("company_id", companyId);
        List<SupplierDO> supplierDOList = selectList(wrapper);
        return supplierDOList;
    }

    @Override
    public HSSFWorkbook exportSupplier(Long companyId, Long userId) {
        String[] title = {"供应商类型","供应商名称","联系人","联系人电话","备注"};
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("供应商模板");

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
        titleCell.setCellValue("供应商模板（红色标识列为必填）");
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
            //最后一个是备注，备注选填，字体黑色
            if (i == title.length-1){
                cell = headRow.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(defaultStyle);
            }else{
                cell = headRow.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(redStyle);
            }
        }

        SupplierTypeDO supplierTypeDO = new SupplierTypeDO();
        supplierTypeDO.setCompanyId(companyId);
        List<SupplierTypeDO> supplierDOS = supplierTypeService.getSupplierTypeList(supplierTypeDO);
        List<String> supplierList = supplierDOS.stream().map(item -> item.getTypeName()).collect(Collectors.toList());
        String[] supplierNameArr = supplierList.toArray(new String[supplierList.size()]);

        //设置设备类型为下拉框选项
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(supplierNameArr);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(2,
                10000, 0, 0);
        HSSFDataValidation materialData = new HSSFDataValidation(
                regions, constraint);
        sheet.addValidationData(materialData);
        return wb;
    }

    @Override
    public void importSupplier(MultipartFile file, Long companyId, Long userId) {
        Boolean flag = CellUtil.isExcel(file.getOriginalFilename());
        if (!flag){
            throw new MessageException("只支持Excel文件");
        }
        SupplierTypeDO param = new SupplierTypeDO();
        param.setCompanyId(companyId);
        List<SupplierTypeDO> supplierTypeDOList = supplierTypeService.getSupplierTypeList(param);

        try {
            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum(); //返回行数下标 从0开始
            //前两行都不是内容，所以从2开始
            for (int i = 2; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                short num = row.getLastCellNum();
                SupplierDO supplierDO = new SupplierDO();
                supplierDO.setCreateBy(userId);
                supplierDO.setCreateDate(Calendar.getInstance().getTime());
                supplierDO.setState(0);
                supplierDO.setCompanyId(companyId);

                //设置供应商id
                Cell supplierTypeCell = row.getCell(0);
                if (CellUtil.isCellEmpty(supplierTypeCell)){
                    throw new MessageException("供应商类型不能为空");
                }
                List<SupplierTypeDO> supplierTypeFilterResult = supplierTypeDOList.stream()
                        .filter(item -> item.getTypeName().equals(supplierTypeCell.getStringCellValue()))
                        .collect(Collectors.toList());
                supplierDO.setSupplierTypeId(supplierTypeFilterResult.get(0).getSupplierTypeId());

                //设置供应商名称
                Cell supplierNameCell = row.getCell(1);
                if (CellUtil.isCellEmpty(supplierNameCell)){
                    throw new MessageException("供应商名称不能为空");
                }
                supplierDO.setSupplierName(supplierNameCell.getStringCellValue());


                //设置联系人
                Cell linkManCell = row.getCell(2);
                if (CellUtil.isCellEmpty(linkManCell)){
                    throw new MessageException("联系人不能为空");
                }
                supplierDO.setLinkman(linkManCell.getStringCellValue());

                //设置联系人电话
                Cell telCell = row.getCell(3);
                if (CellUtil.isCellEmpty(telCell)){
                    throw new MessageException("联系人电话不能为空");
                }
                supplierDO.setTel(telCell.getStringCellValue());

                //设置备注
                Cell markCell = row.getCell(4);
                String mark = "";
                if (markCell != null){
                    markCell.setCellType(CellType.STRING);
                    mark = markCell.getStringCellValue();
                }
                supplierDO.setMark(mark);

                insert(supplierDO);

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

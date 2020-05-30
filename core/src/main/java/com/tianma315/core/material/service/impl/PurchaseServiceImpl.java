package com.tianma315.core.material.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.material.domain.MaterialDO;
import com.tianma315.core.material.dto.PurchaseDto;
import com.tianma315.core.material.service.MaterialService;
import com.tianma315.core.material.vo.PurchaseVO;
import com.tianma315.core.supplier.domain.SupplierDO;
import com.tianma315.core.supplier.service.SupplierService;
import com.tianma315.core.utils.BeanHump;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.material.dao.PurchaseDao;
import com.tianma315.core.material.domain.PurchaseDO;
import com.tianma315.core.material.service.PurchaseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * <pre>
 * 原材料进库表
 * </pre>
 * <small> 2019-06-24 11:23:31 | Wen</small>
 */
@Service
public class PurchaseServiceImpl extends CoreServiceImpl<PurchaseDao, PurchaseDO> implements PurchaseService {
    @Autowired
    private PurchaseDao purchaseDao;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private SupplierService supplierService;

    @Override
    public Page<PurchaseVO> getPurchaseVOPage(Integer pageNumber, Integer pageSize, PurchaseDto purchaseDTO) {
       purchaseDTO.setSortName(BeanHump.camelToUnderline(purchaseDTO.getSortName()));
        Page<PurchaseVO> page = new Page(pageNumber,pageSize);
        List<PurchaseVO> purchaseVOList = purchaseDao.getPurchaseVO(page,purchaseDTO);
//        page.setTotal(purchaseVOList.size());
        page.setRecords(purchaseVOList);
        return page;
    }

    @Override
    public Boolean addPurchase(PurchaseDO purchaseDO) {
        purchaseDO.setState(0);
        return insert(purchaseDO);
    }

    @Override
    public Boolean deletePurchase(Integer id) {
        PurchaseDO purchaseDO = new PurchaseDO();
        purchaseDO.setState(1);
        purchaseDO.setPurchaseId(id);
        return updateById(purchaseDO);
    }

    @Override
    public HSSFWorkbook exportPurchase(Long companyId) {
        String[] title = {"原料","原料生产日期(2019/1/1)","供应商","批次","采购人","入库日期(2019/1/1)","数量","入库标识(原料名称+批次)"};
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
        titleCell.setCellValue("原材料记录模板（红色标识列为必填）");
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
            //下标为0,2,3,4是必填列
            if (i == 3 || i == 4 || i == 0 || i == 2){
                cell = headRow.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(redStyle);
            }else{
                cell = headRow.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(defaultStyle);
            }
        }

//        List<DeviceTypeDO> list = deviceTypeService.getDeviceTypeDOList();
//        String[] deviceTypeArr = new String[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            deviceTypeArr[i] = list.get(i).getTypeName();
//        }
        List<MaterialDO> materialDOS = materialService.getMaterialList(companyId);
        List<String> materialList = materialDOS.stream().map(item -> item.getMaterialName()).collect(Collectors.toList());
        String[] materialNameArr = materialList.toArray(new String[materialList.size()]);

        //设置设备类型为下拉框选项
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(materialNameArr);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(2,
                10000, 0, 0);
        HSSFDataValidation materialData = new HSSFDataValidation(
                regions, constraint);
        sheet.addValidationData(materialData);

        List<SupplierDO> supplierDOS = supplierService.getSupplierList(companyId);
        List<String> supplierList = supplierDOS.stream().map(item -> item.getSupplierName()).collect(Collectors.toList());
        String[] supplierListNameArr = supplierList.toArray(new String[supplierList.size()]);

        //设置设备类型为下拉框选项
        DVConstraint supplierConstraint = DVConstraint.createExplicitListConstraint(supplierListNameArr);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList supplierRegions = new CellRangeAddressList(2,
                10000, 2, 2);
        HSSFDataValidation supplierData = new HSSFDataValidation(
                supplierRegions, supplierConstraint);
        sheet.addValidationData(supplierData);
        return wb;
    }

    @Override
    public void importPurchase(MultipartFile file, Long companyId, Long userId) {
        Boolean flag = isExcel(file.getOriginalFilename());
        if (!flag){
            throw new MessageException("只支持Excel文件");
        }

        List<MaterialDO> materialDOList = materialService.getMaterialList(companyId);
        List<SupplierDO> supplierDOList = supplierService.getSupplierList(companyId);

        try {
            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum(); //返回行数下标 从0开始
            //前两行都不是内容，所以从2开始
            for (int i = 2; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                short num = row.getLastCellNum();
                PurchaseDO purchaseDO = new PurchaseDO();
                purchaseDO.setCompanyId(companyId.intValue());
                purchaseDO.setCreateBy(userId);
                purchaseDO.setCreateDate(Calendar.getInstance().getTime());
                purchaseDO.setState(StateEnum.not_del.getIndex());

                //设置原料id
                Cell materialCell = row.getCell(0);
                if (isCellEmpty(materialCell)){
                    throw new MessageException("原料不能为空");
                }
                List<MaterialDO> materialFilterResult = materialDOList.stream()
                        .filter(item -> item.getMaterialName().equals(materialCell.getStringCellValue()))
                        .collect(Collectors.toList());
                purchaseDO.setMaterialId(materialFilterResult.get(0).getMaterialId());

                //设置出厂时间
                Cell dateOfProductionCell = row.getCell(1);
                if (dateOfProductionCell!=null){
                    purchaseDO.setDateOfProduction(dateOfProductionCell.getDateCellValue());
                }


                //设置供应商
                Cell supplierCell = row.getCell(2);
                if (isCellEmpty(supplierCell)){
                    throw new MessageException("供应商不能空");
                }
                List<SupplierDO> supplierFilterResult = supplierDOList.stream()
                        .filter(item -> item.getSupplierName().equals(supplierCell.getStringCellValue()))
                        .collect(Collectors.toList());
                purchaseDO.setSupplierId(supplierFilterResult.get(0).getSupplierId());

                //设置批次
                Cell purchaseNoCell = row.getCell(3);
                if (isCellEmpty(purchaseNoCell)){
                    throw new MessageException("批次不能为空");
                }
                purchaseDO.setPurchaseNo(purchaseNoCell.getStringCellValue());

                //设置采购人
                Cell purchaseCell = row.getCell(4);
                if (isCellEmpty(purchaseCell)){
                    throw new MessageException("采购人不能为空");
                }
                purchaseDO.setPurchaser(purchaseCell.getStringCellValue());

                //设置入库日期
                Cell dateStoreCell = row.getCell(5);
                if (dateStoreCell!=null){
                    purchaseDO.setDateStore(dateStoreCell.getDateCellValue());
                }

                //设置数量
                Cell numberCell = row.getCell(6);
                Integer number = 0;
                if (!isCellEmpty(numberCell)){
                    number = Integer.parseInt(numberCell.getStringCellValue());
                }
                purchaseDO.setNumber(number);

                //设置入库标识
                Cell identifierCell = row.getCell(7);
                if (identifierCell != null ){
                    identifierCell.setCellType(CellType.STRING);
                    purchaseDO.setIdentifier(identifierCell.getStringCellValue());
                }

                insert(purchaseDO);

            }
        } catch (IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public List<PurchaseDO> getPurchaseDOList(Long companyId) {
        EntityWrapper<PurchaseDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("state",StateEnum.not_del.getIndex());
        entityWrapper.eq("company_id",companyId);
        List<PurchaseDO> purchaseDOList = selectList(entityWrapper);
        return purchaseDOList;
    }

    @Override
    public PurchaseVO gerPurchaseVO(Long purchaseId) {
        PurchaseDO purchaseDO = selectById(purchaseId);
        PurchaseVO purchaseVO = new PurchaseVO();
        if (purchaseDO!=null){
            MaterialDO materialDO = materialService.selectById(purchaseDO.getMaterialId());
            SupplierDO supplierDO = supplierService.selectById(purchaseDO.getSupplierId());
            BeanUtils.copyProperties(purchaseDO,purchaseVO);
            purchaseVO.setMaterialName(materialDO.getMaterialName());
            purchaseVO.setCheckImg(materialDO.getShelflife());
            purchaseVO.setBrand(materialDO.getBrand());
            purchaseVO.setSupplierName(supplierDO.getSupplierName());
        }

        return purchaseVO;
    }

    public Boolean isExcel(String fileName){
        Boolean flag;
        String[] nameArray =  fileName.split("\\.");
        Integer index =nameArray.length - 1;
        String name  = nameArray[index];
        if ("xls".equals(name)){
            flag = true;
        }else if("xlsx".equals(name)){
            flag = true;
        }else if("xlsm".equals(name)){
            flag = true;
        }else{
            flag = false;
        }
        return flag;
    }

    public Boolean isCellEmpty(Cell cell){
        if (cell == null){
            return true;
        }else{
            cell.setCellType(CellType.STRING);
            if (cell.getStringCellValue()==null){
                return true;
            }else{
                return false;
            }
        }

    }
}

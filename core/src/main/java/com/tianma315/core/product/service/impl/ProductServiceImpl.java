package com.tianma315.core.product.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.attr.domain.AttrFieldDO;
import com.tianma315.core.attr.service.AttrFieldService;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.IFastException;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.material.domain.MaterialDO;
import com.tianma315.core.product.dao.ProductMapper;
import com.tianma315.core.product.domain.dto.ProductDto;
import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.product.domain.pojo.ProductFieldDO;
import com.tianma315.core.product.domain.vo.ProductFieldVO;

import com.tianma315.core.product.domain.vo.ProductVo;
import com.tianma315.core.product.service.ProductFieldService;
import com.tianma315.core.product.service.ProductService;
import com.tianma315.core.product.vo.ProductFileVO;
import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.service.FileService;
import com.tianma315.core.utils.BeanHump;
import com.tianma315.core.utils.CellUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

//                   _ooOoo_
//                  o8888888o
//                  88" . "88
//                  (| -_- |)
//                  O\  =  /O
//               ____/`---'\____
//             .'  \\|     |//  `.
//            /  \\|||  :  |||//  \
//           /  _||||| -:- |||||-  \
//           |   | \\\  -  /// |   |
//           | \_|  ''\---/''  |   |
//           \  .-\__  `-`  ___/-. /
//         ___`. .'  /--.--\  `. . __
//      ."" '<  `.___\_<|>_/___.'  >'"".
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//     \  \ `-.   \_ __\ /__ _/   .-` /  /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//         佛祖保佑       永无BUG

/**
 * Description
 * <p>
 * Created by zcm on 2019/6/3.
 */
@Service
@Transactional
public class ProductServiceImpl extends CoreServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private FileService fileService;
    @Autowired
    private ProductFieldService productFieldService;
    @Autowired
    private AttrFieldService attrFieldService;
    @Autowired
    private ProductMapper productMapper;


    @Override
    public List<Product> getAll(Product product) {
        List<Product> products = selectList(new EntityWrapper<Product>() {{
            eq("status", Product.STATUS_ENABLE);
            eq("company_id",product.getCompanyId());
            like("product_name", product.getProductName());
        }});
        return products;
    }

    @Override
    public Page<ProductVo> getPage(int pageNumber, int pageSize, ProductDto product) {
//        product.setSortName(BeanHump.camelToUnderline(product.getSortName()));
       Page<ProductVo> page = new Page<>(pageNumber, pageSize);
//        boolean isAsc=false;
//        if (product.getSortOrder() !=null){
//            if (product.getSortOrder().equals("asc")){
//                isAsc=true;
//            }
//        }
//        EntityWrapper<Product> wrapper = new EntityWrapper<>();
//         wrapper.eq("status", Product.STATUS_ENABLE)
//                .eq("company_id",product.getCompanyId())
//                .like("product_name", product.getProductName())
//                .orderBy(true,product.getSortName(),isAsc);
        List<ProductVo> productVOS = productMapper.selectListPage(page, product);
        page.setRecords(productVOS);
        return page;
    }

    @Override
    public ProductFileVO getById(long product_id) {
        Product productDO = selectById(product_id);
        ProductFileVO productVO = new ProductFileVO();
        BeanUtils.copyProperties(productDO,productVO);
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("product_id",product_id);
        wrapper.eq("state",StateEnum.not_del.getIndex());
        List<ProductFieldDO> productFields = productFieldService.selectList(wrapper);
        List<ProductFieldVO> productFieldVOList = new ArrayList<>();
        for (ProductFieldDO productField : productFields){
            ProductFieldVO productFieldVO = new ProductFieldVO();
            AttrFieldDO attrFieldDO = attrFieldService.selectById(productField.getAttrFieldId());
            BeanUtils.copyProperties(productField,productFieldVO);
            productFieldVO.setType(attrFieldDO.getType());
            productFieldVO.setAttrFieldName(attrFieldDO.getAttrFieldName());
            productFieldVOList.add(productFieldVO);
        }

        productVO.setProductFieldVOS(productFieldVOList);
        return productVO;
    }

    @Override
    public boolean save(ProductFileVO product) {
        FileDO fileDO = null;
        try {
            fileDO = fileService.upload(product.getCreatedBy(),product.getFile().getBytes(),product.getFile().getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int count = selectCount(new EntityWrapper<Product>() {{
            eq("product_name", product.getProductName());
            eq("status", Product.STATUS_ENABLE);
            eq("company_id",product.getCompanyId());
        }});

        if (count > 0) {
            throw new MessageException(String.format("%s 已经存在，请勿重复添加", product.getProductName()));
        }

        product.setProductImg(fileDO.getUrl());

        product.setStatus(Product.STATUS_ENABLE);
        product.setCreatedDate(new Date());
        return insert(product);
    }

    @Override
    public boolean modify(ProductFileVO product) {
//        Product prod = selectById(product.getProductId());

        product.setStatus(Product.STATUS_ENABLE);
        MultipartFile file = product.getFile();
        FileDO fileDO = null;
        if (!file.isEmpty()){
            try {
                fileDO = fileService.upload(product.getCreatedBy(),file.getBytes(),file.getOriginalFilename());
                product.setProductImg(fileDO.getUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//        product.setProductName(product.getProductName());
        return updateById(product);
    }

    @Override
    public boolean remove(long id) {
        Product product = selectById(id);
        product.setStatus(Product.STATUS_DELETE);
        return updateById(product);
    }

    @Override
    public boolean batchRemove(Long... ids) {

        if (ids == null || ids.length < 1) {
            throw new MessageException("请选择要删除的记录");
        }
        List<Product> products = selectList(new EntityWrapper<Product>() {{
            in("product_id", Arrays.asList(ids));
        }});

        if (products == null || products.isEmpty()) {
            throw new MessageException("你要删除的记录不存在");
        }

        products.forEach(p -> {
            p.setStatus(Product.STATUS_DELETE);
        });
        return updateBatchById(products);
    }

    @Override
    public HSSFWorkbook exportProduct(Long companyId) {
//        String[] title = {"产品名称","产品描述","产品包装数量","产品规格","生产许可","生产基地","保质期","产品图片","创建时间"};
        String[] title = {"产品名称","产品描述","产品包装数量","产品规格","溯源模版","产品图片","创建时间"};
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("产品记录模板");
        //合并表标题
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));

//        sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));//第一行第一列开始，跨8列
        HSSFRow titleRow = sheet.createRow(0);//第0行

        //标题样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short)16);
        titleStyle.setFont(font);

        //生成标题
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("产品模板（红色标识列为必填）");
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
            if (i == title.length-1||i == title.length-2){
                cell = headRow.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(defaultStyle);

            }else{
                cell = headRow.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(redStyle);
            }
        }

        return wb;
    }

    @Override
    public void importProduct(MultipartFile file, Long companyId, Long userId) {
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

                Product product =new Product();
                product.setCreatedDate(Calendar.getInstance().getTime());
                product.setCompanyId(companyId);
                product.setCreatedBy(userId);
                product.setStatus(StateEnum.usable.getIndex());
                Cell proNameCell = row.getCell(0);
                if (CellUtil.isCellEmpty(proNameCell)){
                    throw new MessageException("名称不能为空");
                }
                product.setProductName(proNameCell.getStringCellValue());


                Cell describeCell = row.getCell(1);
                if (CellUtil.isCellEmpty(describeCell)){
                    throw new MessageException("描述不能为空");
                }
                product.setDescribe(describeCell.getStringCellValue());

                Cell boxSizeCell = row.getCell(2);
                if (CellUtil.isCellEmpty(boxSizeCell)){
                    throw new MessageException("产品包装数量不能为空");
                }
                try {
                    product.setBoxSize(Integer.parseInt(boxSizeCell.getStringCellValue()));
                }catch (Exception e){
                    throw new MessageException("产品包装数量必须为数字类型");
                }


                Cell productSpecCell = row.getCell(3);
                if (CellUtil.isCellEmpty(productSpecCell)){
                    throw new MessageException("产品规格不能为空");
                }
                product.setProductSpec(productSpecCell.getStringCellValue());

//                Cell permitCell = row.getCell(4);
//                if (CellUtil.isCellEmpty(permitCell)){
//                    throw new MessageException("生产许可不能为空");
//                }
//                product.setPermit(permitCell.getStringCellValue());
//
//                Cell productionBaseCell = row.getCell(5);
//                if (CellUtil.isCellEmpty(productionBaseCell)){
//                    throw new MessageException("生产基地不能为空");
//                }
//                product.setProductionBase(productionBaseCell.getStringCellValue());
//
//                Cell warrantyCell = row.getCell(6);
//                if (CellUtil.isCellEmpty(warrantyCell)){
//                    throw new MessageException("保质期不能为空");
//                }
//                product.setWarranty(warrantyCell.getStringCellValue());
                insert(product);

            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public Boolean addProduct(ProductFileVO productVO) {
        Product productDO = new Product();
        BeanUtils.copyProperties(productVO,productDO);
        if (productVO.getFile()!=null && !productVO.getFile().isEmpty()){
            try {
                FileDO upload = fileService.upload(productVO.getCreatedBy(), productVO.getFile().getBytes(), productVO.getFile().getOriginalFilename());
                String url = upload.getUrl();
                productDO.setProductImg(url);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new IFastException("图片上传异常");
            }
        }

        productDO.setStatus(1);
        Boolean result = insert(productDO);

        List<ProductFieldDO> productFieldDOList = productVO.getProductFieldDOList();
        if (!CollectionUtils.isEmpty(productFieldDOList)){
            for (ProductFieldDO productFieldDO:productFieldDOList) {
                productFieldDO.setState(StateEnum.not_del.getIndex());
                productFieldDO.setProductId((int) productDO.getProductId());
                productFieldService.insert(productFieldDO);
            }
        }


        return result;
    }

    @Override
    public ProductFileVO  getProductById(Long id) {
        Product productDO = selectById(id);
        ProductFileVO productVO = new ProductFileVO();
        BeanUtils.copyProperties(productDO,productVO);
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("product_id",id);
        wrapper.eq("state",StateEnum.not_del.getIndex());
        List<ProductFieldDO> productFields = productFieldService.selectList(wrapper);
        List<ProductFieldVO> productFieldVOList = new ArrayList<>();
        for (ProductFieldDO productField : productFields){
            ProductFieldVO productFieldVO = new ProductFieldVO();
            AttrFieldDO attrFieldDO = attrFieldService.selectById(productField.getAttrFieldId());
            BeanUtils.copyProperties(productField,productFieldVO);
            productFieldVO.setType(attrFieldDO.getType());
            productFieldVO.setAttrFieldName(attrFieldDO.getAttrFieldName());
            productFieldVOList.add(productFieldVO);
        }

        productVO.setProductFieldVOS(productFieldVOList);
        return productVO;
    }

    /**
     * 更新产品信息
     * @param productVO
     * @return
     */
    @Transactional
    @Override
    public Boolean updateProduct(ProductFileVO productVO) {
        Product productDO = new Product();
        BeanUtils.copyProperties(productVO,productDO);
        if (productVO.getFile()!=null && !productVO.getFile().isEmpty()){
            try {
                FileDO upload = fileService.upload(productVO.getCreatedBy(), productVO.getFile().getBytes(), productVO.getFile().getOriginalFilename());
                String url = upload.getUrl();
                productDO.setProductImg(url);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new IFastException("图片上传异常");
            }
        }
        Boolean result = updateById(productDO);

        List<ProductFieldDO> productFieldDOList = productVO.getProductFieldDOList();

        Boolean result2 = true;
        if (!CollectionUtils.isEmpty(productFieldDOList)){
            Wrapper wrapper = new EntityWrapper();
            wrapper.eq("product_id",productDO.getProductId());
            ProductFieldDO oldProductFieldDO = new ProductFieldDO();
            oldProductFieldDO.setState(StateEnum.del.getIndex());
            productFieldService.update(oldProductFieldDO,wrapper);

            for (ProductFieldDO productFieldDO:productFieldDOList) {
                productFieldDO.setState(StateEnum.not_del.getIndex());
                productFieldDO.setProductId((int)productDO.getProductId());
                result2 = productFieldService.insert(productFieldDO);
            }
        }

        return result && result2;
    }

}


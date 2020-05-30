package com.tianma315.web.product.rest;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.product.domain.dto.ProductDto;
import com.tianma315.core.product.domain.pojo.Product;

import com.tianma315.core.product.domain.vo.ProductVo;
import com.tianma315.core.product.service.ProductService;
import com.tianma315.core.product.vo.ProductFileVO;
import com.tianma315.core.utils.ResponseUtil;
import com.tianma315.core.base.Result;
import com.tianma315.web.base.BaseController;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductRestController extends BaseController {

    @Autowired
    private ProductService productService;

    /**
     * 获取所有产品
     * @param product
     * @return
     */
    @GetMapping("/all")
    @ResponseBody
    Result all( Product product) {
        product.setCompanyId(getCompanyId());
       List<Product> list = productService.getAll(product);
        if (list != null)
            return Result.ok(list);

        return Result.fail();
    }


    /**
     * 产品列表
     *
     * @param pageNumber
     * @param pageSize
     * @param product
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    Result list(int pageNumber, int pageSize, ProductDto product) {
        //System.out.println("sortName=============="+product.getSortName());
        product.setCompanyId(getCompanyId());
        Page<ProductVo> page = productService.getPage(pageNumber, pageSize, product);
        if (page != null)
            return Result.ok(page);

        return Result.fail();
    }


    /**
     * @param productFileVO
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    Result add( ProductFileVO productFileVO) {
//        productFileVO.setCreatedBy(getUserId());
//        productFileVO.setCompanyId(getCompanyId());
//        if (productService.save(productFileVO)) {
//            return Result.ok();
//        }
        productFileVO.setCreatedDate(Calendar.getInstance().getTime());
        productFileVO.setCompanyId(getCompanyId());
        productFileVO.setCreatedBy(getUserId());
        if(productService.addProduct(productFileVO)){
            return Result.ok();
        }

        return Result.fail();
    }

    /**
     * @param productFileVO
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    Result modify(@Validated ProductFileVO productFileVO) {
        productFileVO.setCreatedBy(getUserId());
        productFileVO.setStatus(1);

        if(productService.updateProduct(productFileVO)){
            return Result.ok();
        }
        return Result.fail();
    }


    /**
     * @param productId
     * @return
     */
    @PostMapping("/remove")
    @ResponseBody
    Result remove(long productId) {
        if (productService.remove(productId)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/remove/batch")
    @ResponseBody
    Result batchRemove(Long... ids) {

        if (productService.batchRemove(ids)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @GetMapping( "/exportProduct")
    @ResponseBody
    public void exportMaterial(HttpServletResponse response){
        String fileName = "产品导入模板.xls";
        try {
            response = ResponseUtil.getResponse(response,fileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HSSFWorkbook wb =productService.exportProduct(getCompanyId());
        try(OutputStream os = response.getOutputStream()) {
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 进入导入excel
     */
    @GetMapping( "/productImport")
    public String productImport(MultipartFile file){
        return "product/productImport";
    }

    /**
     * 导入excel
     */
    @ResponseBody
    @PostMapping( "/importProduct")
    public Result importMaterial(MultipartFile file){
        if (!file.isEmpty()){
            productService.importProduct(file,getCompanyId(),getUserId());
        }
        return Result.ok();
    }
}

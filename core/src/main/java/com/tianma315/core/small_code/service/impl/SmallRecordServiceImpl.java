package com.tianma315.core.small_code.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.product.dao.ProductMapper;
import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.small_code.dao.SmallRecordDao;
import com.tianma315.core.small_code.domain.SmallRecordDO;
import com.tianma315.core.small_code.domain.dto.SmallRecordDto;
import com.tianma315.core.small_code.service.SmallRecordService;
import com.tianma315.core.small_code.vo.SmallRecordListVO;
import com.tianma315.core.small_code.vo.SmallRecordVO;
import com.tianma315.core.stock.domain.StockDO;
import com.tianma315.core.stock.service.StockService;
import com.tianma315.core.warehouse.dao.WarehouseManagerDao;
import com.tianma315.core.warehouse.domain.WarehouseRecordCodeDO;
import com.tianma315.core.warehouse.domain.WarehouseRecordDO;
import com.tianma315.core.warehouse.domain.vo.WarehouseManagerVo;
import com.tianma315.core.warehouse.service.WarehouseRecordCodeService;
import com.tianma315.core.warehouse.service.WarehouseRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 
 * <pre>
 * 外包装管理记录
 * </pre>
 * <small> 2019-08-10 10:06:48 | Wen</small>
 */
@Service
public class SmallRecordServiceImpl extends CoreServiceImpl<SmallRecordDao, SmallRecordDO> implements SmallRecordService {

    @Autowired
    private WarehouseRecordService warehouseRecordService;

    @Autowired
    private StockService stockService;

    @Autowired
    private WarehouseRecordCodeService warehouseRecordCodeService;

    @Autowired
    private SmallRecordDao smallRecordDao;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private WarehouseManagerDao warehouseManagerDao;

    @Override
    public Boolean addSmallRecord(SmallRecordVO smallRecordVO) {
        smallRecordVO.setCreateDate(Calendar.getInstance().getTime());
        List<String> codes = smallRecordVO.getSmallCodes();
        Boolean check = checkBigRecord(smallRecordVO.getSmallCode(),smallRecordVO.getCompanyId());
        Boolean codeCheck = checkCode(smallRecordVO.getSmallCodes(),smallRecordVO.getCompanyId());
        if (check && codeCheck){
            for (int i = 0; i < codes.size(); i++) {
                String code =codes.get(i);
                SmallRecordDO smallRecordDO = new SmallRecordDO();

                BeanUtils.copyProperties(smallRecordVO,smallRecordDO);
                smallRecordDO.setSmallCode(code);
                    insert(smallRecordDO);
                }
        }else{
            throw new MessageException("外箱码或者产品码不能重复");
        }
        return true;
    }

    @Transactional
    @Override
    public Boolean addSmallRecordAndStock(SmallRecordVO smallRecordVO) {
        smallRecordVO.setCreateDate(Calendar.getInstance().getTime());
        List<String> codes = smallRecordVO.getSmallCodes();
        Boolean check = checkBigRecord(smallRecordVO.getBigCode(),smallRecordVO.getCompanyId());
        Boolean codeCheck = checkCode(smallRecordVO.getSmallCodes(),smallRecordVO.getCompanyId());
        String smallCode = smallRecordVO.getSmallCode();
        Integer productId = smallRecordVO.getProductId();
        Long warehouseId = smallRecordVO.getWarehouseId();

        //查询产品
        Product product = productMapper.selectById(productId);

        //入库记录
        WarehouseRecordDO warehouseRecordDO = new WarehouseRecordDO();

        warehouseRecordDO.setProductId(productId);
        warehouseRecordDO.setWarehouseId(warehouseId);
        warehouseRecordDO.setNumber(1);
        warehouseRecordDO.setType(1);
        warehouseRecordDO.setCreateBy(smallRecordVO.getCreateBy());
        warehouseRecordDO.setCreateDate(Calendar.getInstance().getTime());
        warehouseRecordDO.setCompanyId(smallRecordVO.getCompanyId());
        warehouseRecordService.insert(warehouseRecordDO);



        if (check && codeCheck){
            for (int i = 0; i < codes.size(); i++) {
                String code =codes.get(i);
                SmallRecordDO smallRecordDO = new SmallRecordDO();
                BeanUtils.copyProperties(smallRecordVO,smallRecordDO);
                smallRecordDO.setSmallCode(code);
                insert(smallRecordDO);

                //添加入库记录关联表
                WarehouseRecordCodeDO warehouseRecordCodeDO = new WarehouseRecordCodeDO();
                warehouseRecordCodeDO.setBigCode(smallRecordVO.getBigCode());
                warehouseRecordCodeDO.setSmallCode(code);
                warehouseRecordCodeDO.setWarehouseRecordId(warehouseRecordDO.getId());
                warehouseRecordCodeService.insert(warehouseRecordCodeDO);
                //添加库存
//                StockDO stockDO = new StockDO();
//                stockDO.setCompanyId(smallRecordVO.getCompanyId());
//                stockDO.setBigNumber(1);
////                stockDO.setSmallNumber(Integer.parseInt(code));
//                stockDO.setProductId(smallRecordVO.getProductId().longValue());
//                stockDO.setWarehouseId(smallRecordVO.getWarehouseId());
//                stockService.insert(stockDO);
            }
        }else{
            throw new MessageException("外箱码或者产品码不能重复");
        }

        //添加库存
        EntityWrapper<StockDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("product_id",productId);
        entityWrapper.eq("company_id",smallRecordVO.getCompanyId());
        entityWrapper.eq("warehouse_id",smallRecordVO.getWarehouseId());
        StockDO stockDO = stockService.selectOne(entityWrapper);

        if (stockDO == null){
            stockDO = new StockDO();
            stockDO.setCompanyId(smallRecordVO.getCompanyId());
            stockDO.setProductId(productId.longValue());
            stockDO.setStockNumber(product.getBoxSize());
            //stockDO.setSmallNumber(smallRecordVO.getSmallCodes().size());
            stockDO.setWarehouseId(warehouseId);
            stockService.insert(stockDO);
        }else{
            stockDO.setStockNumber(stockDO.getStockNumber()+product.getBoxSize());
            //stockDO.setSmallNumber(stockDO.getSmallNumber()+smallRecordVO.getSmallCodes().size());
            stockService.updateById(stockDO);
        }
        return true;
    }

    @Override
    public SmallRecordListVO getSmallRecordListVOBySmallCode(String code, Long companyId) {
        EntityWrapper codeWrapper = new EntityWrapper();
        codeWrapper.eq("small_code",code);
        codeWrapper.eq("company_id",companyId);
        SmallRecordListVO smallRecordListVO = new SmallRecordListVO();
        SmallRecordDO smallRecordDO = selectOne(codeWrapper);
        if (smallRecordDO != null){
            smallRecordListVO = getSmallRecordListVOBySmallCode(smallRecordDO.getSmallCode(),companyId);
        }

        return smallRecordListVO;
    }

    @Override
    public SmallRecordListVO getSmallRecordListVOByBigCode(String bigCode, Long companyId) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("big_code",bigCode);
        entityWrapper.eq("company_id",companyId);
        SmallRecordListVO smallRecordListVO = new SmallRecordListVO();
        List<SmallRecordDO> smallRecordDOS = selectList(entityWrapper);
        if (CollectionUtils.isNotEmpty(smallRecordDOS)){
            List<String> code = smallRecordDOS.stream().map(item -> item.getSmallCode()).collect(Collectors.toList());
            smallRecordListVO.setSmallCodes(code);
            smallRecordListVO.setBigCode(bigCode);
        }

        return smallRecordListVO;
    }

    @Override
    public Page<SmallRecordVO> getRecordVOPage(Integer pageNumber, Integer pageSize, SmallRecordDto smallRecordDTO) {
       smallRecordDTO.setSortName(smallRecordDTO.getSortName());
        Page<SmallRecordVO> page = new Page(pageNumber,pageSize,smallRecordDTO.getSortName());
        if (smallRecordDTO.getSortOrder().equals("desc")){
            page.setAsc(false);
        }
        List<SmallRecordVO> smallRecordVOList = smallRecordDao.selectRecordPage(page,smallRecordDTO);
        page.setRecords(smallRecordVOList);
        return page;
    }

    @Transactional
    @Override
    public Boolean inSmallRecord(SmallRecordVO smallRecordVO) {
        //查询产品
        Product product = productMapper.selectById(smallRecordVO.getProductId());
        //type2:小码 1是大码
        Integer type = smallRecordVO.getType();
        WarehouseManagerVo warehouseManagerVo = warehouseManagerDao.selectVoByUserId(smallRecordVO.getCreateBy());
        //入库记录入库
        WarehouseRecordDO warehouseRecordDO = new WarehouseRecordDO();
        BeanUtils.copyProperties(smallRecordVO,warehouseRecordDO);
        warehouseRecordDO.setCreateDate(new Date());
        warehouseRecordDO.setNumber(smallRecordVO.getSmallCodes().size());
        warehouseRecordDO.setWarehouseId(warehouseManagerVo.getWarehouseId());
        warehouseRecordService.insert(warehouseRecordDO);

        for (int i = 0; i<smallRecordVO.getSmallCodes().size();i++){
          //查询是否重复
            WarehouseRecordCodeDO warehouseRecordCodeDOByCode = warehouseRecordCodeService.getWarehouseRecordCodeDOByCode(smallRecordVO.getSmallCodes().get(i));
            if (warehouseRecordCodeDOByCode != null)   throw new MessageException(String.format("%s 已经完成入库,请勿重复操作", smallRecordVO.getSmallCodes().get(i)));
            WarehouseRecordCodeDO warehouseRecordCodeDO = new WarehouseRecordCodeDO();
            warehouseRecordCodeDO.setWarehouseRecordId(warehouseRecordDO.getId());
            if (type == 2){
                warehouseRecordCodeDO.setSmallCode(smallRecordVO.getSmallCodes().get(i));
            }else {
                warehouseRecordCodeDO.setBigCode(smallRecordVO.getSmallCodes().get(i));
            }
            warehouseRecordCodeService.insert(warehouseRecordCodeDO);
        }
        EntityWrapper<StockDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("product_id",smallRecordVO.getProductId());
        entityWrapper.eq("company_id",smallRecordVO.getCompanyId());
        entityWrapper.eq("warehouse_id",warehouseManagerVo.getWarehouseId());
        StockDO stockDO = stockService.selectOne(entityWrapper);
        int cont;
        if (type==2){
            cont = smallRecordVO.getSmallCodes().size();
        }else {
            cont = product.getBoxSize() * smallRecordVO.getSmallCodes().size();
        }
        if (stockDO == null){
            stockDO = new StockDO();
            stockDO.setCompanyId(smallRecordVO.getCompanyId());
            stockDO.setProductId(smallRecordVO.getProductId().longValue());
            stockDO.setStockNumber(cont);
            stockDO.setWarehouseId(warehouseManagerVo.getWarehouseId());
            stockService.insert(stockDO);
        }else{
            stockDO.setStockNumber(stockDO.getStockNumber()+cont);
            //stockDO.setSmallNumber(stockDO.getSmallNumber()+smallRecordVO.getSmallCodes().size());
            stockService.updateById(stockDO);
        }
        return true;
    }

    public Boolean checkBigRecord(String bigCode,Long companyId){
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("big_code",bigCode);
        wrapper.eq("company_id",companyId);
        List<SmallRecordDO> smallRecordDOS = selectList(wrapper);
        Boolean result = false;
        if (CollectionUtils.isEmpty(smallRecordDOS)){
            result = true;
        }
        return result;
    }

    public Boolean checkCode(List<String> smallCode,Long companyId){
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.in("small_code",smallCode);
        wrapper.eq("company_id",companyId);
        List<SmallRecordDO> smallRecordDOList = selectList(wrapper);
        Boolean result = false;
        if (CollectionUtils.isEmpty(smallRecordDOList)){
            result = true;
        }
        return result;
    }
}

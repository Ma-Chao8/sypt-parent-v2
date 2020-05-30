package com.tianma315.core.invoice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.model.DataValues;
import com.tianma315.core.agent.dao.AgentDao;
import com.tianma315.core.agent.domain.AgentAddressDO;
import com.tianma315.core.agent.domain.AgentDO;
import com.tianma315.core.agent.service.AgentAddressService;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.company.dao.CompanyConfigDao;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.invoice.dao.*;
import com.tianma315.core.invoice.domain.*;
import com.tianma315.core.invoice.domain.dto.InvoiceDto;
import com.tianma315.core.invoice.domain.vo.*;
import com.tianma315.core.invoice.service.InvoiceService;
import com.tianma315.core.invoice.vo.DeliverForm;
import com.tianma315.core.invoice.vo.InvoiceDateVO;
import com.tianma315.core.invoice.vo.ProductVo;
import com.tianma315.core.invoice.vo.ScanCodeVo;
import com.tianma315.core.product.dao.ProductMapper;
import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.small_code.dao.SmallRecordDao;
import com.tianma315.core.small_code.domain.SmallRecordDO;
import com.tianma315.core.stock.dao.StockDao;
import com.tianma315.core.stock.domain.StockDO;
import com.tianma315.core.utils.BeanHump;
import com.tianma315.core.utils.DateUtil;
import com.tianma315.core.utils.InvoiceNumberUtils;
import com.tianma315.core.warehouse.dao.WarehouseManagerDao;
import com.tianma315.core.warehouse.domain.vo.WarehouseManagerVo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <pre>
 * 货单
 * </pre>
 * <small> 2019-08-21 14:56:27 | Lgc</small>
 */
@Service
public class InvoiceServiceImpl extends CoreServiceImpl<InvoiceDao, InvoiceDO> implements InvoiceService {
    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    SmallRecordDao smallRecordDao;
    @Autowired
    ReturnedProductCodeDao returnedProductCodeDao;
    @Autowired
    InvoiceProductCodeDao invoiceProductCodeDao;
    @Autowired
    private ReturnedDao returnedDao;
    @Autowired
    private ReturnedProductDao returnedProductDao;
    @Autowired
    private AgentDao agentDao;
    @Autowired
    private AgentAddressService agentAddressService;
    @Autowired
    private InvoiceProductDao invoiceProductDao;
    @Autowired
    WarehouseManagerDao warehouseManagerDao;
//    @Autowired
//    TraceFileMapper traceFileMapper;
    @Autowired
    CompanyConfigDao companyConfigDao;
    @Autowired
    StockDao stockDao;
    @Autowired
    ProductMapper productMapper;

    @Override
    public Page<InvoiceVO> selectIncoicelist(Integer pageNumber, Integer pageSize, InvoiceDto invoiceDTO) {
        invoiceDTO.setSortName(BeanHump.camelToUnderline(invoiceDTO.getSortName()));
        //System.out.println("SortName+++++++++++========"+invoiceDTO.getSortName());
        Page<InvoiceVO> page = new Page<>(pageNumber, pageSize);
        List<InvoiceVO> list = invoiceDao.selectPages(page, invoiceDTO);
        page.setRecords(list);
        return page;
    }

    @Override
    public InspectVo getInspect(Long companyId, String code, String productIdent) {
        if (StringUtils.isBlank(code)) {
            throw new MessageException("稽查码不能为空");
        }

        PackCode packCode = getPackCode(companyId, code, productIdent);
        if (packCode == null) {
            throw new MessageException("您查询的码不是系统的有效码，谨防假冒");
        }
//        System.out.println("########packCode======================="+packCode);
        //判断当前码是否出库
        String invoiceCode = code;//稽查码
        InvoiceProductCodeVO invoiceProductCodeVo = null;

        //1、大码发货  大码退货
        if (getCodeType(packCode, code) == ScanCodeVO.TYPE_BIG_CODE) {
            invoiceProductCodeVo = getLastInvoice(companyId, invoiceCode);
            if (invoiceProductCodeVo != null) {
                //大码查找到了出库记录
                if (hasReturned(companyId, invoiceProductCodeVo, invoiceCode)) {
                    //大码查找到了出库记录和退货，且同属于一个订单
                    throw new MessageException(String.format("%s 未出库", code));
                }
            }
        } else if (getCodeType(packCode, code) == ScanCodeVO.TYPE_SMALL_CODE) {//小码稽查
            //要判定当前码是否出库
            invoiceProductCodeVo = getLastInvoice(companyId, invoiceCode);
            if (invoiceProductCodeVo == null) {
                //小码没有出库记录，只需要判定小码对应的大码是否出库以及大码是否退货即可
                invoiceCode = packCode.getBigCode();
                invoiceProductCodeVo = getLastInvoice(companyId, invoiceCode);
                if (invoiceProductCodeVo != null) {
                    //当前大码已经有出库记录（此时需要判断小码是否退货）
                    if (hasReturned(companyId, invoiceProductCodeVo, invoiceCode)) {
                        //小码找到退货记录，且和大码的出库记录同属于一个订单
                        throw new MessageException(String.format("%s 未出库", code));
                    } else {
                        //大码未退货 判定单个小码是否退货
                        invoiceCode = code;
                        if (hasReturned(companyId, invoiceProductCodeVo, invoiceCode))
                            throw new MessageException(String.format("%s 未出库", code));
                    }
                }
            } else {
                //当前小码已经有出库记录(只有在小码出库的情况下才能查到这个记录)
                //小码有出库记录，还须要查询小码对应的大码是否有出库记录,若二者同时存在，则需要判断出其中的有效记录
                String tempCode = packCode.getBigCode();
                InvoiceProductCodeVO invoiceProductCodeVo1 = getLastInvoice(companyId, tempCode);
                if (invoiceProductCodeVo1 != null) {
                    if (hasReturned(companyId, invoiceProductCodeVo1, tempCode)) {
                        //大码已经退货
                        if (hasReturned(companyId, invoiceProductCodeVo, invoiceCode)) {
                            throw new MessageException(String.format("%s已退货，%s 未出库", code));
                        }
                    } else {
                        //大码未退货
                        if (hasReturned(companyId, invoiceProductCodeVo1, invoiceCode) && hasReturned(companyId, invoiceProductCodeVo, invoiceCode)) {
                            throw new MessageException(String.format("%s 未出库", code));
                        }
                    }
                }
            }
        }

        if (invoiceProductCodeVo == null) {
            throw new MessageException(String.format("%s 未出库", code));
        }

        //查询稽查信息
        InspectVo inspectVo = invoiceDao.selectInspect(StringUtils.isBlank(invoiceProductCodeVo.getBigCode())
                ? invoiceProductCodeVo.getSmallCode()
                : invoiceProductCodeVo.getBigCode());
        if (inspectVo == null) {
            throw new MessageException("您查询的码尚未出库");
        }
        return inspectVo;
    }

    @Transactional
    @Override
    public boolean deliver(DeliverForm form, long user_id, long merchant_id, String product_ident) {

        if (form == null)
            throw new MessageException("发货数据获取失败");

        if (form.getProducts() == null || form.getProducts().isEmpty())
            throw new MessageException("请选择产品");

        //验证提交的码是否允许发货（可能存在重复发货的情况）
        this.allowDelivered(merchant_id, form.getProducts());

        //查询经销商信息
        AgentDO agent = agentDao.selectById(form.getAgentId());
        if (agent == null)
            throw new MessageException("无法获取经销商信息");

        //获取默认地址
        List<AgentAddressDO> addresses = agentAddressService.getAgentAddressByStatus(form.getAgentId(), 1);
        if (addresses.size() != 1)
            throw new MessageException("经销商尚未设置默认地址");
        //查询仓库id
        WarehouseManagerVo warehouseManagerVo = warehouseManagerDao.selectVoByUserId(user_id);
        if (warehouseManagerVo == null) throw new MessageException("无法发货员仓库信息");
        //查询产品对应的开启溯源档案
        long productId = form.getProducts().get(0).getProductId();
//        TraceFileVo traceFileVo = traceFileMapper.selectByProductId(productId);
//        if (traceFileVo == null) throw new MessageException("该产品没有绑定溯源档案，请到后台设置");

        //查询产品
        Product productById = productMapper.selectById(productId);

        //创建货单
        InvoiceDO invoice = new InvoiceDO();
        invoice.setCompanyId(merchant_id);
        invoice.setInvoiceNumber(InvoiceNumberUtils.generateInvoiceNumber());
        invoice.setAgentId(agent.getAgentId());
        invoice.setLinkman(agent.getLinkman());
        invoice.setTel(agent.getTel());
        Date date = new Date();
        invoice.setCreatedDate(date);
        invoice.setCreateBy(user_id);
        invoice.setDeliverDate(date);
        invoice.setDeliverAddres(addresses.get(0).getAddress());
        invoice.setState(1);
        invoice.setWarehouseId(warehouseManagerVo.getWarehouseId());
//        invoice.setTraceFileId(traceFileVo.getTraceFileId());
        //减少库存
        Map map = new HashMap();
        map.put("productId", productId);
        map.put("warehouseId", warehouseManagerVo.getWarehouseId());
        StockDO stockDO = stockDao.selectByProductId(map);
        int bigNumber = 0;
        int smallNumber = 0;
        if (invoiceDao.insert(invoice) != 1)
            throw new MessageException("货单创建失败");

        for (ProductVo product : form.getProducts()) {
            InvoiceProductDO invoiceProduct = new InvoiceProductDO();
            invoiceProduct.setInvoiceId(invoice.getInvoiceId());
            invoiceProduct.setProductId(product.getProductId());
            //绑定产品
            if (invoiceProductDao.insert(invoiceProduct) != 1)
                throw new MessageException("产品绑定失败");

            for (ScanCodeVo code : product.getCodes()) {
                InvoiceProductCodeDO invoiceProductCode = new InvoiceProductCodeDO();
                invoiceProductCode.setInvoiceProductId(invoiceProduct.getInvoiceProductId());
                invoiceProductCode.setStatus(1);
                String codeStr = "";
                if (code.getType() == ScanCodeVo.TYPE_BIG_CODE) {//大码
                    bigNumber = bigNumber + 1;
                    codeStr = code.getBigCode();
                    invoiceProductCode.setBigCode(codeStr);
                } else {//小码
                    smallNumber = smallNumber + 1;
                    codeStr = code.getSmallCode();
                    invoiceProductCode.setSmallCode(codeStr);
                }
                //添加产品和码对应的记录
                if (invoiceProductCodeDao.insert(invoiceProductCode) != 1)
                    throw new MessageException(codeStr + " 绑码失败");
            }
        }
        if (stockDO != null) {
            //计算减去总库存数量
            int count = bigNumber * productById.getBoxSize() + smallNumber;
            stockDO.setStockNumber(stockDO.getStockNumber() - count);
            stockDao.updateById(stockDO);
        }
        return true;
    }

    @Override
    public Page<DeliverRecordVo> deliveredRecord(int currentPage, int pageSize, Long userId, String searchKey) {
        Page<DeliverRecordVo> page = new Page<>(currentPage, pageSize);
        List<DeliverRecordVo> records = invoiceDao.selectDeliveredRecord(page, userId,searchKey);
        page.setRecords(records);
        return page;
    }

    @Override
    @Transactional
    public boolean returned(List<String> codes, long user_id, long merchant_id, String product_ident) {

        if (codes == null || codes.isEmpty())
            throw new MessageException("退货码不能为空");

        Map<Long, Collection<ProductVo>> invoices = new HashMap<>();
        for (String code : codes) {
            if (StringUtils.isBlank(code)) {
                continue;
            }

            PackCode packCode = getPackCode(merchant_id, code, product_ident);
            if (packCode == null) {
                //套标获取失败,说明该码不是系统的码，无法完成退货
                throw new MessageException(String.format("%s 非系统有效码", code));
            }

            String returned_code = code;
            InvoiceProductCodeVO invoiceProductCodeVo = null;
            if (getCodeType(packCode, code) == ScanCodeVo.TYPE_BIG_CODE) {
                //大码退货需要判断该码是否出库, 如果存在出库记录时还须要判断当前码是否退货
                invoiceProductCodeVo = getLastInvoice(merchant_id, returned_code);
                if (invoiceProductCodeVo != null && hasReturned(merchant_id, invoiceProductCodeVo, returned_code)) {
                    //出库记录和退货记录同属于几个订单 可视为已经退货
                    throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                }
            } else if (getCodeType(packCode, code) == ScanCodeVo.TYPE_SMALL_CODE) {
                //小码退货- 需要判断当前小码是否已经发货（包扩小码发货或者小码对应的大码发货）
                invoiceProductCodeVo = getLastInvoice(merchant_id, returned_code);
                if (invoiceProductCodeVo == null) {

                    //小码查不到发货单  使用大码继续查
                    returned_code = packCode.getBigCode();
                    invoiceProductCodeVo = getLastInvoice(merchant_id, returned_code);

                    if (invoiceProductCodeVo != null) {
                        if (hasReturned(merchant_id, invoiceProductCodeVo, returned_code)) {
                            //大码已退货
                            throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                        } else {
                            //大码未退货，需要判断当前小码是否退货
                            returned_code = code;
                            if (hasReturned(merchant_id, invoiceProductCodeVo, returned_code)) {
                                //该小码对应的大码已经有退货记录，且属于同一个订单
                                throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                            }
                        }
                    }
                } else {
                    //小码直接查到了出库记录，说明当前货单是由小码直接发货的
                    String temp_code = packCode.getBigCode();
                    InvoiceProductCodeVO invoiceProductCodeVo1 = getLastInvoice(merchant_id, temp_code);
                    if (invoiceProductCodeVo1 != null) {
                        //同一套标下的大小码同时存在出库记录，按照系统定义的规则（不能大小码同时出库），必有一个是已经退货的状态
                        //此时需要判定哪一个记录才是有效的

                        if (hasReturned(merchant_id, invoiceProductCodeVo1, temp_code)) {
                            //大码已经退货
                            if (hasReturned(merchant_id, invoiceProductCodeVo, returned_code)) {
                                throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                            }
                        } else {
                            //大码未退货
                            if (hasReturned(merchant_id, invoiceProductCodeVo1, returned_code)) {
                                throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                            }
                            invoiceProductCodeVo = invoiceProductCodeVo1;
                        }
                    } else {
                        //大码不存在出库记录，只需要判断当前小码是否退货即可
                        if (invoiceProductCodeVo != null && hasReturned(merchant_id, invoiceProductCodeVo, returned_code))
                            throw new MessageException(String.format("%s 已经完成退货,请勿重复操作", code));
                    }
                }
            }

            //查不到出库记录
            if (invoiceProductCodeVo == null) {
                throw new MessageException(String.format("%s 尚未出库", code));
            }

            //构建退货数据
            ScanCodeVo scanCodeVo = new ScanCodeVo();
            scanCodeVo.setType(getCodeType(packCode, code));
            if (scanCodeVo.isBig_code()) {
                scanCodeVo.setBigCode(code);
            } else if (scanCodeVo.isSmall_code()) {
                scanCodeVo.setSmallCode(code);
            }
            //
            ProductVo productVo = new ProductVo();
            productVo.setProductId(invoiceProductCodeVo.getProductId());
            if (productVo.getCodes() == null) {
                productVo.setCodes(new ArrayList<>());
            }
            productVo.getCodes().add(scanCodeVo);
            //
            if (invoices.get(invoiceProductCodeVo.getInvoiceId()) == null) {
                invoices.put(invoiceProductCodeVo.getInvoiceId(), new ArrayList<>());
            }
            invoices.get(invoiceProductCodeVo.getInvoiceId()).add(productVo);
        }
        //查询货单信息
        List<InvoiceVO> invoiceVos = invoiceDao.selectByIds(invoices.keySet());

        WarehouseManagerVo warehouseManagerVo = warehouseManagerDao.selectVoByUserId(user_id);
        //遍历货单
        for (InvoiceVO invoiceVo : invoiceVos) {
            //判断订单是否属于当前用户
            if (invoiceVo.getCreateBy() != user_id)
                throw new MessageException(String.format("订单 %s 不属于当前用户", invoiceVo.getInvoiceId()));

            //创建退货记录
            ReturnedDO returned = new ReturnedDO();
            returned.setCompanyId(merchant_id);
            returned.setInvoiceId(invoiceVo.getInvoiceId());
            returned.setCreateDate(new Date());
            returned.setCreateBy(user_id);
            returned.setWarehouseId(warehouseManagerVo.getWarehouseId());
            if (returnedDao.insert(returned) != 1)
                throw new MessageException("创建退货单失败");

            for (ProductVo productVo : invoices.get(invoiceVo.getInvoiceId())) {
                //添加退货产品记录
                int bigNumber = 0;
                int smallNumber = 0;
                ReturnedProductDO returnedProduct = new ReturnedProductDO();
                returnedProduct.setReturnedId(returned.getReturnedId());
                returnedProduct.setProductId(productVo.getProductId());
                if (returnedProductDao.insert(returnedProduct) != 1)
                    throw new MessageException("退货单绑定产品失败");
                for (ScanCodeVo codeVo : productVo.getCodes()) {
                    ReturnedProductCodeDO returnedProductCode = new ReturnedProductCodeDO();
                    returnedProductCode.setReturnedProductId(returnedProduct.getReturnedProductId());
                    returnedProductCode.setBigCode(codeVo.getBigCode());
                    returnedProductCode.setSmallCode(codeVo.getSmallCode());
                    if (returnedProductCodeDao.insert(returnedProductCode) != 1)
                        throw new MessageException("退货单绑码失败");

                    //修改发货码状态
                    String code = codeVo.getBigCode() != null ? codeVo.getBigCode() : codeVo.getSmallCode();
                    InvoiceProductCodeDO invoiceProductCode = invoiceProductDao.selectEnableByCode(merchant_id, invoiceVo.getInvoiceId(), code);
                    if (invoiceProductCode == null) throw new MessageException("已退货或未发货");
                    invoiceProductCode.setStatus(2); //将货单码设置为退货状态
                    if (invoiceProductCodeDao.updateById(invoiceProductCode) != 1) {
                        throw new MessageException("货单码状态修改失败");
                    }
                    if (codeVo.getBigCode() != null) {
                        bigNumber = bigNumber + 1;
                    } else if (codeVo.getSmallCode() != null) {
                        smallNumber = smallNumber + 1;
                    }
                }
                //增加库存
                Map map = new HashMap();
                map.put("productId", productVo.getProductId());
                map.put("warehouseId", warehouseManagerVo.getWarehouseId());
                StockDO stockDO = stockDao.selectByProductId(map);
                if (stockDO != null) {
                    //计算加去总库存数量
                    Product productById = productMapper.selectById(productVo.getProductId());
                    int count = bigNumber * productById.getBoxSize() + smallNumber;
                    stockDO.setStockNumber(stockDO.getStockNumber() + count);
                    stockDao.updateById(stockDO);
                }
            }
        }
        return true;
    }

    @Override
    public JSONObject countByYear(InvoiceDateVO invoiceDateVO) {
        List<DataValues> dataValues = invoiceDao.countByYear(invoiceDateVO);

        List<String> dates = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        List<String> values = new ArrayList(12);
//        if (dataValues.size()<12){
        for (int i = 0; i < dates.size(); i++) {
            String date = dates.get(i);
            String value = "";
            for (int x = 0; x < dataValues.size(); x++) {
                String d = dataValues.get(x).getDate();
                if (date.equals(d)) {
                    value = dataValues.get(x).getValue();
                    break;
                } else {
                    value = "0";
                }
            }
            values.add(value);
        }
//        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("values", values);
        jsonObject.put("dates", dates);
        return jsonObject;
    }

    @Override
    public JSONObject countByMonth(InvoiceDateVO invoiceDateVO) {
        List<DataValues> dataValues = invoiceDao.countByMonth(invoiceDateVO);

        List<String> dates = DateUtil.initMonth(invoiceDateVO.getYear(), invoiceDateVO.getMonth());
        List<String> values = new ArrayList<>(dates.size());
        for (int i = 0; i < dates.size(); i++) {
            String date = dates.get(i);
            String value = "";
            for (int x = 0; x < dataValues.size(); x++) {
                String d = dataValues.get(x).getDate();
                if (date.equals(d)) {
                    value = dataValues.get(x).getValue();
                    break;
                } else {
                    value = "0";
                }
            }
            values.add(value);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("values", values);
        jsonObject.put("dates", dates);
        return jsonObject;
    }

    @Override
    public JSONObject countCodeByYear(InvoiceDateVO invoiceDateVO) {
        List<InvoiceProductCodeValue> list = invoiceProductCodeDao.countCodeByYear(invoiceDateVO);
        List<String> dates = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        List<String> bigValues = new ArrayList(12);
        List<String> smallValues = new ArrayList(12);
//        if (dataValues.size()<12){
        for (int i = 0; i < dates.size(); i++) {
            String date = dates.get(i);
            String bigValue = "";
            String smallValue = "";
            for (int x = 0; x < list.size(); x++) {
                String d = list.get(x).getDate();
                if (date.equals(d)) {
                    bigValue = list.get(x).getBigValue();
                    smallValue = list.get(x).getSmallValue();
                    break;
                } else {
                    bigValue = "0";
                    smallValue = "0";
                }
            }
            bigValues.add(bigValue);
            smallValues.add(smallValue);
        }
//        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bigValues", bigValues);
        jsonObject.put("smallValues", smallValues);
        jsonObject.put("dates", dates);
        return jsonObject;
    }

    @Override
    public JSONObject countCodeByMonth(InvoiceDateVO invoiceDateVO) {
        List<InvoiceProductCodeValue> list = invoiceProductCodeDao.countCodeByMonth(invoiceDateVO);
        List<String> dates = DateUtil.initMonth(invoiceDateVO.getYear(), invoiceDateVO.getMonth());
        List<String> bigValues = new ArrayList(dates.size());
        List<String> smallValues = new ArrayList(dates.size());
//        if (dataValues.size()<12){
        for (int i = 0; i < dates.size(); i++) {
            String date = dates.get(i);
            String bigValue = "";
            String smallValue = "";
            for (int x = 0; x < list.size(); x++) {
                String d = list.get(x).getDate();
                if (date.equals(d)) {
                    bigValue = list.get(x).getBigValue();
                    smallValue = list.get(x).getSmallValue();
                    break;
                } else {
                    bigValue = "0";
                    smallValue = "0";
                }
            }
            bigValues.add(bigValue);
            smallValues.add(smallValue);
        }
//        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bigValues", bigValues);
        jsonObject.put("smallValues", smallValues);
        jsonObject.put("dates", dates);
        return jsonObject;
    }

    @Override
    public HSSFWorkbook exportInvoice(Long invoiceId) {
        InvoiceVO invoiceDetails = getInvoiceDetails(invoiceId);
        String[] title = {"货单号", "联系人", "经销商", "电话", "收货地址", "发货时间", "产品名称"};
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("发货单详情导出");
//        sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));//第一行第一列开始，跨8列
        HSSFRow titleRow = sheet.createRow(0);//第0行
        //标题样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 18);
        titleStyle.setFont(font);
        //生成标题
        HSSFCell titleCell = titleRow.createCell(0);
        //合并表头单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        titleCell.setCellValue("发货单详情");
        titleCell.setCellStyle(titleStyle);

        //设置列宽
//        sheet.setColumnWidth(4,10 * 512);
//        sheet.setColumnWidth(5,20 * 512);
//        sheet.setColumnWidth(6,20 * 512);
//        sheet.setColumnWidth(7,10 * 512);
        //红色字体
//        HSSFCellStyle redStyle = wb.createCellStyle();
//        redStyle.setAlignment(HorizontalAlignment.CENTER);
//        HSSFFont redFont = wb.createFont();
//        redFont.setBold(true);
//        redFont.setColor(Font.COLOR_RED);
//        redStyle.setFont(redFont);

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
        for (int i = 0; i < title.length; i++) {
            cell = headRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(defaultStyle);
        }
        HSSFRow headRow2 = sheet.createRow(2);
        cell = headRow2.createCell(0);
        cell.setCellValue(invoiceDetails.getInvoiceId());

        cell = headRow2.createCell(1);
        cell.setCellValue(invoiceDetails.getLinkman());
        cell = headRow2.createCell(2);
        cell.setCellValue(invoiceDetails.getAgentName());
        cell = headRow2.createCell(3);
        cell.setCellValue(invoiceDetails.getTel());
        cell = headRow2.createCell(4);
        cell.setCellValue(invoiceDetails.getDeliverAddres());
        cell = headRow2.createCell(5);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strdate = format.format(invoiceDetails.getDeliverDate());
        cell.setCellValue(strdate);

        if (invoiceDetails.getProducts().size() != 0) {
            cell = headRow2.createCell(6);
            cell.setCellValue(invoiceDetails.getProducts().get(0).getProductName());
        }

        return wb;
    }


    /**
     * 获取套标
     *
     * @return
     */
    protected PackCode getPackCode(long companyId, String code, String productIdent) {
        List<SmallRecordDO> codes = smallRecordDao.selectByCode(companyId, code);
        if (codes == null || codes.isEmpty()) {
            //从数据库表查询码是否存在
//            codes = smallRecordDao.selectByBigCode(code);
//            if (codes == null || codes.isEmpty()) {
//                codes = codeMapper.selectBySmallCode(code);
//                if (codes == null || codes.isEmpty()) {
//                    //产品标识为空 可视为不支持接口查询套标
//                    if (StringUtils.isBlank(product_ident)) {
//                        return null;
//                    }
//                    //查询当前码是否是系统的码
//                    String url = CodeRestService.URL_PACK_CODE
//                            .replace(CodeRestService.LOGISTICS_CODE, code)
//                            .replace(CodeRestService.PRODUCT_CODE, product_ident);
//
//                    HttpResult<PackCode> result = CodeRestService
//                            .getInstance()
//                            .get(url, null, new ParameterizedTypeReference<HttpResult<PackCode>>() {
//                            });
//
//                    if (result.isSuccess()) {
//                        return result.getDetails();
//                    }
            return null;
//                }
//            }
        }

        //通过大码查询当前码的套标数据
        String bigCode = codes.get(0).getBigCode();
        if (!StringUtils.isBlank(bigCode))
            codes = smallRecordDao.selectByCode(companyId, bigCode);

//        if (codes == null || codes.isEmpty()) {
//            if (!StringUtils.isBlank(bigCode))
//                codes = codeMapper.selectByCode(bigCode);
//        }
        //构建套标数据结构
        return buildPackCode(codes, 0);
    }

    private PackCode buildPackCode(List<SmallRecordDO> codes, int index) {
        if (index == codes.size()) {
            return new PackCode();
        }

        PackCode packCode = buildPackCode(codes, index + 1);
        SmallRecordDO code = codes.get(index);

        if (StringUtils.isBlank(packCode.getBigCode())) {
            packCode.setBigCode(code.getBigCode());
        }
        if (packCode.getBigCode().equals(code.getBigCode())) {
            if (packCode.getSmallCodes() == null) {
                packCode.setSmallCodes(new ArrayList<>());
            }
            packCode.getSmallCodes().add(code.getSmallCode());
        }
        return packCode;
    }


    /**
     * 获取码类型
     *
     * @param packCode
     * @param code
     * @return
     */
    private int getCodeType(PackCode packCode, String code) {

        if (packCode == null || StringUtils.isBlank(code))
            return -1;

        if (code.equals(packCode.getBigCode())) {
            return ScanCodeVO.TYPE_BIG_CODE;
        } else if (packCode.getSmallCodes().contains(code)) {
            return ScanCodeVO.TYPE_SMALL_CODE;
        }
        return -1;
    }

    /**
     * 判断当前码是否已经退货
     *
     * @param companyId
     * @param invoice
     * @param code
     * @return
     */
    private boolean hasReturned(long companyId, InvoiceProductCodeVO invoice, String code) {
        if (invoice == null || StringUtils.isBlank(code))
            return false;
        ReturnedProductCodeVO returnedProductCodeVo = getLastReturned(companyId, invoice.getInvoiceId(), code);
        return returnedProductCodeVo != null;
    }

    /**
     * 最新退货的订单
     *
     * @param companyId
     * @param code
     * @return
     */
    private ReturnedProductCodeVO getLastReturned(long companyId, long invoiceId, String code) {
        return returnedProductCodeDao.selectLastReturnedByCode(companyId, invoiceId, code);
    }

    /**
     * 最新发货的订单
     *
     * @param companyId
     * @param code
     * @return
     */
    private InvoiceProductCodeVO getLastInvoice(long companyId, String code) {
        return invoiceProductCodeDao.selectLastDeliverByCode(companyId, code);
    }

    @Override
    public InvoiceVO getInvoiceDetails(Long invoiceId) {
        return invoiceDao.selectDetail(invoiceId);
    }


    /**
     * 判断当前码是否允许出库
     *
     * @param companyId
     * @param products
     */
    private void allowDelivered(long companyId, List<ProductVo> products) {
        List<String> hasInvoicedCode = new ArrayList<>(); //保存已经发货（出库）的码
        List<String> bigCode = new ArrayList<>();
        List<String> smallCode = new ArrayList<>();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("bigCode", bigCode);
        map.put("smallCode", smallCode);
        //分离大小码
        for (ProductVo product : products) {
            for (ScanCodeVo code : product.getCodes()) {
                if (code.getType() == 1) {
                    bigCode.add(code.getBigCode());
                } else {
                    smallCode.add(code.getSmallCode());
                }
            }
        }
        //1,无小码不需要查套标，直接查询是否已发货
        //2,有小码则需要查询套标
        if (smallCode.isEmpty()) {
            //无小码全大码

            hasInvoicedCode = invoiceDao.selectByBigCode(map);

        } else {
            //有小码
            //有大码则查大码
            if (!bigCode.isEmpty()) hasInvoicedCode = invoiceDao.selectByBigCode(map);
            //查询小码
            List<String> bySmallCode = invoiceDao.selectBySmallCode(map);
            hasInvoicedCode.addAll(bySmallCode);
        }

        if (!hasInvoicedCode.isEmpty()) {
            throw new MessageException(String.format("%s 物流码已出库", hasInvoicedCode), hasInvoicedCode);
        }
    }


    /**
     * @param merchant_id
     * @param product_ident
     * @param codes
     * @return
     */
    private List<PackCode> allPackCodes(long merchant_id, String product_ident, List<ScanCodeVo> codes) {
        List<String> invalidCodes = new ArrayList<>();
        List<PackCode> packCodeList = new ArrayList<>();
        String codeTemp = null;
        if (codes != null) {
            for (ScanCodeVo code : codes) {
                PackCode packCode = null;
                if (code.getType() == ScanCodeVo.TYPE_BIG_CODE) {
                    codeTemp = code.getBigCode();
                } else if (code.getType() == ScanCodeVo.TYPE_SMALL_CODE) {
                    codeTemp = code.getSmallCode();
                }

                packCode = getPackCode(merchant_id, codeTemp, product_ident);

                if (packCode == null) {
                    invalidCodes.add(codeTemp);
                } else {
                    packCodeList.add(packCode);
                }
            }
        }

        if (invalidCodes != null && !invalidCodes.isEmpty()) {
            throw new MessageException(String.format("%s 不是系统有效码", invalidCodes), invalidCodes);
        }
        return packCodeList;
    }

    /**
     * 筛选符合条件的套标
     *
     * @param packCodes
     * @param codeVo
     * @return
     */
    private PackCode filterPackCode(List<PackCode> packCodes, ScanCodeVo codeVo) {
        String codeStr = null;
        if (codeVo.getType() == ScanCodeVo.TYPE_BIG_CODE) {
            codeStr = codeVo.getBigCode();
            for (PackCode code : packCodes) {
                if (code.getBigCode().equals(codeVo.getBigCode())) {
                    return code;
                }
            }
        } else if (codeVo.getType() == ScanCodeVo.TYPE_SMALL_CODE) {
            codeStr = codeVo.getSmallCode();
            for (PackCode code : packCodes) {
                if (code.getSmallCodes() != null && code.getSmallCodes().contains(codeVo.getSmallCode())) {
                    return code;
                }
            }
        }
        throw new MessageException(String.format("%s 套标获取失败", codeStr));
    }

    /**
     * @param packCodes
     * @param code
     * @return
     */
    private PackCode filterPackCodeByCode(List<PackCode> packCodes, String code) {
        for (PackCode packCode : packCodes) {
            if (packCode.getBigCode().equals(code) || packCode.getSmallCodes().contains(code)) {
                return packCode;
            }
        }
        throw new MessageException(String.format("%s 套标获取失败", code));
    }


}

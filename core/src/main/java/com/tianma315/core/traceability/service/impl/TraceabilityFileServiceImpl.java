package com.tianma315.core.traceability.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.CodeRestService;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.coderecord.dao.ScanRecordMapper;
import com.tianma315.core.coderecord.domain.CodeRecordDO;
import com.tianma315.core.coderecord.domain.pojo.Code;
import com.tianma315.core.coderecord.domain.pojo.ScanRecord;
import com.tianma315.core.coderecord.service.CodeRecordService;
import com.tianma315.core.company.domain.pojo.Company;
import com.tianma315.core.company.domain.pojo.DemoDO;
import com.tianma315.core.company.service.CompanyService;
import com.tianma315.core.company.service.DemoService;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.exception.MessageViewException;
import com.tianma315.core.invoice.domain.InvoiceDO;
import com.tianma315.core.procedures.domain.*;
import com.tianma315.core.procedures.service.*;
import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.product.service.ProductService;
import com.tianma315.core.product.vo.ProductFileVO;
import com.tianma315.core.small_code.domain.SmallRecordDO;
import com.tianma315.core.small_code.service.CodeService;
import com.tianma315.core.sys.service.ConfigService;
import com.tianma315.core.trace.dao.TraceOutDao;
import com.tianma315.core.trace.domain.TraceOutDO;
import com.tianma315.core.trace.vo.InputDo;
import com.tianma315.core.traceability.domain.TyMenuDO;
import com.tianma315.core.traceability.domain.TyMenuDataDO;
import com.tianma315.core.traceability.service.TyMenuDataService;
import com.tianma315.core.traceability.service.TyMenuService;
import com.tianma315.core.traceability.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianma315.core.traceability.dao.TraceabilityFileDao;
import com.tianma315.core.traceability.domain.TraceabilityFileDO;
import com.tianma315.core.traceability.service.TraceabilityFileService;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * <pre>
 * 溯源档案
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
@Service
public class TraceabilityFileServiceImpl extends CoreServiceImpl<TraceabilityFileDao, TraceabilityFileDO> implements TraceabilityFileService {
    @Autowired
    private ProductService productService;
    @Autowired
    private TyMenuService tyMenuService;
    @Autowired
    private ProceduresService proceduresService;
    @Autowired
    private ProcedureLinkService procedureLinkService;
    @Autowired
    private LinkContentService linkContentService;
    @Autowired
    private TraceabilityFileDao traceabilityFileDao;
    @Autowired
    private DemoService demoService;
    @Autowired
    private ProceduresBannerService proceduresBannerService;
    @Autowired
    private ProceduresFootlinkService footlinkService;
    @Autowired
    private TyMenuDataService tyMenuDataService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private CodeRecordService codeRecordService;
    @Autowired
    private CodeService codeService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private TraceOutDao traceOutDao;
    @Autowired
    private ScanRecordMapper scanRecordMapper;


    @Override
    public Page<TraceabilityFileVO> getTraceabilityFilePage(Integer pageNumber, Integer pageSize, TraceabilityFileVO traceabilityFileVO) {
        Page<TraceabilityFileVO> page = new Page<>(pageNumber, pageSize);
        List<TraceabilityFileVO> traceabilityFileList = traceabilityFileDao.selectPage(page, traceabilityFileVO);
        page.setRecords(traceabilityFileList);
        return page;
    }

    @Override
    public List<TraceabilityFileDO> getTraceabilityFileList(TraceabilityFileDO TraceabilityFileDO) {
        EntityWrapper<TraceabilityFileDO> entityWrapper = new EntityWrapper();
        entityWrapper.eq("company_id", TraceabilityFileDO.getCompanyId());
        List<TraceabilityFileDO> traceabilityFileDOList = selectList(entityWrapper);
        return traceabilityFileDOList;
    }

    @Override
    public TraceabilityFileDO getTraceabilityFileById(Integer id) {
        TraceabilityFileDO traceabilityFileDO = selectById(id);
        return traceabilityFileDO;
    }

    @Override
    public Boolean updateTraceabilityFile(TraceabilityFileDO traceabilityFile) {
        Boolean result = updateById(traceabilityFile);
        return result;
    }

    @Override
    public Boolean cancelTraceabilityFile(Integer id, Integer state) {
        TraceabilityFileDO traceabilityFile = new TraceabilityFileDO();
        traceabilityFile.setTraceabilityFileId(id);
        traceabilityFile.setState(state);

        Boolean result = updateById(traceabilityFile);
        return result;
    }

    @Override
    public Boolean addTraceabilityFile(TraceabilityFileDO traceabilityFile) {
        traceabilityFile.setState(StateEnum.not_del.getIndex());
        Boolean result = insert(traceabilityFile);
        return result;
    }

    @Override
    public List<TraceabilityFileDO> traceabilityFileCheck(TraceabilityFileDO traceabilityFile) {
//        List<TraceabilityFileDO> traceabilityFiles = traceabilityFileDao.traceabilityFileCheck(traceabilityFile);
//        return traceabilityFiles;

        return null;

    }

    @Override
    public ProceduresDetailVO getProceduresInfoView(Integer proceduresId) {
        ProceduresDetailVO proceduresDetailVO = new ProceduresDetailVO();
        //根据proceduresId查找procedures
        ProceduresDO procedures = proceduresService.getProcedureDO(proceduresId);
        proceduresDetailVO.setProcedures(procedures);
        //根据proceduresId查看procedureLink
        List<ProcedureLinkDO> procedureLinkList = procedureLinkService.getProcedureLinkDOByProcedureId(proceduresId);
        List<ProceduresLinkVO> proceduresLinkVOList = new ArrayList<>();
        //根据procedureLink查找linkContent
        for (ProcedureLinkDO p : procedureLinkList) {
            ProceduresLinkVO proceduresLinkVO = new ProceduresLinkVO();
            List<LinkContentDO> linkContentList = linkContentService.getLinkContentListByProcedureLinkId(p.getProcedureLinkId());
            proceduresLinkVO.setProcedureLink(p);
            proceduresLinkVO.setLinkContentList(linkContentList);
            proceduresLinkVOList.add(proceduresLinkVO);
        }
        proceduresDetailVO.setProceduresLinkVOList(proceduresLinkVOList);
        return proceduresDetailVO;
    }

    @Override
    public Page<? extends TraceabilityFileDO> getPage(int pageNumber, int pageSize, String searchKey, Long companyId, String sortName, String sortOrder) {
        Page<TraceabilityFileVO> page = new Page<>(pageNumber, pageSize);
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("company_id", companyId);

//        List list = traceabilityFileDao.selectPage(page, wrapper);
//        page.setRecords(list);
        return page;
    }

    @Override
    public boolean updateStateById(Integer traceabilityFileId) {
        TraceabilityFileDO traceabilityFileDO = new TraceabilityFileDO();
        traceabilityFileDO.setState(1);
        traceabilityFileDO.setTraceabilityFileId(traceabilityFileId);
        Boolean result = updateById(traceabilityFileDO);
        return result;
    }

    @Override
    public List<TraceabilityFileDO> getTraceabilityFileDOList() {
        EntityWrapper<TraceabilityFileDO> wrapper = new EntityWrapper<>();
        wrapper.eq("state", 0);
        return selectList(wrapper);
    }

    /**
     * 查看档案
     *
     * @return
     */
    @Override
    public JSONObject getDataByTraceabilityFileId(Integer id) {

        JSONObject result = new JSONObject();
        //获取溯源档案
        TraceabilityFileDO traceabilityFileDO = selectById(id);
        if (traceabilityFileDO == null) {
            throw new MessageException("没有找到溯源档案");
        }

        //获取产品信息
        Product productDO = productService.getById(traceabilityFileDO.getProductId());
        if (productDO == null) {
            throw new MessageException("没有找到产品");
        }


        //根据档案id查找整个档案流程
        List<TyMenuVO> tyMenuVOS = tyMenuService.getTyMenuByTraceabilityFileId(traceabilityFileDO.getTraceabilityFileId());
        if (CollectionUtils.isEmpty(tyMenuVOS)) {
            throw new MessageException("没找到档案流程");
        }


        result.put("code", 200);
        result.put("product", productDO);
        result.put("produceCenter", tyMenuVOS);

        return result;
    }

    @Override
    public TraceabilityFileVO getTraceabilityFileInfo(long id) {
        //查询档案信息
        TraceabilityFileVO tf = baseMapper.selectInfoId(id);
        if (tf == null) {
            throw new MessageException("档案不存在");
        }
        //set 图片列表list
        List<TyMenuVO> menuList = tf.getMenuList();
        for (int i = 0; i < menuList.size(); i++) {
            List<TyMenuDataVO> dataList = menuList.get(i).getDataList();
            for (int j = 0; j < dataList.size(); j++) {
                TyMenuDataVO tyMenuDataVO = dataList.get(j);
                if (tyMenuDataVO.getType() == 1) {
                    String[] imgList = tyMenuDataVO.getDataValue().split(",");
                    List<String> strings = Arrays.asList(imgList);
                    tyMenuDataVO.setPics(strings);
                }
            }
        }

        Integer proceduresId = tf.getProceduresId();
        //查询轮播图
        Wrapper<ProceduresBanner> wrapper = new EntityWrapper<>();
        wrapper.eq("procedures_id", proceduresId);
        wrapper.eq("banner_status", 1);
        wrapper.orderBy("banner_sequence");
        List<ProceduresBanner> proceduresBanners = proceduresBannerService.selectList(wrapper);

        tf.setProBanners(proceduresBanners);//存入轮播图信息

        //查询底部链接

        Wrapper<ProceduresFootlink> footWrapper = new EntityWrapper<>();
        footWrapper.eq("procedures_id", proceduresId);
        footWrapper.eq("footlink_status", 1);
        List<ProceduresFootlink> proceduresFootlinks = footlinkService.selectList(footWrapper);

        tf.setProFootLinks(proceduresFootlinks);//存入底部链接信息

        //查询产品信息
        Product product = productService.getById(tf.getProductId());
        if (product == null) {
            throw new MessageException("没有找到产品");
        }
        tf.setProduct(product);
        //查询模板信息
        ProceduresDO procedures = proceduresService.selectById(product.getProceduresId());
        if (procedures == null) {
            throw new MessageException("模板信息获取失败");
        }
        //获取主题信息
        DemoDO demo = demoService.selectById(procedures.getThemeId());
        if (demo == null) {
            throw new MessageException("主题信息获取失败");
        }
        tf.setTheme(demo.getDemoValues());
        return tf;
    }

    @Override
    public boolean inserts(TraceabilityFileDO traceabilityFile) {
        //添加到档案数据到表中去
        insert(traceabilityFile);


        //查找产品绑定的溯源模版
        Long productId = traceabilityFile.getProductId();
        Product product = productService.selectById(productId);
        Integer proceduresId = product.getProceduresId();

        //通过proceduresId 查找所属环节
        Wrapper<ProcedureLinkDO> wrapper = new EntityWrapper<>();
        wrapper.eq("state", 0).eq("procedures_id", proceduresId);
        List<ProcedureLinkDO> procedureLinkDOS = procedureLinkService.selectList(wrapper);
        if (!procedureLinkDOS.isEmpty()) {
            for (ProcedureLinkDO procedureLinkDO : procedureLinkDOS) {
                if (procedureLinkDO.getIsDynamic() == 0) {
                    Integer procedureLinkId = procedureLinkDO.getProcedureLinkId();
                    TyMenuDO tyMenuDO = new TyMenuDO();
                    tyMenuDO.setCreateBy(traceabilityFile.getCreateBy());
                    tyMenuDO.setCreateDate(Calendar.getInstance().getTime());
                    tyMenuDO.setMenuName(procedureLinkDO.getProcedureLinkName());
                    tyMenuDO.setMenuLevel(0);
                    tyMenuDO.setParentMenuId(0);
                    tyMenuDO.setTraceabilityFileId(traceabilityFile.getTraceabilityFileId());
                    tyMenuDO.setProcedureLinkId(procedureLinkId);
                    tyMenuDO.setIcon(procedureLinkDO.getIcon());
                    tyMenuService.insert(tyMenuDO);
                    //根据环节id查找细节默认值内容

                    Wrapper<LinkContentDO> wrapper1 = new EntityWrapper<>();
                    wrapper1.eq("state", 0).eq("procedures_link_id", procedureLinkId);
                    List<LinkContentDO> linkContentDOS = linkContentService.selectList(wrapper1);
                    if (!linkContentDOS.isEmpty()) {
                        for (LinkContentDO linkContentDO : linkContentDOS) {
                            TyMenuDataDO tyMenuDataDO = new TyMenuDataDO();
                            tyMenuDataDO.setCreateBy(traceabilityFile.getCreateBy());
                            tyMenuDataDO.setCreateDate(Calendar.getInstance().getTime());
                            tyMenuDataDO.setDataValue(linkContentDO.getDefaultValue());
                            tyMenuDataDO.setLinkContentId(linkContentDO.getLinkContentId());
                            tyMenuDataDO.setDataKey(linkContentDO.getContentName());
                            tyMenuDataDO.setMenuId(tyMenuDO.getMenuId());
                            tyMenuDataDO.setType(linkContentDO.getType());
                            tyMenuDataDO.setMenuDataSort(linkContentDO.getSort());
                            tyMenuDataService.insert(tyMenuDataDO);
                        }
                    }
                }
            }
        }
        return true;
    }

    //wx端code接口获取溯源信息
    @Override
    public TraceInfoVo getTraceInfo(String ident, String code) {
        long traceFileId = -1;

        TraceOutDO record = null;
        if (StringUtils.isNotBlank(ident) && !ident.equals("undefined")) {
            //带产品标识  查询接口获取序列号
            long remoteCodeSerial = getRemoteCodeSerial(ident, code);
            record = getCodeRecordDOS(code, remoteCodeSerial, "%s 接口获取序列号失败");
            if (record != null) {
                traceFileId = record.getTraceabilityFileId();
            }
        } else {
            //无产品标识 直接读取数据库码表获取相关的记录信息
            Code targetCode = getTargetCode(code);
            if (targetCode.getType() == Code.TYPE_TRACE) {
                //溯源码查询出库记录获取绑定档案id
                //获取码的序列号
                long serial = getSerial(targetCode, code);
                record = getCodeRecordDOS(code, serial, "%s 序列号获取失败");
                if (record != null) {
                    traceFileId = record.getTraceabilityFileId();
                }
            }
        }

        if (record == null || traceFileId == -1) {
            throw new MessageViewException(String.format("%s 尚未出库", code));
        }
        //查询溯源档案相关信息
        TraceabilityFileVO vo = getTraceabilityFileInfo(traceFileId);
        TraceInfoVo traceInfo = new TraceInfoVo();

        //更新查询记录
        ScanRecord scanRecord = scanRecordMapper.selectRecordByCode(code);
        if (scanRecord == null) {
            scanRecord = new ScanRecord();
            scanRecord.setCreatedDate(new Date());
            scanRecord.setScanNumber(1);
            scanRecord.setCompanyId(traceInfo.getCompanyId());
            scanRecord.setCode(code);
            scanRecord.setTraceFileId(traceFileId);
            scanRecordMapper.insert(scanRecord);
        } else {
            scanRecord.setScanNumber(scanRecord.getScanNumber() + 1);
            scanRecordMapper.updateById(scanRecord);
        }

        BeanUtils.copyProperties(vo, traceInfo);
        Company company = companyService.getById(traceInfo.getCompanyId());
        traceInfo.setCompany(company);
        traceInfo.setCode(code);
        traceInfo.setFirstQueryDate(scanRecord.getCreatedDate());
        traceInfo.setQueryNumber(scanRecord.getScanNumber());
        return traceInfo;
    }

    private TraceOutDO getCodeRecordDOS(String code, long serialNumber, String s) {
        if (serialNumber == -1) {
            throw new MessageViewException(String.format(s, code));
        }
//        Wrapper<CodeRecordDO> wrapper = new EntityWrapper<>();
//        wrapper.eq("serial_number",serialNumber);
//        CodeRecordDO codeRecordDOS = codeRecordService.findBySerialNumber(serialNumber);
        Wrapper<TraceOutDO> wrapper = new EntityWrapper<>();
        wrapper.le("start_serial", serialNumber);
        wrapper.ge("end_serial", serialNumber);
        List<TraceOutDO> list = traceOutDao.selectList(wrapper);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    /**
     * 出码配置
     *
     * @return
     */
    private JSONObject codeConfig() {
        String code_config = configService.getValuByKey("code_config");

        if (StringUtils.isBlank(code_config)) {
            throw new MessageException("出码配置获取失败");
        }
        JSONObject jsonObject = JSON.parseObject(code_config, JSONObject.class);
        return jsonObject;
    }

    /**
     * 获取码得序列号
     *
     * @param targetCode
     * @param code
     * @return
     */
    private long getSerial(Code targetCode, String code) {
        //优先查询数据库，如果数据库查不到记录再从接口查询序列号
        long sequence = targetCode.getSequence() != null && StringUtils.isNumeric(targetCode.getSequence()) ? Long.parseLong(targetCode.getSequence()) : -1;
        if (code.equals(targetCode.getBigCode())) {
            return sequence;
        } else if (code.equals(targetCode.getSmallCode())) {
            return sequence;
        }
        //return getSerialByRemoteApi(code);
        return -1;
    }

    /**
     * @param code
     * @return
     */
    private Code getTargetCode(String code) {
        Code target = null;
        Wrapper<Code> codeWrapper = new EntityWrapper<Code>()
                .eq("big_code", code)
                .or()
                .eq("small_code", code);
        List<Code> codeList = codeService.selectList(codeWrapper);
        if (codeList == null || codeList.isEmpty()) {
            throw new MessageViewException(String.format("%s 不是有效码", code));
        }
        Iterator<Code> iterator = codeList.iterator();
        while (target == null && iterator.hasNext()) {
            Code c = iterator.next();
            //如果参数code是小码 ，找到对应的记录返回
            if (code.equals(c.getSmallCode())) {
                target = c;
                break;
            }
            //如果参数code是大码  直接返回当前记录
            else if (code.equals(c.getBigCode())) {
                target = c;
                break;
            }
        }
        return target;
    }

    /**
     * 通过接口查码
     *
     * @param ident
     * @param code
     * @return
     */
    private long getRemoteCodeSerial(String ident, String code) {
        JSONObject codeConfig = codeConfig();
        String url = codeConfig.getString("CHECK_CODE_URL");
        if (StringUtils.isBlank(url)) {
            throw new MessageException("查码地址获取失败");
        }
        url = url.replace("{CODE}", code)
                .replace("{PRODUCT_IDENT}", ident);
        RestTemplate restTemplate = new RestTemplate();
        JSONObject result = restTemplate.getForObject(url, JSONObject.class);
        if (!result.getBooleanValue("success")) {
            throw new MessageViewException(result.getString("message"));
        }
        return result.getLong("details");

    }
}

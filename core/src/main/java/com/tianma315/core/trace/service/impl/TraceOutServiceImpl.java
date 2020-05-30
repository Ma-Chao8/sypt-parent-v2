package com.tianma315.core.trace.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;


import com.tianma315.core.coderecord.dao.CodeRecordDao;
import com.tianma315.core.coderecord.domain.CodeRecordDO;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.procedures.dao.ProcedureLinkDao;
import com.tianma315.core.procedures.dao.ProceduresDao;
import com.tianma315.core.procedures.domain.LinkRole;
import com.tianma315.core.procedures.domain.ProcedureLinkDO;
import com.tianma315.core.procedures.domain.ProceduresDO;

import com.tianma315.core.procedures.service.LinkRoleService;
import com.tianma315.core.product.domain.pojo.Product;
import com.tianma315.core.product.service.ProductService;
import com.tianma315.core.report.domain.ReportDO;
import com.tianma315.core.report.domain.ReportImgDO;
import com.tianma315.core.report.service.ReportImgService;
import com.tianma315.core.report.service.ReportService;

import com.tianma315.core.sys.dao.MenuMapper;
import com.tianma315.core.sys.domain.RoleDO;
import com.tianma315.core.sys.service.RoleService;
import com.tianma315.core.trace.dao.TraceOutDao;
import com.tianma315.core.trace.domain.TraceOutDO;
import com.tianma315.core.trace.domain.TraceOutFieldDO;
import com.tianma315.core.trace.service.TraceOutFieldService;
import com.tianma315.core.trace.service.TraceOutService;

import com.tianma315.core.trace.vo.TraceOutAndFieldVO;
import com.tianma315.core.trace.vo.TraceOutApiVO;
import com.tianma315.core.trace.vo.TraceOutAttrFieldVO;
import com.tianma315.core.trace.vo.TraceOutVO;


import com.tianma315.core.traceability.dao.TyMenuDataDao;
import com.tianma315.core.traceability.domain.TyMenuDO;
import com.tianma315.core.traceability.service.TyMenuService;
import com.tianma315.core.traceability.vo.TyMenuDataVO;
import com.tianma315.core.utils.ShiroUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 
 * <pre>
 * 溯源批次出库记录
 * </pre>
 * <small> 2019-08-08 15:02:49 | Wb</small>
 */
@Service
public class TraceOutServiceImpl extends CoreServiceImpl<TraceOutDao, TraceOutDO> implements TraceOutService {

    @Autowired
    private TraceOutDao traceOutDao;
    @Autowired
    private ProceduresDao proceduresDao;
    @Autowired
    private ProcedureLinkDao procedureLinkDao;
    @Autowired
    private TyMenuDataDao tyMenuDataDao;
    @Autowired
    private CodeRecordDao codeRecordDao;
    @Autowired
    private LinkRoleService linkRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuMapper menuMapper;

//    @Autowired
//    private TraceOutFieldService traceOutFieldService;

//    @Autowired
//    private RestTemplate restTemplate;

//    @Autowired
//    private MerchantService merchantService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportImgService reportImgService;

    @Autowired
    private ProductService productService;

//
//    @Autowired
//    private CodesService codesService;

    @Autowired
    private TraceOutFieldService traceOutFieldService;

//    @Autowired
//    private TraceabilityFileService traceabilityFileService;

    @Autowired
    private TyMenuService tyMenuService;


    @Override
    public Page<TraceOutVO> getTraceOutPage(Integer pageNumber, Integer pageSize, TraceOutDO traceOutDTO, Date beginDate, Date endDate) {
        Page page = new Page(pageNumber,pageSize);
        List<TraceOutVO> list = traceOutDao.getTraceOutVO(page,traceOutDTO,beginDate,endDate);
        page.setRecords(list);
        return page;
    }

    @Override
    public TraceOutAndFieldVO getTraceOutById(Integer id) {
        TraceOutAndFieldVO traceOutAndFieldVO = new TraceOutAndFieldVO();
        TraceOutDO traceOutDO = selectById(id);
        BeanUtils.copyProperties(traceOutDO,traceOutAndFieldVO);
        EntityWrapper<TraceOutFieldDO> wrapper = new EntityWrapper<>();
        wrapper.eq("trace_out_id",id);
        List<TraceOutFieldDO> traceOutFieldDOS = traceOutFieldService.selectList(wrapper);
        traceOutAndFieldVO.setTraceOutFieldDOList(traceOutFieldDOS);
        return traceOutAndFieldVO;
    }

    @Transactional
    @Override
    public Boolean addTraceOut(TraceOutAndFieldVO traceOutAndFieldVO) {
        TraceOutDO traceOutDO = new TraceOutDO();
        BeanUtils.copyProperties(traceOutAndFieldVO,traceOutDO);
        traceOutDO.setState(StateEnum.not_del.getIndex());
        traceOutDO.setCreateDate(Calendar.getInstance().getTime());
        Boolean result = insert(traceOutDO);

        List<TraceOutFieldDO> list = traceOutAndFieldVO.getTraceOutFieldDOList();
        if (CollectionUtils.isNotEmpty(list)){
            for (TraceOutFieldDO traceOutField:list) {
                traceOutField.setState(StateEnum.not_del.getIndex());
                traceOutField.setTraceOutId(traceOutDO.getTraceOutId());
                traceOutFieldService.insert(traceOutField);
            }
        }

        // 批量初始化流水码查询记录
        List<CodeRecordDO> records = new ArrayList<>();
        for (int i = traceOutDO.getStartSerial(); i <= traceOutDO.getEndSerial(); i++) {
            CodeRecordDO record = new CodeRecordDO();
            record.setSerialNumber(i);
            record.setRecordNumber(0);
            record.setTraceabilityFileId(traceOutDO.getTraceabilityFileId());
            records.add(record);
        }

        int split_length = 1000; //列表分隔长度
        int groupNum = 0; //列表可分隔的组数
        //计算列表可切割的组数
        if (records.size() % split_length == 0) {
            groupNum = records.size() / split_length;
        } else {
            groupNum = (records.size() / split_length) + 1;
        }
        for (int index = 0; index < groupNum; index++) {
            int start = index * split_length;//起始下标
            int end = start + split_length;//结束下标
            end = end >= records.size() ? records.size() : end;
            List<CodeRecordDO> recordList = records.subList(start, end);
            if (codeRecordDao.insertCodeRecordBatch(recordList) != recordList.size()) {
                throw new MessageException("部分码绑定失败,请重新操作");
            }
        }
        return result;
    }

    @Transactional
    @Override
    public Boolean updateTraceOut(TraceOutAndFieldVO traceOutAndFieldVO) {
        TraceOutDO traceOutDO = new TraceOutDO();
        BeanUtils.copyProperties(traceOutAndFieldVO,traceOutDO);
        Boolean result = updateById(traceOutDO);

        EntityWrapper<TraceOutFieldDO> wrapper = new EntityWrapper<>();
        wrapper.eq("trace_out_id",traceOutDO.getTraceOutId());
        TraceOutFieldDO traceOutFieldDO = new TraceOutFieldDO();
        traceOutFieldDO.setState(StateEnum.del.getIndex());
        traceOutFieldService.update(traceOutFieldDO,wrapper);

        List<TraceOutFieldDO> list = traceOutAndFieldVO.getTraceOutFieldDOList();
        if (CollectionUtils.isNotEmpty(list)){
            for (TraceOutFieldDO traceOutField:list) {
                traceOutField.setState(StateEnum.not_del.getIndex());
                traceOutField.setTraceOutId(traceOutDO.getTraceOutId());
                traceOutFieldService.insert(traceOutField);
            }
        }
        return result;
    }

    @Override
    public Boolean deleteTraceOut(Integer id) {
        TraceOutDO traceOutDO = new TraceOutDO();
        traceOutDO.setState(StateEnum.del.getIndex());
        traceOutDO.setTraceOutId(id);
        Boolean result = updateById(traceOutDO);
        return result;
    }

    @Override
    public Boolean checkSerial(Integer serial) {
        Boolean result = false;
        EntityWrapper entityWrapper = new EntityWrapper();
//        entityWrapper.eq("merchant_id",merchantId);
        entityWrapper.le("start_serial",serial);
        entityWrapper.ge("end_serial",serial);
        Integer count = selectCount(entityWrapper);
        if(count.equals(0)){
            result = true;
        }
        return result;
    }

    @Override
    public TraceOutApiVO getTraceOutInfo(String code) {
        TraceOutApiVO traceOutApiVO = new TraceOutApiVO();
//        MerchantDO merchantDO = merchantService.selectById(merchantId);
//        if (merchantDO != null ){
//            String url = "http://112.74.55.51:8080/cabby_code_v1/code/json/check/sequence/"+code+"/"+merchantDO.getProductIdent()+"";
//            JSONObject jsonObject = restTemplate.getForObject(url, JSONObject.class);
//            if (jsonObject==null){
//                throw new MessageException("未找到序号");
//            }
//            Integer serial = jsonObject.getInteger("details");
//            Integer serial = 1022;
            //根据code查出序号
//            CodesDO codes =codesService.getCodesByFwCode(code);
//            Integer serial = Integer.parseInt(codes.getSequence());
//            //根据序号查出出库记录
//            TraceOutDO traceOutDO = getTraceOutDOBySerial(serial);
//            traceOutApiVO.setTraceOutDO(traceOutDO);
//            if (traceOutDO != null){
//                ProductDO productDO = productService.selectById(traceOutDO.getProductId());
//                traceOutApiVO.setProductDO(productDO);
//                if (traceOutDO.getReportId()!=null){
//                    ReportApiVO reportApiVO = new ReportApiVO();
//                    ReportDO reportDO = reportService.selectById(traceOutDO.getReportId().longValue());
//                    List<ReportImgDO> reportImgDOS = reportImgService.getReportImgByReportId(reportDO.getReportId());
//                    reportApiVO.setReportImgDOList(reportImgDOS);
//                    reportApiVO.setReportDO(reportDO);
//                    traceOutApiVO.setReportApiVO(reportApiVO);
//                }
                //获取属性
//                List<TraceOutAttrFieldVO> traceOutAttrFieldVOS = traceOutFieldService.getTraceOutAttrFieldByTraceOutId(traceOutDO.getTraceOutId());
//                traceOutApiVO.setTraceOutAttrFieldVOS(traceOutAttrFieldVOS);
//            }
//        }else{
//            throw new MessageException("未找到对应的商户");
//        }


        return traceOutApiVO;
    }

    @Override
    public List<ProcedureLinkDO> selectContent(Long userId,Integer productId, Integer traceabilityFileId) {

        //根据产品绑定的模版获取环节列表
        Product product = productService.getById(productId);
        Integer procedureId = product.getProceduresId();
        Wrapper<ProcedureLinkDO> wrapper2 = new EntityWrapper<>();
        wrapper2.eq("state",0);
        wrapper2.eq("procedures_id",procedureId);
        wrapper2.orderBy("sort");
        List<ProcedureLinkDO> procedureLinkDOS = procedureLinkDao.selectList(wrapper2);

        //环节为空
        if (procedureLinkDOS.isEmpty()){
            return null;
        }

        //给环节设置权限
        Iterator<ProcedureLinkDO> iterator = procedureLinkDOS.iterator();
        Subject subjct = ShiroUtils.getSubjct();
        Integer companyId = menuMapper.selectUserCompanyId(userId);
        while (iterator.hasNext()){
            ProcedureLinkDO procedureLinkDO = iterator.next();
            //查看当前用户是否为公司0
            if (companyId==0){
                procedureLinkDO.setIsRole(1);
            }else if (procedureLinkDO.getIsAll()==0){
                Integer procedureLinkId = procedureLinkDO.getProcedureLinkId();
                List<String> roleSigns = roleService.selectRoleList(procedureLinkId);
                boolean[] booleans = subjct.hasRoles(roleSigns);
                for (boolean aBoolean : booleans) {
                    if (aBoolean){
                        procedureLinkDO.setIsRole(1);
                    }
                }
            }

            //该环节是否已被编辑
            EntityWrapper<TyMenuDO> tyMenuDOwrapper = new EntityWrapper<>();
            tyMenuDOwrapper.eq("procedure_link_id",procedureLinkDO.getProcedureLinkId());
            tyMenuDOwrapper.eq("traceability_file_id",traceabilityFileId);
            List<TyMenuDO> tyMenuDOS = tyMenuService.selectList(tyMenuDOwrapper);
            if (null==tyMenuDOS||tyMenuDOS.size()==0){
                procedureLinkDO.setIsAdd(0);
            }else {
                procedureLinkDO.setIsAdd(1);
            }
        }

        return procedureLinkDOS;
    }

    @Override
    public List<TyMenuDataVO> getTyMenuDataDOS(Integer id, Integer traceabilityFileId) {
        Wrapper<TyMenuDO> tyMenuDOWrapper = new EntityWrapper<>();
        tyMenuDOWrapper.eq("procedure_link_id",id);
        tyMenuDOWrapper.eq("traceability_file_id",traceabilityFileId);
        List<TyMenuDO> tyMenuDOS = tyMenuService.selectList(tyMenuDOWrapper);
        List<TyMenuDataVO> tyMenuDataDOS = tyMenuDataDao.selectVOList(tyMenuDOS.get(0).getMenuId());
        for (int i = 0; i < tyMenuDataDOS.size(); i++) {
            TyMenuDataVO tyMenuData = tyMenuDataDOS.get(i);
            if (tyMenuData.getType() == 1);{
                List<String> pics = new ArrayList<>();
                String dataValue = tyMenuDataDOS.get(i).getDataValue();
                String[] split = dataValue.split(",");
                if (split.length>1){
                    for (int j = 0; j < split.length; j++) {
                        pics.add(split[j]);
                    }
                }else if (split.length == 1){
                    pics.add(split[0]);
                }
                tyMenuData.setPics(pics);
            }
        }
        return tyMenuDataDOS;
    }


    public TraceOutDO getTraceOutDOBySerial( Integer serial){
        EntityWrapper<TraceOutDO> traceOutDOEntityWrapper = new EntityWrapper<>();
        traceOutDOEntityWrapper.le("start_serial",serial);
        traceOutDOEntityWrapper.ge("end_serial",serial);
        TraceOutDO traceOutDO = selectOne(traceOutDOEntityWrapper);
        return traceOutDO;
    }

}

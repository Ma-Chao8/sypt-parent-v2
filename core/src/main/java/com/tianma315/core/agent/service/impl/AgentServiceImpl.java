package com.tianma315.core.agent.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.agent.dao.AgentAddressDao;
import com.tianma315.core.agent.dao.AgentDao;
import com.tianma315.core.agent.domain.AgentAddressDO;
import com.tianma315.core.agent.domain.AgentDO;
import com.tianma315.core.agent.domain.AgentLevelDO;
import com.tianma315.core.agent.dto.AgentDto;
import com.tianma315.core.agent.service.AgentAddressService;
import com.tianma315.core.agent.service.AgentLevelService;
import com.tianma315.core.agent.service.AgentService;
import com.tianma315.core.agent.vo.AgentVO;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.material.domain.MaterialDO;
import com.tianma315.core.material.domain.PurchaseDO;
import com.tianma315.core.material.service.MaterialService;
import com.tianma315.core.supplier.domain.SupplierDO;
import com.tianma315.core.utils.BeanHump;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.management.Agent;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 
 * <pre>
 * 经销商
 * </pre>
 * <small> 2019-07-26 10:05:37 | Wen</small>
 */
@Service
public class AgentServiceImpl extends CoreServiceImpl<AgentDao, AgentDO> implements AgentService {

    @Autowired
    private AgentDao agentDao;
    @Autowired
    private AgentAddressDao agentAddressDao;

    @Autowired
    private AgentLevelService agentLevelService;

    @Autowired
    private AgentAddressService agentAddressService;
    @Override
    public List<Agent> getAgentByLevelId(Long levelId) {
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.eq("level_id",levelId);
        List<Agent> agents = selectList(wrapper);
        return agents;
    }

    @Override
    public Page<AgentVO> getAgentPage(Integer pageNumber, Integer pageSize, AgentDto agentDTO) {
        agentDTO.setSortName(BeanHump.camelToUnderline(agentDTO.getSortName()));
        Page<AgentVO> agentVOPage = new Page<>(pageNumber,pageSize);
        List<AgentVO> agentVOS = agentDao.getAgentVO(agentVOPage,agentDTO);
        agentVOPage.setRecords(agentVOS);
        return agentVOPage;
    }

    @Override
    public Boolean addAgentVO(AgentVO agentVO) {
        AgentDO agentDO = new AgentDO();
        BeanUtils.copyProperties(agentVO,agentDO);
        agentDO.setCreateDate(Calendar.getInstance().getTime());
        agentDO.setStatus(StateEnum.usable.getIndex());
        Boolean agentResult = insert(agentDO);
        //插入地址
        AgentAddressDO agentAddressDO = new AgentAddressDO();
        BeanUtils.copyProperties(agentVO,agentAddressDO);
        agentAddressDO.setCreateDate(Calendar.getInstance().getTime());
        agentAddressDO.setAgentId(agentDO.getAgentId());
        agentAddressDO.setStatus(1);
        Boolean agentAddressResult = agentAddressService.insert(agentAddressDO);
        return agentAddressResult;
    }

    @Override
    public Boolean deleteAgentStatus(Long id,Integer status) {
        AgentDO agentDO = new AgentDO();
        agentDO.setAgentId(id);
        agentDO.setStatus(status);
        Boolean result = updateById(agentDO);
        return result;
    }

    @Override
    public Boolean UpdateAgentVO(AgentVO agentVO) {
        AgentDO agentDO = new AgentDO();
        BeanUtils.copyProperties(agentVO,agentDO);
        agentDO.setCreateDate(Calendar.getInstance().getTime());
        agentDO.setStatus(StateEnum.usable.getIndex());
        Boolean agentResult = updateById(agentDO);
        //插入地址
        AgentAddressDO agentAddressDO = new AgentAddressDO();
        BeanUtils.copyProperties(agentVO,agentAddressDO);
        agentAddressDO.setCreateDate(Calendar.getInstance().getTime());
        agentAddressDO.setAgentId(agentDO.getAgentId());
        agentAddressDO.setStatus(1);
        Boolean agentAddressResult = agentAddressService.updateById(agentAddressDO);
        return agentAddressResult;
    }

    @Override
    public AgentVO getAgentVOById(Long id) {
        AgentVO agentVO = new AgentVO();
        AgentDO agentDO = selectById(id);
        BeanUtils.copyProperties(agentDO,agentVO);
        EntityWrapper<AgentAddressDO> wrapper = new EntityWrapper();
        wrapper.eq("agent_id",id);
        wrapper.eq("status",1);
        AgentAddressDO agentAddressDO = agentAddressService.selectOne(wrapper);
        BeanUtils.copyProperties(agentAddressDO,agentVO);
        return agentVO;
    }

    @Override
    public List<AgentDO> getAgentList(Long companyId) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("company_id",companyId);
        entityWrapper.eq("status",StateEnum.usable.getIndex());
        List<AgentDO> agents = selectList(entityWrapper);
        return agents;
    }

    @Override
    public HSSFWorkbook exportAgent(Long companyId) {
//        String[] title = {"等级","代理商名字","联系人","电话","邮箱","详细地址"};
        String[] title = {"代理商名字","联系人","电话","邮箱","省级(如'广东')","市级（如'广州市',不能省略'市'关键字）","区级（如'黄埔区',不能省略'区'或'县'关键字）","详细地址"};
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("经销商记录模板");

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
        //合并表头单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
        titleCell.setCellValue("经销商模板（红色标识列为必填）");
        titleCell.setCellStyle(titleStyle);

        //设置列宽
        sheet.setColumnWidth(4,10 * 512);
        sheet.setColumnWidth(5,20 * 512);
        sheet.setColumnWidth(6,20 * 512);
        sheet.setColumnWidth(7,10 * 512);
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
            if (i != title.length-5){
                cell = headRow.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(redStyle);
            }else{
                cell = headRow.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(defaultStyle);
            }
        }
      //  List<AgentLevelDO> agentLevelDOS = agentLevelService.agentLevelDOList(companyId);
      //  List<String> agentLevelList = agentLevelDOS.stream().map(item -> item.getLevelName()).collect(Collectors.toList());
     //   String[] agentLevelNameArr = agentLevelList.toArray(new String[agentLevelList.size()]);

        //设置设备类型为下拉框选项
       // DVConstraint constraint = DVConstraint.createExplicitListConstraint(agentLevelNameArr);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
       // CellRangeAddressList regions = new CellRangeAddressList(2,
        //        10000, 0, 0);
       // HSSFDataValidation agentLevelData = new HSSFDataValidation(
        //        regions, constraint);
       // sheet.addValidationData(agentLevelData);
        return wb;
    }

    @Override
    public void importAgent(MultipartFile file, Long companyId, Long userId) {
        Boolean flag = isExcel(file.getOriginalFilename());
        if (!flag){
            throw new MessageException("只支持Excel文件");
        }

        List<AgentLevelDO> agentLevelDOS = agentLevelService.agentLevelDOList(companyId);

//        List<MaterialDO> materialDOList = materialService.getMaterialList(companyId);
//        List<SupplierDO> supplierDOList = supplierService.getSupplierList(companyId);

        try {
            HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum(); //返回行数下标 从0开始
            //前两行都不是内容，所以从2开始
            for (int i = 2; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                short num = row.getLastCellNum();

                AgentDO agentDO = new AgentDO();
                agentDO.setStatus(StateEnum.usable.getIndex());
                agentDO.setCreateDate(Calendar.getInstance().getTime());
                agentDO.setUserId(userId);
                agentDO.setCompanyId(companyId);
                //设置等级id
//                Cell agentCell = row.getCell(0);
//                if (isCellEmpty(agentCell)){
//                    throw new MessageException("等级不能为空");
//                }
//                List<AgentLevelDO> agentFilterResult = agentLevelDOS.stream()
//                        .filter(item -> item.getLevelName().equals(agentCell.getStringCellValue()))
//                        .collect(Collectors.toList());
//
//                agentDO.setLevelId(agentFilterResult.get(0).getLevelId());


                //设置代理商名字
                Cell agentNameCell = row.getCell(0);
                if (isCellEmpty(agentNameCell)){
                    throw new MessageException("代理商不能为空");
                }
                agentDO.setAgentName(agentNameCell.getStringCellValue());

                //设置联系人
                Cell linkManCell = row.getCell(1);
                if (isCellEmpty(linkManCell)){
                    throw new MessageException("联系人不能为空");
                }
                agentDO.setLinkman(linkManCell.getStringCellValue());


                //设置电话
                Cell telCell = row.getCell(2);
                if (isCellEmpty(telCell)){
                    throw new MessageException("电话不能为空");
                }
                agentDO.setTel(telCell.getStringCellValue());


                //设置邮箱
                Cell emailCell = row.getCell(3);
                if (isCellEmpty(emailCell)){
                    agentDO.setEmail(null);
                }else {
                    agentDO.setEmail(emailCell.getStringCellValue());
                }
                //
                insert(agentDO);


                AgentAddressDO agentAddressDO = new AgentAddressDO();
                agentAddressDO.setStatus(StateEnum.usable.getIndex());
                agentAddressDO.setCreateDate(Calendar.getInstance().getTime());
                agentAddressDO.setCompanyId(companyId);
                agentAddressDO.setAgentId(agentDO.getAgentId());
                //设置省级
                Cell provinceCell = row.getCell(4);
                if (isCellEmpty(provinceCell)){
                    throw new MessageException("省级不能为空");
                }else {
                    agentAddressDO.setProvince(provinceCell.getStringCellValue());
                }
                //设置市级
                Cell cityCell = row.getCell(5);
                String cityCellValue = cityCell.getStringCellValue();
                if (isCellEmpty(cityCell)){
                    throw new MessageException("市级不能为空");
                }else if (!cityCellValue.substring(cityCellValue.length()-1).equals("市")){
                    throw new MessageException("‘市’关键字不能省略");
                }else {
                    agentAddressDO.setCity(cityCellValue);
                }


                //设置区级
                Cell areaCell = row.getCell(6);
                String areaCellValue = areaCell.getStringCellValue();
                if (isCellEmpty(areaCell)){
                    throw new MessageException("区或县级不能为空");
                }else if (areaCellValue.substring(areaCellValue.length()-1).equals("区")||areaCellValue.substring(areaCellValue.length()-1).equals("县")){
                    agentAddressDO.setArea(areaCellValue);
                }else {
                    throw new MessageException("不能省略'区'或'县'关键字");
                }

                //设置详细地址
                Cell addressCell = row.getCell(7);
                if (isCellEmpty(addressCell)){
                    throw new MessageException("详细地址不能为空");
                }
                agentAddressDO.setAddress(addressCell.getStringCellValue());
                agentAddressDao.insert(agentAddressDO);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
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

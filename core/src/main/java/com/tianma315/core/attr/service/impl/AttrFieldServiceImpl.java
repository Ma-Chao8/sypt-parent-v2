package com.tianma315.core.attr.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.attr.dao.AttrFieldDao;
import com.tianma315.core.attr.domain.AttrFieldDO;
import com.tianma315.core.attr.domain.FieldValueDO;
import com.tianma315.core.attr.service.AttrFieldService;
import com.tianma315.core.attr.service.FieldValueService;
import com.tianma315.core.attr.vo.AttrFieldAndFieldVO;
import com.tianma315.core.attr.vo.AttrFieldVO;

import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.IFastException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * <pre>
 * 辅助属性字段
 * </pre>
 * <small> 2019-08-02 15:38:24 | Wen</small>
 */
@Service
public class AttrFieldServiceImpl extends CoreServiceImpl<AttrFieldDao, AttrFieldDO> implements AttrFieldService {

    @Autowired
    private FieldValueService fieldValueService;

    @Autowired
    private AttrFieldDao attrFieldDao;


    @Override
    public List<AttrFieldAndFieldVO> getAttrFieldVO(AttrFieldDO attrFieldDO) {
        List<AttrFieldAndFieldVO> attrFieldAndFieldVOS = attrFieldDao.getAttrFieldAndField(attrFieldDO);
        return attrFieldAndFieldVOS;
    }

    @Override
    public AttrFieldAndFieldVO getAttrFieldById(Integer id) {
        AttrFieldAndFieldVO attrFieldAndFieldVO = new AttrFieldAndFieldVO();
        AttrFieldDO attrFieldDO = selectById(id);
        BeanUtils.copyProperties(attrFieldDO,attrFieldAndFieldVO);
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("attr_field_id",id);
        List<FieldValueDO> fieldValueDOS = fieldValueService.selectList(entityWrapper);
        attrFieldAndFieldVO.setFieldValueDOList(fieldValueDOS);
        return attrFieldAndFieldVO;
    }

    @Transactional
    @Override
    public Boolean addAttrFieldVO(AttrFieldVO attrFieldVO) {
        AttrFieldDO attrFieldDO = new AttrFieldDO();
        BeanUtils.copyProperties(attrFieldVO,attrFieldDO);
        attrFieldDO.setState(StateEnum.not_del.getIndex());
        Boolean isReport = checkAttrRepeat(attrFieldDO);
        if (isReport){
//            throw new IFastException(EnumErrorCode.orderNumRepeat.getCodeStr());
            throw new IFastException("");
        }
        Boolean result = insert(attrFieldDO);


        String str = attrFieldVO.getFieldValue().trim();
//        String regex = "\u002A";
        String[] strArr = str.split("\\*");
        for (int i = 0; i<strArr.length;i++){
            FieldValueDO fieldValueDO = new FieldValueDO();
            fieldValueDO.setAttrFieldId(attrFieldDO.getAttrFieldId());
            fieldValueDO.setFieldValue(strArr[i]);
            fieldValueService.insert(fieldValueDO);

        }
        return result;
    }

    @Override
    public Boolean deleteAttrFieldDO(Integer id) {
        AttrFieldDO attrFieldDO = new AttrFieldDO();
        attrFieldDO.setState(StateEnum.del.getIndex());
        attrFieldDO.setAttrFieldId(id);
        Boolean result = updateById(attrFieldDO);
        return result;
    }

    @Transactional
    @Override
    public Boolean updateAttrFieldDO(AttrFieldVO attrFieldVO) {
        AttrFieldDO attrFieldDO = new AttrFieldDO();
        BeanUtils.copyProperties(attrFieldVO,attrFieldDO);
        attrFieldDO.setState(StateEnum.not_del.getIndex());
        Boolean isReport = checkAttrRepeat(attrFieldDO);
        if (isReport){
            throw new IFastException("未知错误");
        }
        Boolean result = updateById(attrFieldDO);

        //插入之前把旧数据删除
        EntityWrapper<FieldValueDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("attr_field_id",attrFieldDO.getAttrFieldId());
        fieldValueService.delete(entityWrapper);

        String str = attrFieldVO.getFieldValue().trim();
//        String regex = "\u002A";
        String[] strArr = str.split("\\*");
        for (int i = 0; i<strArr.length;i++){
            FieldValueDO fieldValueDO = new FieldValueDO();
            fieldValueDO.setAttrFieldId(attrFieldDO.getAttrFieldId());
            fieldValueDO.setFieldValue(strArr[i]);
            fieldValueService.insert(fieldValueDO);
        }
        return result;
    }

    @Override
    public Boolean checkAttrRepeat(AttrFieldDO attrFieldDO) {
        EntityWrapper entityWrapper = new EntityWrapper();
        entityWrapper.eq("attr_id",attrFieldDO.getAttrId());
        entityWrapper.eq("state",StateEnum.not_del.getIndex());
        entityWrapper.eq("order_num",attrFieldDO.getOrderNum());
        if (attrFieldDO.getAttrFieldId()!=null){
            entityWrapper.ne("attr_field_id",attrFieldDO.getAttrFieldId());
        }

        AttrFieldDO reportAttr = selectOne(entityWrapper);
        if (reportAttr != null) {
            return true;
        }else {
            return false;
        }
    }


}

package com.tianma315.core.procedures.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.commons.enumutil.StateEnum;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.procedures.dao.LinkContentDao;
import com.tianma315.core.procedures.domain.LinkContentDO;
import com.tianma315.core.procedures.domain.dto.LinkContentDto;
import com.tianma315.core.procedures.service.LinkContentService;
import com.tianma315.core.sys.service.FileService;
import com.tianma315.core.trace.vo.ImgValueDo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 
 * <pre>
 * 环节内容表
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
@Service
public class LinkContentServiceImpl extends CoreServiceImpl<LinkContentDao, LinkContentDO> implements LinkContentService {

    @Autowired
    FileService fileService;
    @Override
    public Page<LinkContentDO> getLinkContentPage(Integer pageNumber, Integer pageSize, LinkContentDO linkContentDTO) {
        EntityWrapper<LinkContentDO> wrapper = new EntityWrapper<>(linkContentDTO);
        wrapper.eq("state", StateEnum.not_del);
        wrapper.eq("procedures_link_id",linkContentDTO.getProceduresLinkId());
        Page<LinkContentDO> page = new Page<>(pageNumber,pageSize);
        page = selectPage(page,wrapper);
//        page.setRecords(list);
        return page;
    }

    /**
     * 添加溯源环节细节
     * @param linkContentDO
     * @return
     */
    @Override
    public Boolean addLinkContent(LinkContentDto linkContentDO) {
        linkContentDO.setCreateDate(new Date());
        linkContentDO.setState(StateEnum.not_del.getIndex());
        //添加图片逻辑
        ImgValueDo[] imgValueDos = linkContentDO.getImgValues();
        if (imgValueDos!=null){
            String urls = "";
            for (ImgValueDo imgValueDo : imgValueDos) {
                urls += fileService.uploadBase64(imgValueDo.getUrl(),imgValueDo.getSize())+",";
            }
            linkContentDO.setDefaultValue(urls.substring(0,urls.length()-1));
        }
        //保存添加
        LinkContentDO linkContent = new LinkContentDO();
        BeanUtils.copyProperties(linkContentDO,linkContent);
        Boolean result = insert(linkContent);
        return result;
    }

    @Override
    public Boolean deleteLinkContent(Integer id) {
        LinkContentDO linkContentDO = new LinkContentDO();
        linkContentDO.setState(StateEnum.del.getIndex());
        Boolean result = updateById(linkContentDO);
        return result;
    }

    @Override
    public List<LinkContentDO> getLinkContentList(LinkContentDO linkContentDTO) {
        EntityWrapper<LinkContentDO> wrapper = new EntityWrapper<>();
        wrapper.eq("state", StateEnum.not_del);
        wrapper.eq("procedures_link_id",linkContentDTO.getProceduresLinkId());
        wrapper.eq("sort",linkContentDTO.getSort());
        if (linkContentDTO.getLinkContentId() !=null){
            wrapper.ne("link_content_id",linkContentDTO.getLinkContentId());
        }
        List<LinkContentDO> list = selectList(wrapper);
        return list;
    }

    @Override
    public List<LinkContentDO> getLinkContentListByProcedureLinkId(Integer procedureLinkId) {
        EntityWrapper<LinkContentDO> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("state",StateEnum.not_del.getIndex());
        entityWrapper.eq("procedures_link_id",procedureLinkId);
        List<LinkContentDO> LinkContentDOList = selectList(entityWrapper);
        return LinkContentDOList;
    }
}

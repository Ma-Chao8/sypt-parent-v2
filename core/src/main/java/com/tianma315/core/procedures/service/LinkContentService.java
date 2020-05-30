package com.tianma315.core.procedures.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreService;
import com.tianma315.core.procedures.domain.LinkContentDO;
import com.tianma315.core.procedures.domain.dto.LinkContentDto;

import java.util.List;

/**
 * 
 * <pre>
 * 环节内容表
 * </pre>
 * <small> 2019-06-18 16:34:19 | Wen</small>
 */
public interface LinkContentService extends CoreService<LinkContentDO> {

    Page<LinkContentDO> getLinkContentPage(Integer pageNumber, Integer pageSize, LinkContentDO linkContentDTO);

    Boolean addLinkContent(LinkContentDto linkContentDO);

    Boolean deleteLinkContent(Integer id);

    List<LinkContentDO> getLinkContentList(LinkContentDO linkContentDTO);

    List<LinkContentDO> getLinkContentListByProcedureLinkId(Integer procedureLinkId);
}

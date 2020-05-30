package com.tianma315.core.procedures.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.procedures.dao.ProceduresFootlinkDao;
import com.tianma315.core.procedures.domain.ProceduresBanner;
import com.tianma315.core.procedures.domain.ProceduresFootlink;
import com.tianma315.core.procedures.domain.dto.ProceduresFootlinkDto;
import com.tianma315.core.procedures.service.ProceduresFootlinkService;
import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 *
 */
@Service
public class ProceduresFootlinkServiceImpl extends CoreServiceImpl<ProceduresFootlinkDao, ProceduresFootlink> implements ProceduresFootlinkService {

    @Autowired
    private FileService fileService;


    @Override
    public Page<ProceduresFootlink> getPage(Integer pageNumber, Integer pageSize, long proceduresId) {
        Page<ProceduresFootlink> page = new Page<>(pageNumber, pageSize);
        Wrapper<ProceduresFootlink> wrapper = new EntityWrapper<>();
        wrapper.eq("procedures_id", proceduresId);
        wrapper.orderBy("footlink_sequence",true);
        selectPage(page, wrapper);
        return page;
    }

    @Override
    public boolean addFootlink(Long userId, ProceduresFootlinkDto footlink) {
        FileDO file;
        Wrapper<ProceduresFootlink> wrapper = new EntityWrapper<>();
        wrapper.eq("footlink_sequence", footlink.getFootlinkSequence());
        int count = selectCount(wrapper);
        if (count != 0) {
            throw new MessageException("顺序已被使用");
        }
        try {
            if (footlink.getFile() != null && footlink.getFile().getSize() > 0) {
                file = fileService.upload(userId, footlink.getFile().getBytes());
                footlink.setFootlinkIcon(file.getUrl());
                footlink.setFootlinkIconId(file.getId());
            }
        } catch (IOException e) {
            throw new MessageException("文件上传失败", e);
        }

        footlink.setFootlinkStatus(ProceduresFootlink.STATUS_ENABLE);

        if (!insert(footlink)) {
            throw new MessageException("图标保存失败");
        }
        return true;

    }

    @Override
    public ProceduresFootlink getById(long footlinkId) {
        return selectById(footlinkId);
    }

    @Override
    public boolean removeBatchIds(List<Long> asList) {
        return deleteBatchIds(asList);
    }

    @Override
    public boolean edit(Long userId, ProceduresFootlinkDto footlink) {
        FileDO file = null;
        ProceduresFootlink target = selectById(footlink.getFootlinkId());
        Wrapper<ProceduresFootlink> wrapper = new EntityWrapper<>();
        wrapper.eq("footlink_sequence", footlink.getFootlinkSequence());
        int count = selectCount(wrapper);
        if (count != 0 && footlink.getFootlinkSequence() != target.getFootlinkSequence()) {
            throw new MessageException("顺序已被使用");
        }

        if (target == null) {
            throw new MessageException("记录不存在");
        }
        if (footlink.getFile() != null && footlink.getFile().getSize() > 0) {
            try {
                file = fileService.upload(userId, footlink.getFile().getBytes());
            } catch (IOException e) {
                throw new MessageException("文件上传失败", e);
            }
            target.setFootlinkIconId(file.getId());
            target.setFootlinkIcon(file.getUrl());
        }
        target.setFootlinkTitle(footlink.getFootlinkTitle());
        target.setFootlinkContent(footlink.getFootlinkContent());
        target.setFootlinkSequence(footlink.getFootlinkSequence());
        target.setFootlinkRedirectUrl(footlink.getFootlinkRedirectUrl());
        if (!updateById(target)) {
            throw new MessageException("保存失败");
        }
        return true;
    }
}

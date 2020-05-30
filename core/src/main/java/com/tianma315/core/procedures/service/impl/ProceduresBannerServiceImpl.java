package com.tianma315.core.procedures.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreServiceImpl;
import com.tianma315.core.exception.MessageException;
import com.tianma315.core.procedures.dao.ProceduresBannerDao;
import com.tianma315.core.procedures.domain.ProceduresBanner;
import com.tianma315.core.procedures.domain.dto.ProceduresBannerDto;
import com.tianma315.core.procedures.service.ProceduresBannerService;
import com.tianma315.core.sys.domain.FileDO;
import com.tianma315.core.sys.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 *
 */
@Service
@Transactional
public class ProceduresBannerServiceImpl extends CoreServiceImpl<ProceduresBannerDao, ProceduresBanner> implements ProceduresBannerService {

    @Autowired
    private FileService fileService;

    @Override
    public boolean saveBanner(Long userId, ProceduresBannerDto banner) {
        FileDO file;
        Wrapper<ProceduresBanner> wrapper = new EntityWrapper<>();
        wrapper.eq("procedures_id",banner.getProceduresId());
        wrapper.eq("banner_sequence", banner.getBannerSequence());
        int count = selectCount(wrapper);
        if (count != 0) {
            throw new MessageException("轮播顺序已被使用");
        }

        try {
            file = fileService.upload(userId, banner.getFile().getBytes());
        } catch (IOException e) {
            throw new MessageException("文件上传失败", e);
        }

        banner.setBannerStatus(ProceduresBanner.STATUS_ENABLE);
        banner.setBannerImgId(file.getId());
        banner.setBannerUrl(file.getUrl());
        if (!insert(banner)) {
            throw new MessageException("轮播图保存失败");
        }
        return true;
    }

    @Override
    public boolean removeBanner(List<Long> bannerId) {
        return deleteBatchIds(bannerId);
    }

    @Override
    public boolean editBanner(Long userId, ProceduresBannerDto banner) {
        FileDO file = null;
        ProceduresBanner target = selectById(banner.getBannerId());
        Wrapper<ProceduresBanner> wrapper = new EntityWrapper<>();
        wrapper.eq("banner_sequence", banner.getBannerSequence());
        int count = selectCount(wrapper);
        if (count != 0 && banner.getBannerSequence() != target.getBannerSequence()) {
            throw new MessageException("轮播顺序已被使用");
        }

        if (target == null) {
            throw new MessageException("记录不存在");
        }
        if (banner.getFile() != null && banner.getFile().getSize() > 0) {
            try {
                file = fileService.upload(userId, banner.getFile().getBytes());
            } catch (IOException e) {
                throw new MessageException("文件上传失败", e);
            }
            target.setBannerImgId(file.getId());
            target.setBannerUrl(file.getUrl());
        }
        target.setBannerTitle(banner.getBannerTitle());
        target.setBannerTitle(banner.getBannerTitle());
        target.setBannerRedirectUrl(banner.getBannerRedirectUrl());
        target.setBannerSequence(banner.getBannerSequence());
        if (!updateById(target)) {
            throw new MessageException("轮播图保存失败");
        }

        return true;
    }

    @Override
    public Page<ProceduresBanner> getPage(long proceduresId, Integer pageNumber, Integer pageSize) {
        Page<ProceduresBanner> page = new Page<>(pageNumber, pageSize);
        Wrapper<ProceduresBanner> wrapper = new EntityWrapper<>();
        wrapper.eq("procedures_id", proceduresId);
        wrapper.eq("banner_status", ProceduresBanner.STATUS_ENABLE);
        wrapper.orderBy("banner_sequence", true);
        selectPage(page, wrapper);
        return page;
    }

    @Override
    public ProceduresBanner getById(long banner_id) {
        return selectById(banner_id);
    }


}

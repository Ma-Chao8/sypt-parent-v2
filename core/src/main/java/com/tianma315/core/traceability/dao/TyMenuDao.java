package com.tianma315.core.traceability.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.traceability.domain.TyMenuDO;
import com.tianma315.core.traceability.vo.TyMenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <pre>
 * 溯源档案菜单(环节)
 * </pre>
 * <small> 2019-06-20 13:49:53 | Wen</small>
 */
public interface TyMenuDao extends CoreMapper<TyMenuDO> {

    List<TyMenuVO> selectMenuPage(@Param("page") Page page, @Param("trace_file_id") long trace_file_id);


}

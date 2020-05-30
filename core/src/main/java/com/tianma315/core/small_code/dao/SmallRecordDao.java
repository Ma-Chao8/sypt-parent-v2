package com.tianma315.core.small_code.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.tianma315.core.base.CoreMapper;
import com.tianma315.core.small_code.domain.SmallRecordDO;
import com.tianma315.core.small_code.vo.SmallRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <pre>
 * 外包装管理记录
 * </pre>
 * <small> 2019-08-10 10:06:48 | Wen</small>
 */
public interface SmallRecordDao extends CoreMapper<SmallRecordDO> {

    List<SmallRecordDO> selectByCode(@Param("companyId") long companyId, @Param("code") String code);

    List<SmallRecordVO> selectRecordPage(@Param("page")Page page,SmallRecordDO smallRecordDO);

    List<SmallRecordDO> selectListByCode(@Param("code") String code);
}

package com.tianma315.core.social.dao;

import com.tianma315.core.social.domain.UserConnection;
import org.apache.ibatis.annotations.Param;

public interface UserConnectionMapper {

    /**
     *
     * @param userId
     * @return
     */
    UserConnection selectById(@Param("userId") String userId);
}

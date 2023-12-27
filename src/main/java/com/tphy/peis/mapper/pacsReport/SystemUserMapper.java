package com.tphy.peis.mapper.pacsReport;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName DjdhsMapper
 * @Description 导检单回收Mapper
 * @Date 2023-10-16
 * @Version 1.0
 **/
@Mapper
@DS("ds2")
public interface SystemUserMapper {
    /**
     * 根据用户Id查询密码  queryUserPwdById
     * @param usersID
     * @return
     */
    @Select("SELECT s.USER_PASSWORD from SYSTEMUSER s WHERE s.users_ID = #{usersID}")
    List<String> queryUserPwdById(@Param("usersID") String usersID);
}

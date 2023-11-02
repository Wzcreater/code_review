package com.tphy.peis.mapper.peisReport;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName DjdhsMapper
 * @Description 收费明细打印Mapper
 * @Date 2023-10-16
 * @Version 1.0
 **/
@Mapper
public interface SfxpdyMapper {
    /**
     * 根据体检号查询pacs视图返回个人体检项目明细  queryUserPwdById
     * @param examNum
     * @return
     */
    @Select("exec proc_rep_exam_itemAll @exam_num= #{examNum}")
    List<Map<String,Object>> queryPacsItemsByExamNum(@Param("examNum") String examNum);

    /**
     * 根据体检号查询pacs视图返回患者个人单次体检信息  queryPacsPInfoByExamNum
     * @param examNum
     * @return
     */
    @Select("exec proc_rep_exam_info_charge @exam_num= #{examNum}")
    List<Map<String,Object>> queryPacsPInfoByExamNum(@Param("examNum") String examNum);
}

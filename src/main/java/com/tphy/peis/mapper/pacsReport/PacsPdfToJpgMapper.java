package com.tphy.peis.mapper.pacsReport;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName PacsGetReportMapper
 * @Description Pacs拿取体检各项信息Mapper
 * @Date 2023-10-16
 * @Version 1.0
 **/
@Mapper
@Qualifier("secondaryDataSource")
public interface PacsPdfToJpgMapper {
    /**
     * 根据所有体检回传数据 getReportData
     * @param
     * @return
     */
    @Select("SELECT top 100 * from GetPacsReportDataForPeis order by pname Desc ")
     List<Map<String,String>> getReportData();

}

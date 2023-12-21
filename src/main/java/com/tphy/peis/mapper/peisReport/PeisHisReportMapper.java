package com.tphy.peis.mapper.peisReport;

import com.tphy.peis.entity.dto.PacsItemDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface PeisHisReportMapper {

    /**
     * 通过时间范围获取项目收费信息
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<Object,Object>> getItemChargeWithinTime(@Param("startTime") String startTime,@Param("endTime") String endTime);

}

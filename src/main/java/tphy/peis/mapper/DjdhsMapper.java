package tphy.peis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tphy.peis.entity.dto.*;

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
public interface DjdhsMapper {
    List<DepExamResultDTO> queryExamResultByExamNum(@Param("examNum") String examNum);
    List<DepExamResultDTO> queryExamResultByExamNum1(@Param("examNum") String examNum);
    List<DepExamResultDTO> queryExamResultByExamNumInView(@Param("examNum") String examNum);

    List<DepExamResultDTO> queryExamResultByExamNumInView1(@Param("examNum") String examNum);

    List<CommonExamDetailDTO> queryCommonExamDetail(@Param("examNum") String examNum,@Param("itemCode") String itemCode);
    List<CriticalDTO> queryCriticalDetail(@Param("examNum") String examNum, @Param("itemCode") String itemCode);
    List<CriticalDTO> queryCriticalDatasourseByItemCode(@Param("examNum") String examNum, @Param("itemCode") String itemCode);

    List<CriticalDTO> queryCriticalDatasourseByPacsReqCode(@Param("examNum") String examNum, @Param("pacsReqCode") String pacsReqCode);

    List<ExaminfoChargingItemDTO> queryExaminfoChargingItemDTO(@Param("examNum") String examNum,@Param("centerNum") String centerNum);

    List<SampleExamDetailDTO> querySampleExamDetailDTO(@Param("examNum") String examNum, @Param("sampleId") Long sampleId);

    List<CenterConfigurationDTO> getCenterconfigByKey(@Param("configKey")String configKey);
    //queryExamCriticalDetail
}

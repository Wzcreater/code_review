package tphy.peis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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


    /*
     * @Description: 根据体检编号拆线呢顾客类型，类型编码
     * @Author: ZCZ
     * @Date: 2023/10/18 15:46
     * @Params: [examNum]
     * @Return: java.util.List<tphy.peis.entity.dto.CustomerTypeDTO>
     **/
    List<CustomerTypeDTO>  getcustomerTypeList(@Param("examNum")String examNum);

    /**
     * 公共检查明细表批量插入
     * @param commonExamDetailDTO
     * @return
     */
    void   insertCommonExamDetail(@Param(value = "commonExamDetailList") List<CommonExamDetailDTO> commonExamDetailDTO);

    @Select("SELECT id FROM users WHERE exam_num = #{examNum}")
    String  getExamInfoId(@Param("examNum")String examNum);
}

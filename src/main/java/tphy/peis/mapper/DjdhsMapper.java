package tphy.peis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tphy.peis.entity.dto.*;

import java.util.List;
import java.util.Map;

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

    List<CommonExamDetailDTO> queryCommonExamDetail(@Param("examNum") String examNum, @Param("itemCode") String itemCode);

    List<CriticalDTO> queryCriticalDetail(@Param("examNum") String examNum, @Param("itemCode") String itemCode);

    List<CriticalDTO> queryCriticalDatasourseByItemCode(@Param("examNum") String examNum, @Param("itemCode") String itemCode);

    List<CriticalDTO> queryCriticalDatasourseByPacsReqCode(@Param("examNum") String examNum, @Param("pacsReqCode") String pacsReqCode);

    List<ExaminfoChargingItemDTO> queryExaminfoChargingItemDTO(@Param("examNum") String examNum, @Param("centerNum") String centerNum);

    List<SampleExamDetailDTO> querySampleExamDetailDTO(@Param("examNum") String examNum, @Param("sampleId") Long sampleId);

    List<CenterConfigurationDTO> getCenterconfigByKey(@Param("configKey") String configKey);

    //queryExamCriticalDetail


    /*
     * @Description: 根据体检编号查询顾客类型，类型编码
     * @Author: ZCZ
     * @Date: 2023/10/18 15:46
     * @Params: [examNum]
     * @Return: java.util.List<tphy.peis.entity.dto.CustomerTypeDTO>
     **/
    List<CustomerTypeDTO> getcustomerTypeList(@Param("examNum") String examNum);

    /**
     * 公共检查明细表批量插入
     *
     * @param commonExamDetailDTO
     * @return
     */
    Integer insertCommonExamDetail(@Param(value = "commonExamDetailList") List<CommonExamDetailDTO> commonExamDetailDTO);

    /**
     * 根据体检编号查询体检信息  exam_info_id
     * @param examNum
     * @return
     */
    @Select("SELECT id FROM exam_info WHERE exam_num = #{examNum}")
    String getExamInfoId(@Param("examNum") String examNum);

    /**
     * 根据科室名称查询 科室ID  科室编码
     * @param depName
     * @return
     */
    @Select("SELECT top 1 id,dep_num FROM department_dep WHERE dep_name = #{depName}")
    Map getDeptNum(@Param("depName") String depName);

    /**
     * 批量插入科室结论
     * @param examdepResult
     * @return
     */
    Integer insertExamDeptResult(@Param(value = "examdepResultList")List<ExamdepResult>  examdepResult);

    /**
     * 根据小项名称  查询小项编码信息
     * @param itemName
     * @return
     */
    @Select("SELECT top 1 id,item_num FROM examination_item WHERE item_name = #{itemName }")
    Map getItemCodeByName(@Param("itemName") String itemName);

    /**
     * 根据大项名称  查询大项信息  （目前内科外科暂时根据科室名称传参，后续扩展传参需更改）
     * @param itemName
     * @return
     */
    @Select("SELECT top 1 id,item_code FROM charging_item WHERE item_name = #{itemName }")
    Map getChargingItemCode(@Param("itemName") String itemName);

    /**
     * 根据体检编号 和 大项编码 更新项目已检 状态
     * @param examNum
     * @param chargingItemCode
     */
    @Update("UPDATE examinfo_charging_item set exam_status ='Y' where exam_num = #{examNum} and charging_item_Code=#{chargingItemCode}")
    void updateExamStatus(@Param("examNum") String examNum, @Param("chargingItemCode") String chargingItemCode);

}

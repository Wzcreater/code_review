package com.tphy.peis.mapper.peisReport;

import com.tphy.peis.entity.dto.*;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Qualifier;

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
    /**
     * 根据体检编号查询体检信息(部分信息)  queryExamResultByExamNum
     * @param examNum
     * @return
     */
    List<DepExamResultDTO> queryExamResultByExamNum(@Param("examNum") String examNum);

    List<DepExamResultDTO> queryExamResultByExamNum1(@Param("examNum") String examNum);

    List<DepExamResultDTO> queryExamResultByExamNumInView(@Param("examNum") String examNum);

    List<DepExamResultDTO> queryExamResultByExamNumInView1(@Param("examNum") String examNum);
    /**
     * 根据体检编号查询公共体检项目  queryExamResultByExamNumInView1
     * @param examNum
     * @return
     */

    List<CommonExamDetailDTO> queryCommonExamDetail(@Param("examNum") String examNum, @Param("itemCode") String itemCode);

    List<CriticalDTO> queryCriticalDetail(@Param("examNum") String examNum, @Param("itemCode") String itemCode);

    List<CriticalDTO> queryCriticalDatasourseByItemCode(@Param("examNum") String examNum, @Param("itemCode") String itemCode);

    List<CriticalDTO> queryCriticalDatasourseByPacsReqCode(@Param("examNum") String examNum, @Param("pacsReqCode") String pacsReqCode);

    List<ExaminfoChargingItemDTO> queryExaminfoChargingItemDTO(@Param("examNum") String examNum, @Param("centerNum") String centerNum);

    List<SampleExamDetailDTO> querySampleExamDetailDTO(@Param("examNum") String examNum, @Param("sampleId") Long sampleId);

    List<CenterConfigurationDTO> getCenterconfigByKey(@Param("configKey") String configKey);


    /**
     * @Description: 根据体检编号查询顾客类型，类型编码
     * @Author: ZCZ
     * @Date: 2023/10/18 15:46
     * @Params: [examNum]
     * @Return: java.util.List<tphy.peis.entity.dto.CustomerTypeDTO>
     */
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


    /**
     * 通过体检编号获取患者姓名
     * @param examNum
     * @return
     */
    @Select("select  top 1 * from  exam_info a join  customer_info b on a.customer_id = b. id  where a.exam_num = #{examNum }")
    List<Map<String,Object>> getNameByExamNum(@Param("examNum") String examNum);

    /**
     * 获取项目小项默认值
     * @param item_num
     * @return
     */
    @Select({"select exam_result,item_category from examination_item ei LEFT JOIN item_result_lib irb on ei.default_value = irb.id WHERE item_num =#{item_num} and isActive ='Y'"})
    Map<String, String> getDefaultResult(@Param("item_num") String item_num);


    @Select({"    select distinct  CAST(u.id AS VARCHAR) AS id,u.chi_name from user_usr u,exam_user e where u.id = e.user_id and u.is_active = 'Y' and e.center_num='20201100037001' and u.id in (select d.user_id from dep_user d where d.dep_id = (SELECT top 1 id from            department_dep WHERE dep_name = #{dep_name}    )) order by u.chi_name"})
    List<Map<String, String>> getDepDoctors(@Param("dep_name") String dep_name);

    @Select({"select chi_name from user_usr where id = #{id}"})
    String getDocNameById(@Param("id") String id);
}

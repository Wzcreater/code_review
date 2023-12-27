package com.tphy.peis.mapper.peisReport;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tphy.peis.entity.dto.MedicalHistoryItemDetailsResult;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName MedicalHistoryMapper
 * @Description 病史数据库Mapper
 * @Date 2023-10-16
 * @Version 1.0
 **/
@Mapper
public interface MedicalHistoryMapper extends BaseMapper<MedicalHistoryItemDetailsResult> {
    /**
     *   查询病史项目详情 queryMedicalHistoryDetail
     * @param
     * @return
     */
    List<Map<String,Object>> queryMedicalHistoryDetail();


    /**
     * 根据exam_num以及item_details_id查询
     * @param medicalHistoryItemDetailsResults
     * @return
     */
    @Select("select top 1 * from medical_history_item_details_result " +
            "where exam_num = #{exam_num} and item_details_id = #{item_details_id}")
    MedicalHistoryItemDetailsResult selectCheckItemDetailsResult(MedicalHistoryItemDetailsResult medicalHistoryItemDetailsResults);

    @Insert("INSERT INTO [dbo].[medical_history_item_details_result] ([item_details_sum]," +
            " [item_details_text], [item_details_id], [exam_num], [item_id]) VALUES (#{item_details_sum},#{item_details_text}, #{item_details_id}, #{exam_num},#{item_id});")
    Integer insertResult(MedicalHistoryItemDetailsResult medicalHistoryItemDetailsResults);

    @Update("UPDATE [dbo].[medical_history_item_details_result] SET [item_details_sum] = #{item_details_sum}, [item_details_text] = #{item_details_text}, [item_details_id] = #{item_details_id}, [exam_num] = #{exam_num}, [item_id] = #{item_id} WHERE [id] = #{id};")
    Integer updateResult(MedicalHistoryItemDetailsResult medicalHistoryItemDetailsResults);

    @Select("SELECT * from medical_history_item_details_result where exam_num = #{exam_num}")
    List<MedicalHistoryItemDetailsResult> getDetailsResult(@Param("exam_num") String exam_num);
}

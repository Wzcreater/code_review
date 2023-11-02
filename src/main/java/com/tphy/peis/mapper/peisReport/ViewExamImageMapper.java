package com.tphy.peis.mapper.peisReport;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName ViewExamImageMapper
 * @Description Pacs回传图片Mapper
 * @Date 2023-10-28
 * @Version 1.0
 **/
@Mapper
public interface ViewExamImageMapper {
    /**
     * 根据体检号和reqCode查询回传图片路径 queryImagePathByExamNumAndReqCode
     * @param examNum
     * @return
     */
    @Select("SELECT vimg.image_path from view_exam_image vimg WHERE vimg.exam_num = #{examNum} and vimg.pacs_req_code = #{reqCode}")
    List<String> queryImagePathByExamNumAndReqCode(@Param("examNum") String examNum, @Param("reqCode") String reqCode);

    /**
     * 调用触发器进行图片路径存储 procReportAutoSave
     * @param
     * @return
     */
    Boolean procReportAutoSave(@Param("patientid")String patientid,
                               @Param("summaryId")String summaryId,
                               @Param("exandevice")String exandevice,
                               @Param("ReportDoctor")String ReportDoctor,
                               @Param("dateNow")String dateNow,
                               @Param("ReportFinding")String ReportFinding,
                               @Param("ReportDignosis")String ReportDignosis,
                               @Param("updTime")String updTime);



    @Select("SELECT p.summary_id from pacs_detail p WHERE p.examinfo_num =#{examNum} and p.pacs_req_code=#{reqCode}")
    List<String> querySummaryIdByExamNumAndReqCode(@Param("examNum") String examNum, @Param("reqCode") String reqCode);

    String queryUpdTimeByExamNumAndReqCode(@Param("examNum") String examNum, @Param("reqCode") String reqCode);

    Boolean saveUpdTimeAndImgPath(@Param("updTime")String updTime, @Param("pathNow")String pathNow, @Param("examNum")String patientId, @Param("reqCode")String inHospitalNum);
}

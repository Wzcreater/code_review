package com.tphy.peis.mapper.peisReport;

import com.tphy.peis.entity.dto.PacsItemDTO;
import com.tphy.peis.entity.dto.PacsSqdInfo;
import com.tphy.peis.entity.dto.PeisToPacsGetReportDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface PeisApplyToPacsMapper {

    List<PacsItemDTO> getPacsItems(@Param("examNum") String examNum);


    Map<String,Object> getChargingItem(@Param("examNum")String examNum,@Param("chargingItemCode")String chargingItemCode);

    @Delete("delete from examinfo_charging_item_trans where exam_num = #{examNum} and charging_item_id = #{chargingItemId}")
    boolean deleteExamItemTrans(@Param("examNum") String examNum,@Param("chargingItemId") String chargingItemId);

    @Insert("insert into examinfo_charging_item_trans (exam_num,charging_item_id,charging_item_code,trans_status,trans_time) values(#{examNum},#{chargingItemId},#{chargingItemCode},1,getDate())")
    boolean insertExamItemTrans(@Param("examNum") String examNum,
                                @Param("chargingItemId") String chargingItemId,
                                @Param("chargingItemCode") String chargingItemCode);


    @Select("SELECT patientName, sex, birthday, hisId, admIdIss, admId, accessionNo, applyDepartmentStr, applyDoctor, applyHospital, applyHospitalFk, Modality, checkItemName, extNo\n" +
            "FROM v_tphy_pacs_sqd_info where accessionNo = #{accessionNo} and admId =#{admId} ")
    List<PacsSqdInfo> getPacsSqdhInfo(@Param("accessionNo") String accessionNo,@Param("admId") String admId);

    Integer updateItemStatus(@Param("accessionNo") String accessionNo, @Param("admId") String admId);

    Integer insertPacsResutl(PeisToPacsGetReportDTO peisToPacsGetReportDTO);

    Integer updateItemStatusToY(@Param("accessionNo") String accessionNo, @Param("admId") String admId);

    @Select("SELECT count(1) from pacs_result where exam_num =#{admId} and req_no =#{accessionNo}")
    Integer getPacsResultCount(PeisToPacsGetReportDTO peisToPacsGetReportDTO);

    Integer updatePacsResutl(PeisToPacsGetReportDTO peisToPacsGetReportDTO);
}

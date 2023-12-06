package com.tphy.peis.mapper.peisReport;

import com.tphy.peis.entity.dto.PacsItemDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}

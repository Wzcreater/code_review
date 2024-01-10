package com.tphy.peis.mapper.peisReport;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.tphy.peis.entity.dto.PacsDetailDTO;
import com.tphy.peis.entity.dto.ThirdEcgApplyDTO;
import com.tphy.peis.entity.dto.ThirdEcgResultDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @InterfaceName: ThirdECGMapper
 * @Description: 三方ECG申请数据
 * @Date: 2024/1/4 21:08
 * @Author: ZCZ
 **/
@Mapper
public interface ThirdECGMapper {

    /**
     * 获取心电图申请信息
     *
     * @return
     */
    @DS("ds1")
    List<ThirdEcgApplyDTO> getPacsEcgData();


    HashMap<String, String> saveEcgData(HashMap<String, String> hashMap);

    /**
     * 保存心电图结论数据
     *
     * @param pacsEcgResult
     * @param thirdEcgApplyDTO
     * @return
     */
    int savePacsResultEcg(@Param("pacsEcgResult") ThirdEcgResultDTO pacsEcgResult, @Param("thirdEcgApplyDTO") ThirdEcgApplyDTO thirdEcgApplyDTO);

    /**
     * 获取心电图三方返回数据
     *
     * @param exam_num
     * @param reqNo
     * @return
     */
    @DS("ds3")
    ThirdEcgResultDTO getPacsEcgResult(@Param("examNum") String exam_num, @Param("reqNo") String reqNo);


    /**
     * 获取心电图申请 项目信息
     *
     * @param exam_num
     * @param itemCode
     * @return
     */
    List<PacsDetailDTO> getPacsdetailData(@Param("examNum") String exam_num, @Param("itemCode") String itemCode);
}

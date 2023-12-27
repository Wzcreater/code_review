package com.tphy.peis.service;

import com.tphy.peis.entity.dto.MedicalHistoryItemDetailsResult;
import com.tphy.peis.entity.vo.SfxpdyVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName PacsGetReportMapper
 * @Description Pacs拿取体检各项信息Mapper
 * @Date 2023-10-16
 * @Version 1.0
 **/
@Mapper
@Qualifier("secondaryDataSource")
public interface MedicalHistoryService {


    Set<List<Map<String, Object>>> getMedicalHistoryDetail();

    int saveMedicalHistoryDetails(List<MedicalHistoryItemDetailsResult> medicalHistoryItemDetailsResults);

    List<MedicalHistoryItemDetailsResult> getDetailsResult(String exam_num);
}

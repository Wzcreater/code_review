package com.tphy.peis.service;

import com.tphy.peis.entity.dto.CommonExamDetailDTO;
import com.tphy.peis.entity.dto.ExaminfoChargingItemDTO;
import com.tphy.peis.entity.dto.CenterConfigurationDTO;
import com.tphy.peis.entity.dto.DepExamResultDTO;

import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName JxhsxswhService
 * @Description 导检单回收Service
 * @Date 2023/9/2
 * @Version 1.0
 **/

public interface DjdhsService {

    public List<DepExamResultDTO> getAcceptanceItemResult(String exam_num, String IS_EXAM_RESULT_CITEM);

    List<ExaminfoChargingItemDTO> queryWjxmExamInfo(String examNum, String centerNum);

    CenterConfigurationDTO getCenterconfigByKey(String paramString1, String centerNum);


    void insertCommonExamDetail(List<CommonExamDetailDTO> commonExamDetailDTOList);

    List<Map<String,Object>> getNameByExamNum(String examNum);

}

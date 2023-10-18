package tphy.peis.service;

import tphy.peis.entity.dto.CenterConfigurationDTO;
import tphy.peis.entity.dto.DepExamResultDTO;
import tphy.peis.entity.dto.ExaminfoChargingItemDTO;

import java.util.List;

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
}

package com.tphy.peis.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tphy.peis.conf.reponse.ResponseData;
import com.tphy.peis.conf.reponse.SuccessResponseData;
import com.tphy.peis.mapper.peisReport.PeisHisReportMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName
 * @Description 向His提供数据 接口Controller
 * @Date 2023-09-04
 * @Version 1.0
 **/
@RestController
@RequestMapping(value = "peis/hisReport")
public class PeiHisReportController {

    @Resource
    public PeisHisReportMapper peisHisReportMapper;

    @GetMapping("getChargeInfo")
    public ResponseData getDoctors(@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime) {
        List<Map<Object, Object>> itemChargeWithinTime = peisHisReportMapper.getItemChargeWithinTime(startTime, endTime);
        return new SuccessResponseData(itemChargeWithinTime);
    }
}

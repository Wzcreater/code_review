package com.tphy.peis.controller;

import com.tphy.peis.conf.reponse.ErrorResponseData;
import com.tphy.peis.conf.reponse.ResponseData;
import com.tphy.peis.conf.reponse.SuccessResponseData;
import com.tphy.peis.entity.dto.CommonExamDetailDTO;
import com.tphy.peis.entity.dto.MedicalHistoryItemDetailsResult;
import com.tphy.peis.entity.dto.SfxpdyDTO;
import com.tphy.peis.entity.vo.SfxpdyVO;
import com.tphy.peis.service.MedicalHistoryService;
import com.tphy.peis.service.SfxpdyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName
 * @Description 病史页面Controller
 * @Date 2023-12-26
 * @Version 1.0
 **/
@RestController
@RequestMapping("/peis/medicalHistory")
public class MedicalHistoryController {

    @Autowired
    MedicalHistoryService medicalHistoryService;

    @GetMapping("/getMedicalHistoryDetail")
    public ResponseData getMedicalHistoryDetail() {
        try {
            Set<List<Map<String, Object>>> medicalHistoryDetail = medicalHistoryService.getMedicalHistoryDetail();
            // 返回处理后的数据
            return new SuccessResponseData(medicalHistoryDetail);
        } catch (Exception e) {
            // 处理异常情况
            return new ErrorResponseData("获取失败");
        }
    }
    @PostMapping("/saveMedicalHistoryDetails")
    public ResponseData saveMedicalHistoryDetails(@RequestBody List<MedicalHistoryItemDetailsResult> medicalHistoryItemDetailsResults) {
        try {
            System.out.println(medicalHistoryItemDetailsResults);
            int count = medicalHistoryService.saveMedicalHistoryDetails(medicalHistoryItemDetailsResults);
            // 返回处理后的数据
            if (count>0){
                return new SuccessResponseData("保存成功");
            }else{
                return new SuccessResponseData("保存失败");

            }
        } catch (Exception e) {
            // 处理异常情况
            return new ErrorResponseData("保存异常");
        }
    }

    /**
     * 获取指定体检号的所有结果
     * @param examNum
     * @return
     */
    @GetMapping("/getDetailsResult")
    public ResponseData getDetailsResult(@RequestParam("examNum")String examNum) {
        try {
            List<MedicalHistoryItemDetailsResult> medicalHistoryItemDetailsResults = medicalHistoryService.getDetailsResult(examNum);
            // 返回处理后的数据
            return new SuccessResponseData(medicalHistoryItemDetailsResults);
        } catch (Exception e) {
            // 处理异常情况
            return new ErrorResponseData("获取失败");
        }
    }
}

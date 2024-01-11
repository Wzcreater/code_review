package com.tphy.peis.controller;

import com.tphy.peis.service.ThirdECGService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ThirdECGController
 * @Description: 三方心电图数据处理
 * @Date: 2024/1/4 21:07
 * @Author: ZCZ
 **/
@RestController
@RequestMapping(value = "peis/ecg")
@Slf4j
public class ThirdECGController {
    @Autowired
    private ThirdECGService thirdECGService;


    //@Scheduled(fixedDelayString = "${spring.scheduled.ecgSave}")
    @GetMapping("ecgData")
    public void ecgData() {
        try {
            thirdECGService.dealEcgData();
//            JsonUtils.objectToJson("response", "200", "获取结果成功", thirdECGService.dealEcgData());
        } catch (Exception e) {
            log.error("Error fetching ECG data: {}", e.getMessage(), e);
//            JsonUtils.objectToJson("response", "500", "获取结果出现异常", "");
        }
    }
}

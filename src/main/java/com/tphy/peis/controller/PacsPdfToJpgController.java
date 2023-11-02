package com.tphy.peis.controller;

import com.tphy.peis.conf.reponse.ResponseData;
import com.tphy.peis.conf.reponse.SuccessResponseData;
import com.tphy.peis.service.PacsPdfToJpgService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName
 * @Description Pacs系统pdf转jpg Controller
 * @Date 2023-10-28
 * @Version 1.0
 **/
@Transactional
@Slf4j
@RestController
@RequestMapping(value = "pacs/pacsPdfToJpg")
public class PacsPdfToJpgController {

    @Autowired
    PacsPdfToJpgService pacsPdfToJpgService;


    @Scheduled(fixedDelayString = "${spring.scheduled.pdfToJpg}")
    @GetMapping("pdfToJpg")
    public ResponseData pdfToJpg() throws IOException {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("开始转换pdf，当前时间：" + formattedTime);
        Integer imgs = pacsPdfToJpgService.pacsPdfToJpg();
        log.info("成功转换"+imgs+"张图片");
        return new SuccessResponseData("成功转换"+imgs+"张图片");
    }
}
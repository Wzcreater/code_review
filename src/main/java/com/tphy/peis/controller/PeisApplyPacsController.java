package com.tphy.peis.controller;

import cn.hutool.json.JSONUtil;
import com.tphy.peis.conf.reponse.ErrorResponseData;
import com.tphy.peis.conf.reponse.ResponseData;
import com.tphy.peis.conf.reponse.SuccessResponseData;
import com.tphy.peis.entity.dto.PacsItemDTO;
import com.tphy.peis.service.PacsPdfToJpgService;
import com.tphy.peis.service.PeisApplyToPacsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName
 * @Description 体检系统加项减项时向Pacs系统发送请求 Controller
 * @Date 2023-11-09
 * @Version 1.0
 **/
@Transactional
@Slf4j
@RestController
@RequestMapping(value = "peiToPacs")
public class PeisApplyPacsController {

    @Autowired
    PeisApplyToPacsService peisApplyToPacsService;
    /**
     * 加项
     */
    @PostMapping("applyToAdd")
    public ResponseData applyToAdd(@RequestBody String examNum) throws IOException {
        log.info(examNum);
        Boolean ifAdd =  peisApplyToPacsService.applyToAdd(examNum);
        if(!ifAdd){
            return new SuccessResponseData("体检号 "+examNum+" 存在项目申请Pacs失败，请查看日志");
        }
        return new SuccessResponseData("Pacs申请成功");
    }
    /**
     * 减项
     */
    @GetMapping("applyToMinus")
    public ResponseData applyToMinus() throws IOException {
        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log.info("开始转换pdf，当前时间：" + formattedTime);
        return new SuccessResponseData("成功转换");
    }

    @PostMapping("getTest")
    public ResponseData getTest(@RequestBody PacsItemDTO pacsItemDTO,@RequestHeader(value = "Authorization", required = false) String token){
        System.out.println("token: "+token);
        if (token == null || token.isEmpty() ||!token.equals("Bearer eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsImV4cCI6MTY5OTQxODgyMn0.AsqfHt9bN7rhWAdG5-JBpD2OALlOgBVySFjmb83nguw")) {
            // Token is not present in the headers, return an error response
            return new ErrorResponseData("Missing or empty 'token' header");
        }
        System.out.println(pacsItemDTO.toString());
        return new SuccessResponseData("成功转换"+pacsItemDTO.toString());
    }

    @PostMapping("accessToken/create")
    public ResponseData accessToken(@RequestBody String secret){

        System.out.println(secret);
        String secret1 = (String) JSONUtil.parseObj(secret).get("secret");
        Map<String,String> map = new HashMap<>();
        map.put("token","Bearer eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsImV4cCI6MTY5OTQxODgyMn0.AsqfHt9bN7rhWAdG5-JBpD2OALlOgBVySFjmb83nguw");
        if(secret1.equals("G1lZzw4efMfjCwvAo2LWnLGwVQXb8VrnPb3WdvDBZ4/QQeFCCBWZy7puZTd4DAEhb7LH+clh8RjYrwoX7qP4j3DsuA4SNWrOc9/ophq/upU0J/K5cwRcRUY1MK6o9TqbNlpOUba7zx1Q1klBXGR1Dnd56EK0u/7vF4ZNJqUc4go=")){
            return new SuccessResponseData(map);
        }
        return new SuccessResponseData("获取失败");
    }



}

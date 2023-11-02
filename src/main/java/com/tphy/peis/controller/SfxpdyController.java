package com.tphy.peis.controller;

import com.tphy.peis.conf.reponse.ErrorResponseData;
import com.tphy.peis.conf.reponse.ResponseData;
import com.tphy.peis.conf.reponse.SuccessResponseData;
import com.tphy.peis.entity.dto.SfxpdyDTO;
import com.tphy.peis.entity.vo.SfxpdyVO;
import com.tphy.peis.service.SfxpdyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName
 * @Description 收费小票打印Controller
 * @Date 2023-10-30
 * @Version 1.0
 **/
@RestController
@RequestMapping("/peis/sfxpdy")
public class SfxpdyController {

    @Autowired
    SfxpdyService service;
    @PostMapping("/getInfo")
    public ResponseData getInfo(@RequestBody SfxpdyDTO params) {
        System.out.println(params.getExamNum()+params.getItemCodeList());
        try {
            SfxpdyVO sfxpdyVO = service.getInfo(params.getExamNum(),params.getItemCodeList());
            // 在这里编写你的业务逻辑，使用 examNum 和 itemCodeList 参数
            // 例如，可以查询数据库或执行其他操作
            // 这里只是一个示例，你需要根据你的业务逻辑进行编写

            // 假设你有一个方法来获取相关信息
           // String result = yourService.getInfo(examNum, itemCodeList);

            // 返回处理后的数据
            return new SuccessResponseData(sfxpdyVO);
        } catch (Exception e) {
            // 处理异常情况
            return new ErrorResponseData("获取失败");
        }
    }
}

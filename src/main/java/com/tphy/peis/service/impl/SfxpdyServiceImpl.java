package com.tphy.peis.service.impl;


import com.tphy.peis.entity.vo.SfxpItemDetail;
import com.tphy.peis.entity.vo.SfxpdyVO;
import com.tphy.peis.mapper.peisReport.SfxpdyMapper;
import com.tphy.peis.service.SfxpdyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName JxHsxswhServiceImpl
 * @Description 导检单回收ServiceImpl
 * @Date 2023/9/4
 * @Version 1.0
 **/
@Service
@Slf4j
public class SfxpdyServiceImpl implements SfxpdyService{
    @Autowired
    SfxpdyMapper sfxpdyMapper;


    @Override
    public SfxpdyVO getInfo(String examNum, List<String> itemCodeList) {

        SfxpdyVO sfxpdyVO = new SfxpdyVO();
        //个人体检项目明细
        List<Map<String, Object>> maps = sfxpdyMapper.queryPacsItemsByExamNum(examNum);

        //个人体检明细
        List<Map<String, Object>> maps1 = sfxpdyMapper.queryPacsPInfoByExamNum(examNum);

        //System.out.println(maps1.size());
        if(!ObjectUtils.isEmpty(maps1)){
            //拿到最后一次个人体检信息
            Map<String, Object> pInfoMap = maps1.get(maps1.size() - 1);
            //System.out.println(pInfoMap.get("exam_num").toString()+pInfoMap.get("age").toString());
            sfxpdyVO.setAge(pInfoMap.get("age").toString());
            sfxpdyVO.setDysj("111");
            sfxpdyVO.setTjh(pInfoMap.get("exam_num").toString());
            sfxpdyVO.setTjhz(pInfoMap.get("name").toString());
            sfxpdyVO.setAge(pInfoMap.get("age").toString());
            sfxpdyVO.setSex(pInfoMap.get("sex").toString());
            sfxpdyVO.setTjdw(pInfoMap.get("cardCom").toString());
            sfxpdyVO.setCzy(/*pInfoMap.get("age")*/"张立华");
            sfxpdyVO.setKye(pInfoMap.get("cardAmount").toString());
            sfxpdyVO.setTjkh(pInfoMap.get("cardNum").toString());
            // 获取当前时间
            Date currentDate = new Date();

            // 创建一个格式化模式
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            // 使用格式化模式将日期转换为字符串
            String formattedDate = dateFormat.format(currentDate);
            sfxpdyVO.setDysj(formattedDate);
        }

        Double sum =0.00d;
        List<SfxpItemDetail> sfxpItemDetails = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            for (String itemCode : itemCodeList) {
                String item_code = map.get("item_code").toString();
                //System.out.println(item_code);
                if(itemCode.equals(item_code)){
                    String amount = map.get("amount").toString();
                    //System.out.println(amount);
                    sum += Double.parseDouble(amount);
                    SfxpItemDetail sfxpItemDetail = SfxpItemDetail.builder()
                            .itemname(map.get("item_name").toString())
                            .number(map.get("itemnum").toString())
                            .price(map.get("item_amount").toString())
                            .zk(map.get("discount").toString())
                            .zkprice(amount)
                            .build();
                    sfxpItemDetails.add(sfxpItemDetail);
                }
            }
        }
        sfxpdyVO.setZjg(String.format("%.2f", sum));
        sfxpdyVO.setSfxpItemDetails(sfxpItemDetails);
        return sfxpdyVO;
    }
}

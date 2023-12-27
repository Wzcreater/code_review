package com.tphy.peis.service.impl;


import com.tphy.peis.entity.dto.MedicalHistoryItemDetailsResult;
import com.tphy.peis.entity.vo.SfxpItemDetail;
import com.tphy.peis.entity.vo.SfxpdyVO;
import com.tphy.peis.mapper.peisReport.MedicalHistoryMapper;
import com.tphy.peis.mapper.peisReport.SfxpdyMapper;
import com.tphy.peis.service.MedicalHistoryService;
import com.tphy.peis.service.SfxpdyService;
import com.tphy.peis.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.util.*;

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
public class MedicalHistoryServiceImpl implements MedicalHistoryService {
    @Autowired
    MedicalHistoryMapper medicalHistoryMapper;


    @Override
    public Set<List<Map<String, Object>>>  getMedicalHistoryDetail() {
        List<Map<String, Object>> medicalHistoryDetail = medicalHistoryMapper.queryMedicalHistoryDetail();
        Set<List<Map<String, Object>>> set = new TreeSet<>(new Comparator<List<Map<String, Object>>>() {
            @Override
            public int compare(List<Map<String, Object>> list1, List<Map<String, Object>> list2) {
                // Compare by item_queue_number
                Integer itemQueueNumber1 = (Integer) list1.get(0).get("item_queue_number");
                Integer itemQueueNumber2 = (Integer) list2.get(0).get("item_queue_number");
                int result = Integer.compare(itemQueueNumber1, itemQueueNumber2);
                return result;
            }
        });
        List<Map<String, Object>> tmpList= new ArrayList<>();
        String tmLastItem = medicalHistoryDetail.get(0).get("item_name").toString();
        for (Map<String, Object> stringObjectMap : medicalHistoryDetail) {
            if(ObjectUtils.isEmpty(stringObjectMap.get("item_detail_group_describe"))){
                stringObjectMap.put("item_detail_group_describe","");
            }
            if(stringObjectMap.get("item_name").toString().equals(tmLastItem)){
                tmpList.add(stringObjectMap);
            }else{
                set.add(tmpList);
                tmpList = new ArrayList<>();
                tmLastItem = stringObjectMap.get("item_name").toString();
                tmpList.add(stringObjectMap);
            }
        }
        set.add(tmpList);
        return set;
    }

    @Override
    public int saveMedicalHistoryDetails(List<MedicalHistoryItemDetailsResult> medicalHistoryItemDetailsResults) {
        int count = 0;
        for (MedicalHistoryItemDetailsResult medicalHistoryItemDetailsResult : medicalHistoryItemDetailsResults) {
            MedicalHistoryItemDetailsResult result1 = new MedicalHistoryItemDetailsResult();
            //复选框类型
            if(!ObjectUtils.isEmpty(medicalHistoryItemDetailsResult.item_details_sum)
                    ||(!ObjectUtils.isEmpty(medicalHistoryItemDetailsResult.item_details_text))){
                result1 = medicalHistoryMapper.selectCheckItemDetailsResult(medicalHistoryItemDetailsResult);
            }
            //为空则插入表，不为空则更新表
            if(ObjectUtils.isEmpty(result1)){
                count = medicalHistoryMapper.insertResult(medicalHistoryItemDetailsResult);
            }else{
                medicalHistoryItemDetailsResult.setId(result1.getId());
                count =medicalHistoryMapper.updateResult(medicalHistoryItemDetailsResult);
            }
        }
        return count;
    }

    @Override
    public List<MedicalHistoryItemDetailsResult> getDetailsResult(String exam_num) {
        List<MedicalHistoryItemDetailsResult> results = medicalHistoryMapper.getDetailsResult(exam_num);
        return results;
    }
}

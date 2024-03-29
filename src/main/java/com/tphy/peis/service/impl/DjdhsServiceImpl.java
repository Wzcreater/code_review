package com.tphy.peis.service.impl;


import com.tphy.peis.entity.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.tphy.peis.mapper.peisReport.DjdhsMapper;
import com.tphy.peis.service.DjdhsService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class DjdhsServiceImpl implements DjdhsService {

    @Resource
    DjdhsMapper djdhsMapper;

    @Override
    public List<DepExamResultDTO> getAcceptanceItemResult(String examNum, String IS_EXAM_RESULT_CITEM) {
        List<DepExamResultDTO> list = new ArrayList<>();
        List<DepExamResultDTO> commonList = djdhsMapper.queryExamResultByExamNum(examNum);
        for (DepExamResultDTO common : commonList) {
            if (!StringUtils.isEmpty(common.getItem_num())) {
                List<CommonExamDetailDTO> comdetailList = djdhsMapper.queryCommonExamDetail(examNum, common.getItem_num());
                if (comdetailList.size() != 0) {
                    common.setHealth_level(((CommonExamDetailDTO) comdetailList.get(0)).getHealth_level());
                    common.setExam_result(((CommonExamDetailDTO) comdetailList.get(0)).getExam_result());
                    common.setExam_doctor(((CommonExamDetailDTO) comdetailList.get(0)).getExam_doctor());
                } else {
                    common.setExam_date("");
                }
                List<CriticalDTO> criticalList = djdhsMapper.queryCriticalDetail(examNum, common.getItem_num());
                if (criticalList.size() != 0) {
                    common.setCritical_id(((CriticalDTO) criticalList.get(0)).getId());
                    common.setData_source(((CriticalDTO) criticalList.get(0)).getData_source());
                }

            }
        }
        list.addAll(commonList);
        List<DepExamResultDTO> viewList1 = djdhsMapper.queryExamResultByExamNumInView(examNum);
        List<DepExamResultDTO> viewList = djdhsMapper.queryExamResultByExamNumInView1(examNum);
        viewList.addAll(viewList1);
        if (viewList1.size() > 0) {
            for (DepExamResultDTO vList : viewList1) {
                if ("检查结论".equals(vList.getItem_name())) {
                    List<CriticalDTO> criticalList = djdhsMapper.queryCriticalDatasourseByPacsReqCode(vList.getExam_num(), vList.getReq_id());
                    if (criticalList.size() != 0) {
                        vList.setCritical_id(((CriticalDTO) criticalList.get(0)).getId());
                        vList.setData_source(((CriticalDTO) criticalList.get(0)).getData_source());
                    }
                }
            }
        }
        list.addAll(viewList);
        List<DepExamResultDTO> examList = djdhsMapper.queryExamResultByExamNum1(examNum);
        if (examList.size() > 0) {
            for (DepExamResultDTO lislist : examList) {
                List<CriticalDTO> criticalList = djdhsMapper.queryCriticalDatasourseByItemCode(lislist.getExam_num(), lislist.getItem_num());
                if (criticalList.size() != 0) {
                    lislist.setCritical_id(((CriticalDTO) criticalList.get(0)).getId());
                    lislist.setData_source(((CriticalDTO) criticalList.get(0)).getData_source());
                }
            }
        }
        list.addAll(examList);
        return list;
    }

    @Override
    public List<ExaminfoChargingItemDTO> queryWjxmExamInfo(String examNum, String centerNum) {
        List<ExaminfoChargingItemDTO> list = djdhsMapper.queryExaminfoChargingItemDTO(examNum, centerNum);
        for (ExaminfoChargingItemDTO examinfoitem : list) {
            //131为检验科
            if ("131".equals(examinfoitem.getDep_category())) {
                List<SampleExamDetailDTO> listsample = djdhsMapper.querySampleExamDetailDTO(examinfoitem.getExam_num(), examinfoitem.getSample_id());
                if (listsample.size() > 0) {
                    examinfoitem.setSample_status(((SampleExamDetailDTO) listsample.get(0)).getStatus());
                    examinfoitem.setSample_statuss(((SampleExamDetailDTO) listsample.get(0)).getStatus_y());
                }
            }
        }
        return list;
    }

    @Override
    public CenterConfigurationDTO getCenterconfigByKey(String configKey, String centerNum) {
        // 在中心配置表中根据传过来的key查询
        List<CenterConfigurationDTO> list = djdhsMapper.getCenterconfigByKey(configKey);
        // 如果list为空则配置库缺少该配置
        if (list == null || list.size() == 0) {
            System.out.println("配置库 center_configuration 缺少 " + configKey + " 配置！！！");
            return null;
        }
        if (centerNum == null) {
            return list.get(0);
        }
        // 遍历list
        for (CenterConfigurationDTO ccd : list) {
            if (centerNum.equals(ccd.getCenter_num())) {
                return ccd;
            }
            if ("000".equals(ccd.getCenter_num())) {
                return ccd;
            }
            if (StringUtils.isEmpty(ccd.getCenter_num())) {
                return ccd;
            }
        }
        System.out.println("配置库 center_configuration " + configKey + " 配置center_num错误！！！");
        return null;
    }

    @Override
    public void insertCommonExamDetail(List<CommonExamDetailDTO> commonExamDetailDTOList) {
        Date currentTime = new Date();
        // 将时间转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(currentTime);


        // 分离数据
        List<CommonExamDetailDTO> departmentConclusionList = new ArrayList<>();
        List<CommonExamDetailDTO> otherDataList = new ArrayList<>();

        for (CommonExamDetailDTO conclusion : commonExamDetailDTOList) {
            if ("科室结论".equals(conclusion.getItem_name()) || "专家建议".equals(conclusion.getItem_name())) {
                departmentConclusionList.add(conclusion);
            } else {
                otherDataList.add(conclusion);
            }
        }


        // 处理科室结论
        List<ExamdepResult> examdepResultList = new ArrayList<>();
        List<CommonExamDetailDTO> list1 = departmentConclusionList.stream()
                .filter(item -> "科室结论".equals(item.getItem_name()))
                .collect(Collectors.toList());

        List<CommonExamDetailDTO> list2 = departmentConclusionList.stream()
                .filter(item -> "专家建议".equals(item.getItem_name()))
                .collect(Collectors.toList());
        String doc_name;
        String exam_doctor_id;
        for (int i = 0; i < list1.size(); i++) {
            CommonExamDetailDTO item1 = list1.get(i);
            CommonExamDetailDTO item2 = list2.get(i);

            if (item1.getDep_name().equals(item2.getDep_name())) {

                if (item1.getExam_result() == null) {
                    item1.setExam_result("");
                }
                if (item2.getExam_result() == null) {
                    item2.setExam_result("");
                }
                ExamdepResult examdepResult = new ExamdepResult();
                String examInfoId = djdhsMapper.getExamInfoId(item1.getExam_num());
                examdepResult.setExam_info_id(Long.parseLong(examInfoId));
                doc_name = item1.getExam_doctor();
                if (!doc_name.equals("管理员")) {
                    exam_doctor_id = item1.getExam_doctor();
                    doc_name = this.djdhsMapper.getDocNameById(exam_doctor_id);
                }
                examdepResult.setExam_doctor(doc_name);
                Map deptNum = djdhsMapper.getDeptNum(item1.getDep_name());
                examdepResult.setDep_id(deptNum.get("id").toString());
                examdepResult.setDep_num(deptNum.get("dep_num").toString());
                examdepResult.setExam_result_summary(item1.getExam_result());
                examdepResult.setSuggestion(item2.getExam_result());
                examdepResult.setCenter_num("20201100037001");
                examdepResult.setApprover(0);
                examdepResult.setCreater(0);
                examdepResult.setCreate_time(formattedDate);
                examdepResult.setApp_type("1");
                examdepResult.setExam_num(item1.getExam_num());
                examdepResultList.add(examdepResult);
            }
        }
        // 处理公共检查细项
        ArrayList<CommonExamDetailDTO> commonExamDetails = new ArrayList<>();
        for (CommonExamDetailDTO commonExamDetailDTO : otherDataList) {
            if (commonExamDetailDTO.getExam_result() == null) {
                commonExamDetailDTO.setExam_result("");
            }
            String examInfoId = djdhsMapper.getExamInfoId(commonExamDetailDTO.getExam_num());
            commonExamDetailDTO.setExam_info_id(Long.parseLong(examInfoId));
            Map itemCodeByName = djdhsMapper.getItemCodeByName(commonExamDetailDTO.getItem_name());
            commonExamDetailDTO.setItem_code(itemCodeByName.get("item_num").toString());
            commonExamDetailDTO.setExam_item_id(itemCodeByName.get("id").toString());
            String dep_name =commonExamDetailDTO.getDep_name();
            if(dep_name.equals("眼科")){
                dep_name = "眼科检查";
            }
            if(dep_name.equals("妇科")){
                dep_name = "妇科检查";
            }
            if(dep_name.equals("口腔科")){
                dep_name.equals("口腔检查");
            }
            Map chargingItemCode = djdhsMapper.getChargingItemCode(dep_name);
            commonExamDetailDTO.setCharging_item_id(chargingItemCode.get("id").toString());
            commonExamDetailDTO.setCharging_item_code(chargingItemCode.get("item_code").toString());
            doc_name = commonExamDetailDTO.getExam_doctor();
            if (!doc_name.equals("管理员")) {
                exam_doctor_id = commonExamDetailDTO.getExam_doctor();
                doc_name = this.djdhsMapper.getDocNameById(exam_doctor_id);
            }
            commonExamDetailDTO.setExam_doctor(doc_name);
            commonExamDetailDTO.setCenter_num("20201100037001");
            commonExamDetailDTO.setHealth_level("Z");
            commonExamDetailDTO.setExam_result_back(commonExamDetailDTO.getExam_result());
            commonExamDetailDTO.setExam_date(formattedDate);
            commonExamDetailDTO.setCreate_time(formattedDate);
            commonExamDetails.add(commonExamDetailDTO);
        }


        // 科室结论插入
        Integer conclusionFlag = djdhsMapper.insertExamDeptResult(examdepResultList);
        // 检查细项插入
        Integer insertCommonFlag = djdhsMapper.insertCommonExamDetail(commonExamDetails);

        // 根据体检编号和大项 更新大项状态为已检
        List<String> chargingItemCodeList = commonExamDetails.stream().map(CommonExamDetailDTO::getCharging_item_code)
                .distinct()
                .collect(Collectors.toList());

        if (conclusionFlag > 0 && insertCommonFlag > 0) {
            for (String chargingItemCode : chargingItemCodeList) {
                djdhsMapper.updateExamStatus(commonExamDetails.get(0).getExam_num(), chargingItemCode);
            }
        }

    }

    /*
     * @Description: 通过体检编号获取患者姓名
     * @Author: ZCZ
     * @Date: 2023/10/19 20:07
     * @Params: [examNum]
     * @Return: java.lang.String
     **/
    @Override
    public List<Map<String,Object>> getNameByExamNum(String examNum) {
        return djdhsMapper.getExamInfoByExamNum(examNum);
    }
}

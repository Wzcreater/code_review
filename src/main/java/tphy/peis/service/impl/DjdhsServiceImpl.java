package tphy.peis.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tphy.peis.entity.dto.*;
import tphy.peis.mapper.DjdhsMapper;
import tphy.peis.service.DjdhsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
                    List<CriticalDTO> criticalList = djdhsMapper.queryCriticalDatasourseByPacsReqCode(vList.getExam_num(),vList.getReq_id());
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
                List<CriticalDTO> criticalList = djdhsMapper.queryCriticalDatasourseByItemCode(lislist.getExam_num(),lislist.getItem_num());
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
                List<SampleExamDetailDTO> listsample = djdhsMapper.querySampleExamDetailDTO(examinfoitem.getExam_num(),examinfoitem.getSample_id());
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
        List<CenterConfigurationDTO> list =djdhsMapper.getCenterconfigByKey(configKey);
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
}

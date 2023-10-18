package tphy.peis.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import tphy.peis.conf.reponse.ResponseData;
import tphy.peis.conf.reponse.SuccessResponseData;
import tphy.peis.entity.dto.CommonExamDetailDTO;
import tphy.peis.entity.dto.DepExamResultDTO;
import tphy.peis.entity.dto.ExaminfoChargingItemDTO;
import tphy.peis.mapper.DjdhsMapper;
import tphy.peis.service.DjdhsService;
import tphy.peis.util.StringUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName
 * @Description  导检单回收Controller
 * @Date 2023-09-04
 * @Version 1.0
 **/
@RestController
@RequestMapping(value="peis/djdhs")
public class DjdhsController {
    @Resource
    private DjdhsMapper djdhsMapper;
    @Resource
    private DjdhsService djdhsService;
    /**
     * 批量手动录入
     */
    @GetMapping("plsdlr")
    public ResponseData plsdlr(String examNum){

        List<DepExamResultDTO> depExamResultDTOS = djdhsMapper.queryExamResultByExamNum(examNum);
        return new SuccessResponseData(depExamResultDTOS);
    }
    /**
     * 检查细项
     */
    @GetMapping("getJcxx")
    public ResponseData getJcxx(String examNum){

        List<DepExamResultDTO> jcxx = djdhsService.getAcceptanceItemResult(examNum, "Y");
        return new SuccessResponseData(jcxx);
    }
    /**
     * 未检项目
     */
    @GetMapping("getWjxm")
    public ResponseData getWjxm(String examNum) {

        List<ExaminfoChargingItemDTO> list = djdhsService.queryWjxmExamInfo(examNum, "20201100037001");
//结束回收页面未检收费项目列表，配置默认排在前边的收费项目.配置收费项目ID，以 , 隔开
        String examEndItemSeq = djdhsService.getCenterconfigByKey("EXAM_END_ITEM_SEQ", "20201100037001").getConfig_value();
        String[] ids = examEndItemSeq.split(",");
        List<ExaminfoChargingItemDTO> temp = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (StringUtil.isDouble(ids[i])) {
                for (ExaminfoChargingItemDTO itemDto : list) {
                    if (itemDto.getCharge_item_id() == Long.valueOf(ids[i]).longValue()) {
                        itemDto.setIsActive("yes");
                        temp.add(itemDto);
                    }
                }
            }
        }
        list.removeAll(temp);
        //所有未检项目
        temp.addAll(list);
        //所有检查细项
        List<DepExamResultDTO> jcxx = djdhsService.getAcceptanceItemResult(examNum, "Y");
        //所有未检项目
        List<ExaminfoChargingItemDTO> wjxm = new ArrayList<>();

        for (ExaminfoChargingItemDTO examinfoChargingItemDTO : temp){
            for (DepExamResultDTO depExamResultDTO : jcxx) {
                if(examinfoChargingItemDTO.getDep_name().equals(depExamResultDTO.getKsmc())){
                    if(wjxm.isEmpty()){
                        wjxm.add(examinfoChargingItemDTO);
                        break;
                    }
                    if(!wjxm.get(wjxm.size()-1).getDep_name().equals(examinfoChargingItemDTO.getDep_name())){
                        wjxm.add(examinfoChargingItemDTO);
                    }
                    break;
                }
            }
        }

        return new SuccessResponseData(wjxm);
    }
    /**
     * 未检细项
     */
    @GetMapping("getWjxx")
    public ResponseData getWjxx(String examNum){

        List<ExaminfoChargingItemDTO> list = djdhsService.queryWjxmExamInfo(examNum, "20201100037001");
//结束回收页面未检收费项目列表，配置默认排在前边的收费项目.配置收费项目ID，以 , 隔开
        String examEndItemSeq = djdhsService.getCenterconfigByKey("EXAM_END_ITEM_SEQ", "20201100037001").getConfig_value();
        String[] ids = examEndItemSeq.split(",");
        List<ExaminfoChargingItemDTO> temp = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (StringUtil.isDouble(ids[i])) {
                for (ExaminfoChargingItemDTO itemDto : list) {
                    if (itemDto.getCharge_item_id() == Long.valueOf(ids[i]).longValue()) {
                        itemDto.setIsActive("yes");
                        temp.add(itemDto);
                    }
                }
            }
        }

        list.removeAll(temp);
        //所有未检项目
        temp.addAll(list);
        //所有检查细项
        List<DepExamResultDTO> jcxx = djdhsService.getAcceptanceItemResult(examNum, "Y");
        //所有未检项目的检查细项
        List<DepExamResultDTO> wjxx = new ArrayList<>();

        for (DepExamResultDTO depExamResultDTO : jcxx) {
            for (ExaminfoChargingItemDTO examinfoChargingItemDTO : temp) {
                if(depExamResultDTO.getKsmc().equals(examinfoChargingItemDTO.getDep_name())){
                    wjxx.add(depExamResultDTO);
                    break;
                }
            }
        }
        return new SuccessResponseData(wjxx);
    }
//    addexamDepResult.action



    @PostMapping("insert")
    public ResponseData saveCommonExamDetails(@RequestBody  List<CommonExamDetailDTO> commonExamDetailDTOList) throws JsonProcessingException {
      djdhsService.insertCommonExamDetail(commonExamDetailDTOList);
      return null;
    }
}

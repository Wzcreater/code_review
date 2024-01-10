package com.tphy.peis.service.impl;


import com.tphy.peis.entity.dto.PacsDetailDTO;
import com.tphy.peis.entity.dto.ThirdEcgApplyDTO;
import com.tphy.peis.entity.dto.ThirdEcgResultDTO;
import com.tphy.peis.mapper.peisReport.ThirdECGMapper;
import com.tphy.peis.service.ThirdECGService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: ThirdECGService
 * @Description: 心电图三方对接处理 心电图
 * @Date: 2024/1/4 22:25
 * @Author: ZCZ
 **/
@Slf4j
@Service
public class ThirdECGServiceImpl implements ThirdECGService {
    @Autowired
    private ThirdECGMapper thirdECGMapper;

    /**
     * 处理心电图数据
     *
     * @return 处理结果提示信息
     */
    @Override
    public String dealEcgData() {
        log.info("处理心电图数据开始------------");
        //查询心电图申请的数据
        List<ThirdEcgApplyDTO> pacsEcgData = thirdECGMapper.getPacsEcgData();
        String errorMsg = null;
        for (ThirdEcgApplyDTO pacsEcgDatum : pacsEcgData) {
            processEachEcgApply(pacsEcgDatum);
        }
        if (ObjectUtils.isEmpty(pacsEcgData)) {
            return "暂无申请患者"; // 结果为空或报告URL为空，直接返回
        }
        log.info("--------------处理心电图数据结束");
        return errorMsg;
    }

    /**
     * 处理每条心电图申请记录
     *
     * @param pacsEcgDatum 心电图申请记录
     */
    private void processEachEcgApply(ThirdEcgApplyDTO pacsEcgDatum) {
        String patientId = pacsEcgDatum.getPATIENT_ID();
        String noteNo = pacsEcgDatum.getNOTE_NO();

        // 根据心电图申请的患者信息 查询心电图结果信息
        ThirdEcgResultDTO pacsEcgResult = thirdECGMapper.getPacsEcgResult(patientId, noteNo);
        if (pacsEcgResult == null || StringUtils.isEmpty(pacsEcgResult.getREPORT_URL())) {
            log.info("申请编号为{}的患者：{}心电图报告url为空", noteNo, patientId);
            return; // 结果为空或报告URL为空，直接返回
        }
        // 下载并处理结果报告
        handleEcgResultReports(pacsEcgDatum, pacsEcgResult);
    }

    /**
     * 处理心电图结果报告
     *
     * @param pacsEcgDatum  心电图数据
     * @param pacsEcgResult 心电图结果
     */
    private void handleEcgResultReports(ThirdEcgApplyDTO pacsEcgDatum, ThirdEcgResultDTO pacsEcgResult) {
        List<PacsDetailDTO> pacsDetailData = thirdECGMapper.getPacsdetailData(pacsEcgDatum.getPATIENT_ID(),
                pacsEcgDatum.getITEM_CODE());

        for (PacsDetailDTO pacsDetailDatum : pacsDetailData) {
            HashMap<String, String> paramMap = createParamMap(pacsEcgDatum, pacsEcgResult, pacsDetailDatum);

            if (thirdECGMapper.savePacsResultEcg(pacsEcgResult, pacsEcgDatum) > 0) {
                try {
                    thirdECGMapper.saveEcgData(paramMap);
                    // 存储执行成功，下载心电图url图片至本地
                    downloadEcgReport(pacsDetailDatum.getPacsReqCode(), pacsEcgResult.getREPORT_URL(), pacsEcgDatum.getPATIENT_ID());
                } catch (Exception e) {
                    log.error("保存心电图数据异常: {}", e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 创建请求参数Map
     *
     * @return 参数Map
     */
    private HashMap<String, String> createParamMap(ThirdEcgApplyDTO pacsEcgDatum, ThirdEcgResultDTO pacsEcgResult,
                                                   PacsDetailDTO pacsDetailDatum) {
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("exam_num", pacsEcgDatum.getPATIENT_ID());
        paramMap.put("item_code", pacsDetailDatum.getSummaryId());
        paramMap.put("study_type", "ECG");
        paramMap.put("check_doct", pacsEcgDatum.getDOCT_NAME());
        paramMap.put("check_date", pacsEcgResult.getREPORT_DATETIME());
        paramMap.put("Diagnose", pacsEcgResult.getCONCLUSIONS());
        paramMap.put("check_desc", pacsEcgResult.getCONCLUSIONS());
        paramMap.put("error_code", "");
        return paramMap;
    }

    /**
     * 下载心电图报告
     */
    private void downloadEcgReport(String reqCode, String reportUrl, String patientId) {
        String patientDirectory = saveFile(patientId);
        String outputFile = patientDirectory + File.separator + reqCode + ".jpg";

        try (InputStream in = new URL(reportUrl).openStream()) {
            Files.copy(in, Paths.get(outputFile), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("下载心电图报告异常: {}", e.getMessage(), e);
        }
    }


    /*
     * @Description: 组织下载图片路径
     * @Author: ZCZ
     * @Date: 2024/1/5 10:00
     * @Params: [patientId]
     * @Return: java.lang.String
     **/
    public String saveFile(String patientId) {
        try {
            Resource resource = new ClassPathResource("url.properties");
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            String outputBaseDirectory = properties.getProperty("pacsReportImgPath");
            Path outputDir = Paths.get(outputBaseDirectory);
            Path todayDir = outputDir.resolve(new SimpleDateFormat("yyyyMMdd").format(new Date()));
            Path exandeviceDir = todayDir.resolve("ECG");
            Path patientDir = exandeviceDir.resolve(patientId);
            Files.createDirectories(patientDir);
            return patientDir.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error creating directories", e);
        }
    }
}

package com.tphy.peis.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.util.StringUtils;
import com.tphy.peis.entity.dto.PacsItemDTO;
import com.tphy.peis.entity.dto.PacsSqdInfo;
import com.tphy.peis.entity.dto.PeisToPacsGetReportDTO;
import com.tphy.peis.entity.dto.PeisToPacsSqdjDTO;
import com.tphy.peis.mapper.peisReport.DjdhsMapper;
import com.tphy.peis.mapper.peisReport.PeisApplyToPacsMapper;
import com.tphy.peis.mapper.peisReport.ViewExamImageMapper;
import com.tphy.peis.service.PacsPdfToJpgService;
import com.tphy.peis.service.PeisApplyToPacsService;
import com.tphy.peis.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Copyright (C) 2023  北京天鹏恒宇科技发展有限公司 版权所有
 * Copyright (C) 2023  TPHY.Co.,Ltd.  All rights reserved
 *
 * @Author wangzhen
 * @ClassName PeisApplyToPacsServiceImpl
 * @Description peis体检系统提供给pacs的对接接口ServiceImpl
 * @Date 2023/01/11
 * @Version 1.0
 **/
@Service
@Slf4j
public class PeisApplyToPacsServiceImpl implements PeisApplyToPacsService {
    @Resource
    ViewExamImageMapper viewExamImageMapper;
    @Resource
    PeisApplyToPacsMapper peisApplyToPacsMapper;
    @Resource
    DjdhsMapper djdhsMapper;
    @Override
    public Boolean applyToAdd(String examNum) {
        examNum = (String) JSONUtil.parseObj(examNum).get("examNum");
        Boolean insertFlag = true;
        String url = "http://192.168.88.29:1314/frontend/accessToken/create";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("secret", "BnoyKWy04sJ5LvnXZ+/YA9rTCf6/kYI5rirE6ENcFmC+cuuLPAVPti6miCC8P=");
        HttpResponse response = HttpRequest.post(url)
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .execute();
        JSONObject jsonResult = new JSONObject(response.body());
        String token = jsonResult.getJSONObject("data").getStr("token");
        System.out.println(token);
        List<PacsItemDTO> items = peisApplyToPacsMapper.getPacsItems(examNum);
        String insertUrl = "http://192.168.88.29:1314/frontend/accessToken/create";

        for (PacsItemDTO item : items) {
            //item.setStatus("0");
            String jsonPayload = JSONUtil.toJsonStr(item);
            // 创建HTTP请求对象并设置负载和URL
            HttpResponse response1 = HttpRequest.post(url)
                    .header("Content-Type", "application/json")
                    .header("Authorization", token)
                    .body(jsonPayload)
                    .execute();

            JSONObject insertResult = new JSONObject(response.body());
            String isInsert = insertResult.getStr("data");
            if (isInsert.equals("成功")) {
                peisApplyToPacsMapper.deleteExamItemTrans(item.getPeisNo(), item.getItemId());
                peisApplyToPacsMapper.insertExamItemTrans(item.getPeisNo(), item.getItemId(), item.getItemCode());
            } else {
                log.info(item.getItemName() + "项目 " + item.getItemCode() + " 插入失败");
                insertFlag = false;
            }
        }
        return insertFlag;
    }

    /**
     * pacs登记后调用peis系统修改项目登记状态
     *
     * @param peisToPacsSqdjDTO
     * @return
     */
    @Override
    public String updateStatus(PeisToPacsSqdjDTO peisToPacsSqdjDTO) {

        String status = peisToPacsSqdjDTO.getStatus();
        String admIdIss = peisToPacsSqdjDTO.getAdmIdIss();
        if (!status.equals("REGISTERED")) {
            return "不是登记状态,登记失败-error";
        }
        if (!admIdIss.equals("2")) {
            return "不是体检来源,登记失败-error";
        }
        List<PacsSqdInfo> sqdInfos = peisApplyToPacsMapper.getPacsSqdhInfo(peisToPacsSqdjDTO.getAccessionNo(), peisToPacsSqdjDTO.getAdmId());
        if (ObjectUtils.isEmpty(sqdInfos)) {
            return "申请单号未查询到相关数据-error";
        }
        int size = sqdInfos.size();

        Integer updateCount = peisApplyToPacsMapper.updateItemStatus(peisToPacsSqdjDTO.getAccessionNo(), peisToPacsSqdjDTO.getAdmId());

        if (updateCount <= 0) {
            return "成功更新0条登记状态-error";
        }
        return "成功更新登记状态-ok";
    }

    @Override
    public String postReport(PeisToPacsGetReportDTO peisToPacsGetReportDTO) throws IOException {

        String examNum = peisToPacsGetReportDTO.getAdmId();
        //判断是否已终检，如果已终检则返回已终检
        List<Map<String, Object>> examInfoByExamNum = djdhsMapper.getExamInfoByExamNum(examNum);
        if(examInfoByExamNum.get(0).get("status").equals("Z")){
            return "推送报告失败,当前体检人已终检-error";
        }
        String pacsReqCode = peisToPacsGetReportDTO.getAccessionNo();
        String modalityName = peisToPacsGetReportDTO.getModalityName();
        //要保存的图片信息 pdf
        String reportPath = peisToPacsGetReportDTO.getReportPath();
        if (StringUtils.isEmpty(reportPath)) {
            return "推送报告失败,报告路径为空-error";
        }

        URL url;
        InputStream in;
        try {
            url = new URL(reportPath);
            log.info("pdfPath:{}", reportPath);
            log.info("url:{}", url);
            in = url.openStream();
        } catch (FileNotFoundException e) {
            // 处理文件未找到的情况，例如记录日志或其他操作
            System.err.println("文件未找到：" + reportPath + e.getMessage());
            log.error("文件未找到:{},{}", reportPath, e.getMessage());
            return "文件未找到，path：" + reportPath + "-error";
        }

        PDDocument document;
        document = PDDocument.load(in);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        // PDF文件的总页数
        int totalPage = 0;
        int sum = 0;

        Properties properties = new Properties();
        InputStream input = PacsPdfToJpgServiceImpl.class.getClassLoader().getResourceAsStream("url.properties");
        properties.load(input);
        String outputBaseDirectory = properties.getProperty("pacsReportImgPath");
        SimpleDateFormat dateNow = new SimpleDateFormat("yyyyMMdd");
        String todayDate = dateNow.format(new Date());
        //如果path不为空说明已经插了这个报告回传图片

        String exandeviceDirectory = outputBaseDirectory + File.separator + todayDate + File.separator + modalityName;
        File exandeviceDir = new File(exandeviceDirectory);
        if (!exandeviceDir.exists()) {
            exandeviceDir.mkdirs();
        }
        String patientDirectory = exandeviceDirectory + File.separator + examNum;
        File patientDir = new File(patientDirectory);
        log.info("patientDir:{}", patientDir);
        if (!patientDir.exists()) {
            patientDir.mkdirs();
        }
        String summary_id =null;
        List<String> summary = viewExamImageMapper.querySummaryIdByExamNumAndReqCode(examNum, pacsReqCode);
        if(summary.isEmpty()){
            log.info(examNum+"  summary_id为空："+pacsReqCode);
        }else{
            summary_id = summary.get(0);
        }
        List<String> paths = viewExamImageMapper.queryImagePathByExamNumAndReqCode(examNum, pacsReqCode);
        //如果存在以前版本的报告路径，先删除后添加
        if(!ObjectUtils.isEmpty(paths)){
            Integer integer = viewExamImageMapper.deleteImagePathByExamNumAndReqCode(examNum, pacsReqCode);
            log.info(examNum+" 删除原版本报告成功："+pacsReqCode);
        }
        totalPage = document.getNumberOfPages();
        for (int i = 0; i < totalPage; i++) {
            String fixPath ="_"+i;

            File outputFile = new File(patientDirectory, pacsReqCode + "_" + i + ".jpg");
            // 渲染当前页面为BufferedImage对象
            BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 300);
            boolean ifWrite = ImageIO.write(bim, "jpg", outputFile);
            //如果写入图片到文件夹成功，则修改图片路径
            String pathNow = "/pacs_img/" + todayDate + "/" + modalityName + "/" + examNum + "/" + pacsReqCode + "_" + i + ".jpg";
            if (ifWrite) {

                Boolean aBoolean = viewExamImageMapper.procReportAutoSave(examNum, summary_id,
                        modalityName, peisToPacsGetReportDTO.doctorNameR, todayDate, peisToPacsGetReportDTO.getResult(), peisToPacsGetReportDTO.getEyesee(), null,fixPath);
                sum++;
                if(aBoolean){
                    log.info("插入图片成功：" + pathNow);
                }else{
                    log.info("插入图片路径失败：" + pathNow);
                }
            } else {
                log.info("插入图片失败：" + pathNow);
            }
        }
        if (sum != totalPage) {
            log.info("插入报告图片缺失：" + examNum);
        }
        document.close();
        //检查结论存在则修改，不存在则更新
        Integer count;
        Integer has = peisApplyToPacsMapper.getPacsResultCount(peisToPacsGetReportDTO);
        if(has>0){
            count = peisApplyToPacsMapper.updatePacsResutl(peisToPacsGetReportDTO);
        }else{
            count= peisApplyToPacsMapper.insertPacsResutl(peisToPacsGetReportDTO);
        }
        if (count <= 0) {
            return "推送报告失败-error";
        }
        // 推送成功，将项目修改为已检查状态
        peisApplyToPacsMapper.updateItemStatusToY(pacsReqCode,examNum);

        return "成功推送报告-ok";
    }
}

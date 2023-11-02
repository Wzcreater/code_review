package com.tphy.peis.service.impl;


import com.tphy.peis.entity.dto.*;
import com.tphy.peis.mapper.pacsReport.PacsPdfToJpgMapper;
import com.tphy.peis.mapper.peisReport.DjdhsMapper;
import com.tphy.peis.mapper.peisReport.ViewExamImageMapper;
import com.tphy.peis.service.DjdhsService;
import com.tphy.peis.service.PacsPdfToJpgService;
import com.tphy.peis.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
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
public class PacsPdfToJpgServiceImpl implements PacsPdfToJpgService {

    @Resource
    PacsPdfToJpgMapper pacsPdfToJpgMapper;
    @Resource
    ViewExamImageMapper viewExamImageMapper;
    @Override
    public Integer pacsPdfToJpg() throws IOException {
        Properties properties = new Properties();
        InputStream input = PacsPdfToJpgServiceImpl.class.getClassLoader().getResourceAsStream("url.properties");
        properties.load(input);

        List<Map<String, String>> reportData = pacsPdfToJpgMapper.getReportData();
        int sum = 0;
        String outputBaseDirectory = properties.getProperty("pacsReportImgPath");
        SimpleDateFormat dateNow = new SimpleDateFormat("yyyyMMdd");
        String todayDate = dateNow.format(new Date());

        try {
            for (Map<String, String> report : reportData) {
                String exandevice = report.get("exandevice");
                String patientId = report.get("patientid");
                String inHospitalNum = report.get("inhospitalnum");
                String pdfPath = report.get("f_pdf_path");
                List<String> paths = viewExamImageMapper.queryImagePathByExamNumAndReqCode(patientId,inHospitalNum);

                List<String> summary = viewExamImageMapper.querySummaryIdByExamNumAndReqCode(patientId, inHospitalNum);

                //如果path不为空说明已经插了这个报告回传图片
                if(!ObjectUtils.isEmpty(paths)){
                    log.info(patientId+"体检号 已有图片"+  paths.get(0));
                    continue;
                }
                if(ObjectUtils.isEmpty(summary)||summary.get(0).equals("")){
                    //log.info("体检号："+patientId+" reqCode："+inHospitalNum+"  没有对应summaryId");
                    //continue;
                }
                pdfPath= "http://123.57.5.73:809/zhyy/task/2023-10-27/9161819391dc4af1bdae63461f77f996.pdf";
                URL url = new URL(pdfPath);
                InputStream in = url.openStream();
                PDDocument document = PDDocument.load(in);
                PDFRenderer pdfRenderer = new PDFRenderer(document);

                String exandeviceDirectory = outputBaseDirectory + File.separator + todayDate + File.separator + exandevice;
                File exandeviceDir = new File(exandeviceDirectory);
                if (!exandeviceDir.exists()) {
                    exandeviceDir.mkdirs();
                }

                String patientDirectory = exandeviceDirectory + File.separator + patientId;
                File patientDir = new File(patientDirectory);
                if (!patientDir.exists()) {
                    patientDir.mkdirs();
                }

                File outputFile = new File(patientDirectory, inHospitalNum + ".jpg");
                BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);
                ImageIO.write(bim, "jpg", outputFile);
                String reportDoctor = report.get("ReportDoctor");
                String reportFinding = report.get("ReportFinding");
                String reportDignosis = report.get("ReportDignosis");
                /*viewExamImageMapper.procReportAutoSave(patientId,summary.get(0),
                        exandevice,reportDoctor,todayDate,reportFinding,reportDignosis);*/
                sum++;
                document.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

}

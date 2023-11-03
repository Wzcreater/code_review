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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
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

    @Value("${spDate.time}")
    Integer date = 3;
    @Resource
    PacsPdfToJpgMapper pacsPdfToJpgMapper;
    @Resource
    ViewExamImageMapper viewExamImageMapper;
    @Override
    public Integer pacsPdfToJpg() throws IOException {
        Properties properties = new Properties();
        InputStream input = PacsPdfToJpgServiceImpl.class.getClassLoader().getResourceAsStream("url.properties");
        properties.load(input);

        List<Map<String, String>> reportData = pacsPdfToJpgMapper.getReportData(date);
        log.info("reportData size:{}",String.valueOf(reportData.size()));
        int sum = 0;
        String outputBaseDirectory = properties.getProperty("pacsReportImgPath");
        SimpleDateFormat dateNow = new SimpleDateFormat("yyyyMMdd");
        String todayDate = dateNow.format(new Date());
        log.info("outputBaseDirectory:{}", outputBaseDirectory);
        log.info("dateNow:{}", dateNow);
        log.info("todayDate:{}", todayDate);
        PDDocument document = null;
            for (Map<String, String> report : reportData) {
                String exandevice = report.get("exandevice");
                String patientId = report.get("patientid");
                String inHospitalNum = report.get("inhospitalnum");
                String updTime = report.get("upd_time");
                String approveDate = report.get("ApproveDate");
                String pdfPath = report.get("f_pdf_path");
              try {

//                pdfPath= "http://123.57.5.73:809/zhyy/task/2023-10-23/61c6b625b5154e07addf87b767b3d696.pdf";
                List<String> paths = viewExamImageMapper.queryImagePathByExamNumAndReqCode(patientId,inHospitalNum);
                log.info("paths:{}",paths);
                log.info("patientId:{}",patientId);
                log.info("inHospitalNum:{}",inHospitalNum);
                List<String> summary = viewExamImageMapper.querySummaryIdByExamNumAndReqCode(patientId, inHospitalNum);
                log.info("summary:{}",summary);
                if(ObjectUtils.isEmpty(summary)||summary.get(0).equals("")){
                    log.info("体检号："+patientId+" reqCode："+inHospitalNum+"  没有对应summaryId");
                    continue;
                }
                log.info("继续：{}",patientId);
                URL url;
                InputStream in;
                try{
                    url = new URL(pdfPath);
                    log.info("pdfPath:{}",pdfPath);
                    log.info("url:{}",url);
                    in= url.openStream();
                }catch (FileNotFoundException e) {
                    // 处理文件未找到的情况，例如记录日志或其他操作
                    System.err.println("文件未找到："+pdfPath + e.getMessage());
                    log.error("文件未找到:{},{}",pdfPath,e.getMessage());
                    continue; // 跳过当前报告的处理，继续下一个报告)
                }
                 document = PDDocument.load(in);
                PDFRenderer pdfRenderer = new PDFRenderer(document);

                //如果path不为空说明已经插了这个报告回传图片
                if(!ObjectUtils.isEmpty(paths)){
                    //log.info(patientId+"体检号 已有图片"+  paths.get(0));
                    //如果审核时间不等于update时间说明发了两版以上的pacs报告，就需要判断是否需要更新peis系统的对应报告了 +".0" 是因为时间戳
                    log.info("approveDate:{}",approveDate);
                    log.info("updTime:{}",updTime);
                    if(!approveDate.equals(updTime)){
                        //同样通过体检号和req_code 拿到viewExamImage的表中pacs_report_update 字段，做判断。
                        String peisUpdTime =  viewExamImageMapper.queryUpdTimeByExamNumAndReqCode(patientId, inHospitalNum);
                        log.info("peisUpdTime：{}",peisUpdTime);
                        //  如果不相等则版本不同步 ,需要下载图片并更新图片路径
                        if(ObjectUtils.isEmpty(peisUpdTime)||!(updTime+".0").equals(peisUpdTime)){
                            log.info(paths.get(0)+" 最新报告时间"+updTime+"------"+" 现存报告时间"+peisUpdTime);
                            String exandeviceDirectory = outputBaseDirectory + File.separator + todayDate + File.separator + exandevice;
                            log.info("exandeviceDirectory:{}",exandeviceDirectory);
                            File exandeviceDir = new File(exandeviceDirectory);
                            log.info("exandeviceDir:{}",exandeviceDir);
                            if (!exandeviceDir.exists()) {
                                exandeviceDir.mkdirs();
                            }
                            String patientDirectory = exandeviceDirectory + File.separator + patientId;
                            log.info("patientDirectory:{}",patientDirectory);
                            File patientDir = new File(patientDirectory);
                            log.info("patientDir:{}",patientDir);
                            if (!patientDir.exists()) {
                                patientDir.mkdirs();
                            }

                            File outputFile = new File(patientDirectory, inHospitalNum + ".jpg");
                            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);
                            boolean ifWrite = ImageIO.write(bim, "jpg", outputFile);
                            //如果写入图片到文件夹成功，则修改图片路径
                            String pathNow = "/pacs_img/"+todayDate+"/"+exandevice+"/"+patientId+"/"+inHospitalNum+".jpg";
                            if(ifWrite){
                                log.info("修改-插入图片成功："+pathNow);
                                Boolean aBoolean = viewExamImageMapper.saveUpdTimeAndImgPath(updTime, pathNow, patientId, inHospitalNum);
                                if(aBoolean){
                                    sum++;
                                    log.info("修改图片路径成功："+pathNow);
                                }else {
                                    log.info("修改图片路径失败："+pathNow);
                                }
                            }else {
                                log.info("修改-插入图片失败："+pathNow);
                            }
                        }
                    }

                    continue;
                }

                String exandeviceDirectory = outputBaseDirectory + File.separator + todayDate + File.separator + exandevice;
                log.info("exandeviceDirectory:{}",exandeviceDirectory);
                File exandeviceDir = new File(exandeviceDirectory);
                log.info("exandeviceDir:{}",exandeviceDir);
                if (!exandeviceDir.exists()) {
                    exandeviceDir.mkdirs();
                }

                String patientDirectory = exandeviceDirectory + File.separator + patientId;
                log.info("patientDirectory:{}",patientDirectory);

                File patientDir = new File(patientDirectory);
                log.info("patientDir:{}",patientDir);
                if (!patientDir.exists()) {
                    patientDir.mkdirs();
                }

                File outputFile = new File(patientDirectory, inHospitalNum + ".jpg");
                log.info("outputFile:{}",outputFile);
                BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);
                boolean ifWrite = ImageIO.write(bim, "jpg", outputFile);
                log.info("ifWrite:{}",ifWrite);
                if(ifWrite){
                    log.info("保存-插入图片成功："+outputFile.getPath());

                    String reportDoctor = report.get("ReportDoctor");
                    String reportFinding = report.get("ReportFinding");
                    String reportDignosis = report.get("ReportDignosis");
                    try {
                        Boolean aBoolean = viewExamImageMapper.procReportAutoSave(patientId, summary.get(0),
                                exandevice, reportDoctor, todayDate, reportFinding, reportDignosis, updTime);
                        log.info("aBoolean:{}", aBoolean);
                        if (aBoolean) {
                            log.info("保存图片信息成功" + outputFile.getPath());
                        } else {
                            log.info("保存图片信息失败" + outputFile.getPath());
                        }
                    } catch (Exception e) {
                        log.error("保存图片信息失败：{}", e);
                    }

                } else {
                    log.info("保存-插入图片失败：" + outputFile.getPath());
                }
                  sum++;
                document.close();
            } catch (Exception e){
                  log.error("error:{}",e);
                  log.info("体检号：{}，报错",patientId);
                  document.close();
              }
            }

        return sum;
    }

}

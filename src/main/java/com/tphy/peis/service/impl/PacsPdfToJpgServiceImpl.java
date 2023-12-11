package com.tphy.peis.service.impl;


import com.tphy.peis.mapper.pacsReport.PacsPdfToJpgMapper;
import com.tphy.peis.mapper.peisReport.ViewExamImageMapper;
import com.tphy.peis.service.PacsPdfToJpgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
        log.info("reportData size:{}", String.valueOf(reportData.size()));
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
                List<String> paths = viewExamImageMapper.queryImagePathByExamNumAndReqCode(patientId, inHospitalNum);
                log.info("paths:{}", paths);
                log.info("patientId:{}", patientId);
                log.info("inHospitalNum:{}", inHospitalNum);
                List<String> summary = viewExamImageMapper.querySummaryIdByExamNumAndReqCode(patientId, inHospitalNum);
                log.info("summary:{}", summary);
                if (ObjectUtils.isEmpty(summary) || summary.get(0).equals("")) {
                    log.info("体检号：" + patientId + " reqCode：" + inHospitalNum + "  没有对应summaryId");
                    continue;
                }
                log.info("继续：{}", patientId);
                URL url;
                InputStream in;
                try {
                    url = new URL(pdfPath);
                    log.info("pdfPath:{}", pdfPath);
                    log.info("url:{}", url);
                    in = url.openStream();
                } catch (FileNotFoundException e) {
                    // 处理文件未找到的情况，例如记录日志或其他操作
                    System.err.println("文件未找到：" + pdfPath + e.getMessage());
                    log.error("文件未找到:{},{}", pdfPath, e.getMessage());
                    continue; // 跳过当前报告的处理，继续下一个报告)
                }
                document = PDDocument.load(in);
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                int pageNum = 0; // 当前要处理的页面号码
                int totalPage = 0; // PDF文件的总页数


                //如果path不为空说明已经插了这个报告回传图片
                if (!ObjectUtils.isEmpty(paths)) {
                    //log.info(patientId+"体检号 已有图片"+  paths.get(0));
                    //如果审核时间不等于update时间说明发了两版以上的pacs报告，就需要判断是否需要更新peis系统的对应报告了 +".0" 是因为时间戳
                    log.info("approveDate:{}", approveDate);
                    log.info("updTime:{}", updTime);
                    if (!approveDate.equals(updTime)) {
                        //同样通过体检号和req_code 拿到viewExamImage的表中pacs_report_update 字段，做判断。
                        String peisUpdTime = viewExamImageMapper.queryUpdTimeByExamNumAndReqCode(patientId, inHospitalNum);
                        log.info("peisUpdTime：{}", peisUpdTime);
                        //  如果不相等则版本不同步 ,需要下载图片并更新图片路径
                        if (ObjectUtils.isEmpty(peisUpdTime) || !(updTime + ".0").equals(peisUpdTime)) {
                            log.info(paths.get(0) + " 最新报告时间" + updTime + "------" + " 现存报告时间" + peisUpdTime);
                            String exandeviceDirectory = outputBaseDirectory + File.separator + todayDate + File.separator + exandevice;
                            log.info("exandeviceDirectory:{}", exandeviceDirectory);
                            File exandeviceDir = new File(exandeviceDirectory);
                            log.info("exandeviceDir:{}", exandeviceDir);
                            if (!exandeviceDir.exists()) {
                                exandeviceDir.mkdirs();
                            }
                            String patientDirectory = exandeviceDirectory + File.separator + patientId;
                            log.info("patientDirectory:{}", patientDirectory);
                            File patientDir = new File(patientDirectory);
                            log.info("patientDir:{}", patientDir);
                            if (!patientDir.exists()) {
                                patientDir.mkdirs();
                            }


                            totalPage = document.getNumberOfPages();

                            for (int i = 0; i < totalPage; i++) {

                                File outputFile = new File(patientDirectory, inHospitalNum +"_"+i+ ".jpg");
                                BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 300); // 渲染当前页面为BufferedImage对象
                                boolean ifWrite = ImageIO.write(bim, "jpg", outputFile);
                                //如果写入图片到文件夹成功，则修改图片路径
                                String pathNow = "/pacs_img/" + todayDate + "/" + exandevice + "/" + patientId + "/" + inHospitalNum +"_"+i+ ".jpg";
                                if (ifWrite) {
                                    log.info("修改-插入图片成功：" + pathNow);
                                    Boolean aBoolean = viewExamImageMapper.saveUpdTimeAndImgPath(updTime, pathNow, patientId, inHospitalNum);
                                    if (aBoolean) {
                                        sum++;
                                        log.info("修改图片路径成功：" + pathNow);
                                    } else {
                                        log.info("修改图片路径失败：" + pathNow);
                                    }
                                } else {
                                    log.info("修改-插入图片失败：" + pathNow);
                                }
                            }

                        }
                    }

                    continue;
                }

                String exandeviceDirectory = outputBaseDirectory + File.separator + todayDate + File.separator + exandevice;
                log.info("exandeviceDirectory:{}", exandeviceDirectory);
                File exandeviceDir = new File(exandeviceDirectory);
                log.info("exandeviceDir:{}", exandeviceDir);
                if (!exandeviceDir.exists()) {
                    exandeviceDir.mkdirs();
                }

                String patientDirectory = exandeviceDirectory + File.separator + patientId;
                log.info("patientDirectory:{}", patientDirectory);

                File patientDir = new File(patientDirectory);
                log.info("patientDir:{}", patientDir);
                if (!patientDir.exists()) {
                    patientDir.mkdirs();
                }



                totalPage = document.getNumberOfPages();


                for (int i = 0; i < totalPage; i++) {

                    File outputFile = new File(patientDirectory, inHospitalNum + "_"+i+".jpg");

                    String fixPath ="_"+i;
                    log.info("outputFile:{}", outputFile);


                    BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 300); // 渲染当前页面为BufferedImage对象
                    boolean ifWrite = ImageIO.write(bim, "jpg", outputFile);// 将BufferedImage写入到JPEG文件中
                    pageNum++; // 更新页面号码，指向下一页


                    log.info("ifWrite:{}", ifWrite);
                    if (ifWrite) {
                        log.info("保存-插入图片成功：" + outputFile.getPath());

                        String reportDoctor = report.get("ReportDoctor");
                        String reportFinding = report.get("ReportFinding");
                        String reportDignosis = report.get("ReportDignosis");
                        try {
//                            StringBuilder imgFileBuilder = new StringBuilder();
//                            imgFileBuilder.append("/pacs_img/");
//                            imgFileBuilder.append(todayDate);
//                            imgFileBuilder.append("/");
//                            imgFileBuilder.append(exandevice);
//                            imgFileBuilder.append("/");
//                            imgFileBuilder.append(patientId);
//                            imgFileBuilder.append("/");
//                            imgFileBuilder.append(inHospitalNum);
//                            imgFileBuilder.append("-");
//                            imgFileBuilder.append(i);
//                            imgFileBuilder.append(".jpg");
//                            String img_file = imgFileBuilder.toString();

//                            Integer integer = viewExamImageMapper.insertViewImage(img_file, patientId, inHospitalNum, approveDate);
                            Boolean aBoolean = viewExamImageMapper.procReportAutoSave(patientId, summary.get(0),
                                    exandevice, reportDoctor, todayDate, reportFinding, reportDignosis, updTime,fixPath);
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
                }
                document.close();



//                BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);
//                boolean ifWrite = ImageIO.write(bim, "jpg", outputFile);

            } catch (Exception e) {
                log.error("error:{}", e);
                log.info("体检号：{}，报错", patientId);
                document.close();
            }
        }

        return sum;
    }



    /*
     * @Description: //TODO 处理对应路径下的PDF转换
     * @Author: ZCZ
     * @Date: 2023/11/7 19:18
     * @Params: []
     * @Return: void
     **/
    @Override
    public void convertPdfInFolder() {
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String ECGpath = String.format("D:\\pic\\pacs_img\\%s\\ECG", formattedDate);
        String DTpath = String.format("D:\\pic\\pacs_img\\%s\\DT", formattedDate);
        String DCpath = String.format("D:\\pic\\pacs_img\\%s\\DC", formattedDate);
        String ECpath = String.format("D:\\pic\\pacs_img\\%s\\EC", formattedDate);

        String Testpath = "Z:\\pic";

        // 处理心电图pdf转jpg
        convertPdfInFolder(ECGpath);
        // 处理动态心电图pdf转jpg
        convertPdfInFolder(DTpath);
        // 动态血压pdf转jpg
        convertPdfInFolder(DCpath);
        // 动脉硬化pdf转jpg
        convertPdfInFolder(ECpath);

    }

    /**
     * 存衡水pacs回传图片
     * @return
     * @throws IOException
     */
    @Override
    public Integer hsinsertJpg() throws IOException {
        Properties properties = new Properties();
        InputStream input = PacsPdfToJpgServiceImpl.class.getClassLoader().getResourceAsStream("url.properties");
        properties.load(input);

        List<Map<String, Object>> reportData = viewExamImageMapper.getHsPacsReportData(date);
        log.info("reportData size:{}", String.valueOf(reportData.size()));
        int sum = 0;
        String outputBaseDirectory = properties.getProperty("pacsReportImgPath");
        SimpleDateFormat dateNow = new SimpleDateFormat("yyyyMMdd");
        String todayDate = dateNow.format(new Date());
        log.info("outputBaseDirectory:{}", outputBaseDirectory);
        log.info("dateNow:{}", dateNow);
        log.info("todayDate:{}", todayDate);
        for (Map<String, Object> report : reportData) {
            String exandevice = (String) report.get("modality");
            String patientId = (String) report.get("tjh");
            String inHospitalNum = (String) report.get("pacsreqcode");
            Date updatetime1 = (Date) report.get("updatetime");
            Date approveDate1 = (Date)report.get("finaltime");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String updTime = simpleDateFormat.format(updatetime1);
            String approveDate = simpleDateFormat.format(approveDate1);

            String pdfPath = (String) report.get("pdfurl");
            try {

//                pdfPath= "http://123.57.5.73:809/zhyy/task/2023-10-23/61c6b625b5154e07addf87b767b3d696.pdf";
                List<String> paths = viewExamImageMapper.queryImagePathByExamNumAndReqCode(patientId, inHospitalNum);
                log.info("paths:{}", paths);
                log.info("patientId:{}", patientId);
                log.info("inHospitalNum:{}", inHospitalNum);
                List<String> summary = viewExamImageMapper.querySummaryIdByExamNumAndReqCode(patientId, inHospitalNum);
                log.info("summary:{}", summary);
                if (ObjectUtils.isEmpty(summary) || summary.get(0).equals("")) {
                    log.info("体检号：" + patientId + " reqCode：" + inHospitalNum + "  没有对应summaryId");
                    continue;
                }
                //log.info("继续：{}", patientId);
                String[] split = pdfPath.split(";;");
                // PDF文件的总页数
                int totalPage = split.length;


                //如果path不为空说明已经插了这个报告回传图片
                if (!ObjectUtils.isEmpty(paths)) {
                    //log.info(patientId+"体检号 已有图片"+  paths.get(0));
                    //如果审核时间不等于update时间说明发了两版以上的pacs报告，就需要判断是否需要更新peis系统的对应报告了 +".0" 是因为时间戳
                    log.info("approveDate:{}", approveDate);
                    log.info("updTime:{}", updTime);
                    if (!approveDate.equals(updTime)) {
                        //同样通过体检号和req_code 拿到viewExamImage的表中pacs_report_update 字段，做判断。
                        String peisUpdTime = viewExamImageMapper.queryUpdTimeByExamNumAndReqCode(patientId, inHospitalNum);
                        log.info("peisUpdTime：{}", peisUpdTime);
                        //  如果不相等则版本不同步 ,需要下载图片并更新图片路径
                        if (ObjectUtils.isEmpty(peisUpdTime) || !(updTime + ".0").equals(peisUpdTime)) {
                            log.info(paths.get(0) + " 最新报告时间" + updTime + "------" + " 现存报告时间" + peisUpdTime);
                            String exandeviceDirectory = outputBaseDirectory + File.separator + todayDate + File.separator + exandevice;
                            log.info("exandeviceDirectory:{}", exandeviceDirectory);
                            File exandeviceDir = new File(exandeviceDirectory);
                            log.info("exandeviceDir:{}", exandeviceDir);
                            if (!exandeviceDir.exists()) {
                                exandeviceDir.mkdirs();
                            }
                            String patientDirectory = exandeviceDirectory + File.separator + patientId;
                            log.info("patientDirectory:{}", patientDirectory);
                            File patientDir = new File(patientDirectory);
                            log.info("patientDir:{}", patientDir);
                            if (!patientDir.exists()) {
                                patientDir.mkdirs();
                            }


                           /* totalPage = document.getNumberOfPages();*/

                            for (int i = 0; i < totalPage; i++) {

                                String outputFile = patientDirectory+"\\"+inHospitalNum +"-"+i+ ".jpg";
                                downloadImage(split[i],outputFile);
                                /*BufferedImage bim = pdfRenderer.renderImageWithDPI(i, 300); // 渲染当前页面为BufferedImage对象
                                boolean ifWrite = ImageIO.write(bim, "jpg", outputFile);*/
                                //如果写入图片到文件夹成功，则修改图片路径
                                String pathNow = "/pacs_img/" + todayDate + "/" + exandevice + "/" + patientId + "/" + inHospitalNum +"-"+i+ ".jpg";
                                /*if (ifWrite) {*/
                                    log.info("修改-插入图片成功：" + pathNow);
                                    Boolean aBoolean = viewExamImageMapper.saveUpdTimeAndImgPath(updTime, pathNow, patientId, inHospitalNum);
                                    if (aBoolean) {
                                        sum++;
                                        log.info("修改图片路径成功：" + pathNow);
                                    } else {
                                        log.info("修改图片路径失败：" + pathNow);
                                    }
                                } /*else {
                                    log.info("修改-插入图片失败：" + pathNow);
                                }
                            }*/
                        }
                    }
                    continue;
                }

                String exandeviceDirectory = outputBaseDirectory + File.separator + todayDate + File.separator + exandevice;
                log.info("exandeviceDirectory:{}", exandeviceDirectory);
                File exandeviceDir = new File(exandeviceDirectory);
                log.info("exandeviceDir:{}", exandeviceDir);
                if (!exandeviceDir.exists()) {
                    exandeviceDir.mkdirs();
                }

                String patientDirectory = exandeviceDirectory + File.separator + patientId;
                log.info("patientDirectory:{}", patientDirectory);

                File patientDir = new File(patientDirectory);
                log.info("patientDir:{}", patientDir);
                if (!patientDir.exists()) {
                    patientDir.mkdirs();
                }
                for (int i = 0; i < totalPage; i++) {

                   // File outputFile = new File(patientDirectory, inHospitalNum + );

                    String fixPath ="-"+i;
                    //log.info("outputFile:{}", outputFile);
                    String outputFile =  patientDirectory+"\\"+inHospitalNum+"-"+i+".jpg";

                    downloadImage(split[i],outputFile);

                    /*log.info("ifWrite:{}", ifWrite);
                    if (ifWrite) {*/
                        log.info("保存-插入图片成功：" + outputFile);

                        String reportDoctor = "";
                        String reportFinding = "";
                        String reportDignosis = "";
                        try {
                            Boolean aBoolean = viewExamImageMapper.procReportAutoSave(patientId, summary.get(0),
                                    exandevice, reportDoctor, todayDate, reportFinding, reportDignosis, updTime,fixPath);
                            log.info("aBoolean:{}", aBoolean);
                            if (aBoolean) {
                                log.info("保存图片信息成功" + outputFile);
                            } else {
                                log.info("保存图片信息失败" + outputFile);
                            }
                        } catch (Exception e) {
                            log.error("保存图片信息失败：{}", e);
                        }

                    /*} else {
                        log.info("保存-插入图片失败：" + outputFile.getPath());
                    }*/
                    sum++;
                }
            } catch (Exception e) {
                log.error("error:{}", e);
                log.info("体检号：{}，报错", patientId);
            }
        }

        return sum;
    }

    public static void downloadImage(String imageUrl, String destinationPath) throws IOException {
        URL url = new URL(imageUrl);

        try (InputStream in = url.openStream()) {
            Path destination = Paths.get(destinationPath);  // 使用 Paths.get
            Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /*
     * @Description: //TODO 遍历路径下的文件，转换路径下的PDF为jpg
     * @Author: ZCZ
     * @Date: 2023/11/7 19:16
     * @Params: [folderPath]
     * @Return: void
     **/
    @Override
    public void convertPdfInFolder(String folderPath) {
        File folder = new File(folderPath);
        log.info("pdf地址:{}", folderPath);
        if (folder.exists() && folder.isDirectory()) {
            // 遍历文件夹内所有的文件和子文件夹
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    convertPdfInFolder(file.getAbsolutePath()); // 递归处理子文件夹
                } else if (file.getName().toLowerCase().endsWith(".pdf")) {
                    log.info("PDF转换：{}", file.getName());
                    try {
                        convertPdfToJpg(file); // 转换 pdf 文件为 jpg
                        log.info("PDF转换为JPG成功！");
                    } catch (IOException e) {
                        log.error("PDF转换为JPG失败");
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }


    /**
     * PDF文件转换为JPG
     * @param pdfFile
     * @throws IOException
     */
    private void convertPdfToJpg(File pdfFile) throws IOException {
        // 实现 PDF 转 JPG 的具体逻辑

        if (pdfFile == null || !pdfFile.exists() || pdfFile.length() == 0) {
            throw new IllegalArgumentException("无效的PDF文件");
        }
        PDDocument document = PDDocument.load(pdfFile);

        // pdf路径名不能为空
        if (pdfFile.getAbsolutePath() != null && !pdfFile.getAbsolutePath().isEmpty()) {
            int lastDotIndex = pdfFile.getAbsolutePath().lastIndexOf(".");
            String absolutePath = pdfFile.getAbsolutePath().substring(0, lastDotIndex); // 去掉扩展名部分

            // pdf文件页面不能为空
            if (document.getNumberOfPages() == 0) {
                throw new IllegalArgumentException("PDF文件中没有页面");
            }
            try {
                PDFRenderer pdfRenderer = new PDFRenderer(document);

                // 转为jpg,多张和单张 生成名称规则不同
                if (document.getNumberOfPages() == 1) {
                    BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
                    ImageIO.write(bim, "jpg", new File(absolutePath + ".jpg"));
                } else {
                    for (int page = 0; page < document.getNumberOfPages(); ++page) {
                        BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
                        int pages = page + 1;
                        ImageIO.write(bim, "jpg", new File(absolutePath + "." + pages + ".jpg"));
                    }
                }

            } finally {
                document.close();
            }
        }
    }
}

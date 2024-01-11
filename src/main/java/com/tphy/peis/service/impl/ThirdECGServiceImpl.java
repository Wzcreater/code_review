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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
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
     * 下载心电图报告并将图片由横版转为竖版
     */
    private void downloadEcgReport(String reqCode, String reportUrl, String patientId) {
        // 存储路径
        String patientDirectory = saveFile(patientId);
        String outputFile = patientDirectory + File.separator + reqCode + ".jpg";

//        try (InputStream in = new URL(reportUrl).openStream()) {
//            Files.copy(in, Paths.get(outputFile), StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            log.error("下载心电图报告异常: {}", e.getMessage(), e);
//        }
        try {
            // 创建URL对象
            URL url = new URL(reportUrl);

            // 读取图片
            BufferedImage originalImage = ImageIO.read(url);

            // 通过旋转来创建一个新的图片以得到竖版效果
            BufferedImage rotatedImage = rotateImageByNinetyDeg(originalImage);

            // 写入旋转后的图片到输出文件
            ImageIO.write(rotatedImage, "jpg", new File(outputFile));
        } catch (IOException e) {
            log.error("下载并转换心电图报告异常: {}", e.getMessage(), e);
        }
    }

    /**
     * 将图片旋转90度
     * @param originalImage 原始横版图片
     * @return 旋转后的竖版图片
     */
    private BufferedImage rotateImageByNinetyDeg(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // 创建一个新的无图像BufferedImage对象，大小为原图高宽调换
        BufferedImage rotatedImage = new BufferedImage(height, width, originalImage.getType());

        // 创建一个新的图形上下文
        Graphics2D graphics = (Graphics2D) rotatedImage.getGraphics();

        // 创建仿射变换，先进行90度旋转（顺时针），然后平移以确保图像内容完全显示
        AffineTransform transform = new AffineTransform();
        // 注意：这里的平移操作调整为了图片的高度width（因为转了90度后，原来的宽度变成了高度）
        // 并且宽度height（因为转了90度后，原来的高度变成了宽度）
        transform.translate(height / 2.0, width / 2.0);
        transform.rotate(Math.PI / 2);
        transform.translate(-width / 2.0, -height / 2.0);

        // 将变换应用到图形上下文
        graphics.transform(transform);

        // 绘制并释放资源
        graphics.drawImage(originalImage, 0, 0, null);
        graphics.dispose();

        return rotatedImage;
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

package tphy.peis;

import cn.hutool.core.date.DateTime;
import com.tphy.peis.PeisApplication;
import com.tphy.peis.entity.dto.ExaminfoChargingItemDTO;
import com.tphy.peis.entity.dto.PacsItemDTO;
import com.tphy.peis.entity.vo.SfxpdyVO;
import com.tphy.peis.mapper.pacsReport.PacsPdfToJpgMapper;
import com.tphy.peis.mapper.pacsReport.SystemUserMapper;
import com.tphy.peis.mapper.peisReport.*;
import com.tphy.peis.service.PacsPdfToJpgService;
import com.tphy.peis.service.SfxpdyService;
import com.tphy.peis.service.impl.PacsPdfToJpgServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.tphy.peis.service.DjdhsService;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest(classes = PeisApplication.class)
class ApplicationTests {

    @Resource
    DjdhsMapper djdhsMapper;

    @Resource
    PeisApplyToPacsMapper peisApplyToPacsMapper;
    @Resource
    DjdhsService djdhsService;

    @Autowired
    SystemUserMapper systemUserMapper;
    @Autowired
    PacsPdfToJpgMapper pacsPdfToJpgMapper;
    @Autowired
    PacsPdfToJpgService pacsPdfToJpgService;
    @Autowired
    ViewExamImageMapper viewExamImageMapper;
    @Autowired
    SfxpdyService sfxpdyService;
    @Autowired
    SfxpdyMapper sfxpdyMapper;
    @Autowired
    MedicalHistoryMapper medicalHistoryMapper;
    @Test
    void contextLoads() {

    }

    @Test
    void testMybatis(){
       /* System.out.println(111);
        List<DepExamResultDTO> depExamResultDTOS = djdhsMapper.queryExamResultByExamNum("92310070003");
        System.out.println(depExamResultDTOS.size());*/

       /*List<CommonExamDetailDTO> commonExamDetailDTOS = djdhsMapper.queryCommonExamDetail("82012100003", "E0001922");
        for (CommonExamDetailDTO commonExamDetailDTO : commonExamDetailDTOS) {
            System.out.println(commonExamDetailDTO.toString());
        }*/

        /*List<DepExamResultDTO> depExamResultDTOS = djdhsMapper.queryExamResultByExamNum1("92310070003");
        for (DepExamResultDTO depExamResultDTO : depExamResultDTOS) {
            System.out.println(depExamResultDTO.toString());
        }*/
//        djdhsService.queryWjxmExamInfo("")
       /* List<SampleExamDetailDTO> sampleExamDetailDTOS = djdhsMapper.querySampleExamDetailDTO("92310070003", 1575L);
        for (SampleExamDetailDTO sampleExamDetailDTO : sampleExamDetailDTOS) {
            System.out.println(sampleExamDetailDTO.toString());
        }*/
        /*CenterConfigurationDTO centerConfigurationDTO = djdhsService.getCenterconfigByKey("EXAM_END_ITEM_SEQ", "20201100037001");
        System.out.println(centerConfigurationDTO.toString());*/
        List<ExaminfoChargingItemDTO> examinfoChargingItemDTOS = djdhsMapper.queryExaminfoChargingItemDTO("92310070002", "20201100037001");

        for (ExaminfoChargingItemDTO examinfoChargingItemDTO : examinfoChargingItemDTOS) {
            System.out.println(examinfoChargingItemDTO.toString());
        }

    }

    @Test
    void testPacs() throws IOException {
        /*List<String> userPwd = systemUserMapper.queryUserPwdById("wxb");
        System.out.println(userPwd);*/

        /*List<Map<String, String>> reportDatas = pacsPdfToJpgMapper.getReportData();
        for (Map<String, String> reportData : reportDatas) {
            String f_pdf_path = reportData.get("f_pdf_path");
            System.out.println(f_pdf_path);
        }*/
       /* String s = viewExamImageMapper.queryImagePathByExamNumAndReqCode("92305220014", "02305220010");
        System.out.println(s);*/
        /*Integer integer = pacsPdfToJpgService.pacsPdfToJpg();
        System.out.println(integer);*/
        /*Boolean aBoolean = viewExamImageMapper.procReportAutoSave("92305300001", "305287", "US", "US", "20231028", "肝形态大小正常，包膜完整，实质回声均匀，肝内管道显示清晰。\n" +
                "胆囊大小形态正常，壁齐，腔内未见异常。肝内胆管未见扩张，肝外胆管内径正常，管腔内未见异常。\n" +
                "胰腺形态大小正常，回声均匀，主胰管无扩张。\n" +
                "脾厚2.8cm，肋下（-）。\n" +
                "双肾形态大小正常，包膜完整，实质回声均匀，肾盂结构规律，CDFI示：未见异常血流信号。\n" +
                "膀胱充盈下探查：内未见异常回声。\n" +
                "双侧输尿管未见扩张。\n" +
                "前列腺大小形态正常，三径xxcm，实质回声均匀。\n" +
                "甲状腺双侧叶形态大小正常，腺体组织回声均匀，未见占位性病变，CDFI：血流分布及频谱未见异常。\n" +
                "双侧颈部未见明显异常淋巴结。", "肝、胆、胰、脾、双肾、输尿管、膀胱、前列腺、甲状腺未见明显异常");
        System.out.println(aBoolean);*/
        /*List<String> summaryId = viewExamImageMapper.querySummaryIdByExamNumAndReqCode("92305300001", "02305300008");
        if(!ObjectUtils.isEmpty(summaryId)){
            System.out.println(summaryId.get(0));
        }*/

        /*List<Map<String, String>> queryPacsPInfoByExamNum = sfxpdyMapper.queryPacsPInfoByExamNum("queryPacsPInfoByExamNum");
        List<Map<String, Object>> maps = sfxpdyMapper.queryPacsItemsByExamNum("92310300001");
        for (Map<String, Object> map : maps) {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                System.out.println(entry.getKey()+entry.getValue().toString());
            }
        }*/
        List<String> items = new ArrayList<>();
        items.add("C0002464");
        items.add("C0002474");
        items.add("C0002484");
        SfxpdyVO info = sfxpdyService.getInfo("92310300001", items);
        System.out.println(info.toString());
        /*List<Map<String, Object>> maps1 = sfxpdyMapper.queryPacsPInfoByExamNum("92310300001");
        for (Map<String, Object> map : maps1) {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                System.out.println(entry.getKey()+entry.getValue());
            }
        }
        System.out.println(maps1.size());*/
    }
    @Test
    void peisToPACS(){
       /* List<PacsItemDTO> pacsItems = peisApplyToPacsMapper.getPacsItems("92309120001");
        for (PacsItemDTO pacsItem : pacsItems) {
            System.out.println(pacsItem.toString());
        }*//*
        boolean b = peisApplyToPacsMapper.insertExamItemTrans("92309120001", "123", "123");
        System.out.println(b);

        peisApplyToPacsMapper.deleteExamItemTrans("{0}","{1}");*/

        List<Map<String, Object>> hsPacsReportData = viewExamImageMapper.getHsPacsReportData(50);
        for (Map<String, Object> report : hsPacsReportData) {

            String exandevice = (String) report.get("modality");
            String patientId = (String) report.get("tjh");
            String inHospitalNum = (String) report.get("pacsreqcode");
            Date updatetime1 = (Date) report.get("updatetime");
            Date approveDate1 = (Date)report.get("finaltime");
            String pdfPath = (String) report.get("pdfurl");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String updatetime = simpleDateFormat.format(updatetime1);
            String approveDate = simpleDateFormat.format(approveDate1);

            System.out.println(exandevice);
            System.out.println(patientId);
            System.out.println(inHospitalNum);
            System.out.println(updatetime);
            System.out.println(approveDate);
            System.out.println(pdfPath);
        }
    }

    @Test
    void peisurl() throws IOException {
       // PacsPdfToJpgServiceImpl.downloadImage("http://img.zcool.cn/community/01ab1f554496aa0000019ae9a878ba.jpg@1280w_1l_2o_100sh.jpg","C:\\Users\\10413\\Desktop\\文档\\测试1.png");
        String a = "111111111";
        String[] split = a.split(";");
        System.out.println(split.length);
        System.out.println(split[0]);
    }

    @Test
    void medicalHistory() throws IOException {
        Integer integer = medicalHistoryMapper.selectCount(null);
        System.out.println(integer);

    }
}

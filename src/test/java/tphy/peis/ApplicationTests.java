package tphy.peis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tphy.peis.entity.dto.*;
import tphy.peis.mapper.DjdhsMapper;
import tphy.peis.service.DjdhsService;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Resource
    DjdhsMapper djdhsMapper;

    @Resource
    DjdhsService djdhsService;
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
}

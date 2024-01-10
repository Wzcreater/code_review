package com.tphy.peis.entity.dto;

import lombok.Data;

/**
 * @ClassName: ThirdEcgResultDTO
 * @Description: 三方心电图 获取结果
 * @Date: 2024/1/4 22:32
 * @Author: ZCZ
 **/
@Data
public class ThirdEcgResultDTO {
    /**
     * 体检编号
     */
    private String EXAM_NUM;

    /**
     * 申请编号
     */
    private String REQ_NO;

    /**
     * 报告时间
     */
    private String REPORT_DATETIME;

    /**
     * 检查结论
     */
    private String CONCLUSIONS;

    /**
     * 报告医生名称
     */
    private String REPORT_DOCTORNAME;

    /**
     * 检查状态
     */
    private String EXAM_STATUS;

    /**
     * 报告地址
     */
    private String REPORT_URL;
}

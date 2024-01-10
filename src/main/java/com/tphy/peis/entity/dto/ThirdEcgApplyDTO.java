package com.tphy.peis.entity.dto;

import lombok.Data;

/**
 * @ClassName: ThirdEcgDTO
 * @Description: 三方心电申请实体
 * @Date: 2024/1/4 21:59
 * @Author: ZCZ
 **/
@Data
public class ThirdEcgApplyDTO {
    /**
     * 病人ID
     */
    private  String  PATIENT_ID;

    /**
     *  病人来源
     */
    private String  PATIENT_SOURCE;

    /**
     * 姓名
     */
    private String NAME;

    /**
     * 性别
     */
    private String SEX;

    /**
     * 出生日期
     */
    private String BIRTHDAY;

    /**
     * 年龄
     */
    private String AGE;

    /**
     * 年龄单位
     */
    private String AGE_UNIT;

    /**
     * 家庭住址
     */
    private String ADDRESS;

    /**
     * 电话号码
     */
    private String PHONE_NUMBER;


    /**
     * 处方流水号
     */
    private String NOTE_NO;

    /**
     * 申请科室代码
     */
    private String CDEPT_CODE;

    /**
     * 申请科室名称
     */
    private String CDEPT_NAME;

    /**
     * 执行科室代码
     */
    private String EXE_DEPT_CODE;

    /**
     * 执行科室名称
     */
    private String EXE_DEPT_NAME;

    /**
     * 申请医生代码
     */
    private String DOCT_CODE;

    /**
     * 申请医生姓名
     */
    private String DOCT_NAME;

    /**
     * 申请时间
     */
    private String OPER_DTIME;

    /**
     * 检查类别
     */
    private String STUDY_CLASS;

    /**
     * 检查项目
     */
    private String STUDY_ITEM;


    /**
     * 检查项目代码
     */
    private String ITEM_CODE;

}

package com.tphy.peis.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacsSqdInfo {

    /**
     * 患者姓名
     */
    public String patientName;
    /**
     * 患者性别
     */
    public String sex;
    /**
     * 生日
     */
    public String birthday;
    /**
     * HISID号
     */
    public String hisId;
    /**
     * 患者来源 （0：住院，1：门诊，2：体检）
     */
    public String admIdIss;
    /**
     * 体检号
     */
    public String admId;
    /**
     * 申请单号
     */
    public String accessionNo;
    /**
     * 申请科室
     */
    public String applyDepartmentStr;
    /**
     * 申请医生
     */
    public String applyDoctor;
    /**
     * 申请医院名称
     */
    public String applyHospital;
    /**
     * 申请医院编码
     */
    public String applyHospitalFk;
    /**
     * 检查类型
     */
    public String Modality;
    /**
     * 检查项目名称
     */
    public String checkItemName;
    /**
     * 项目编号
     */
    public String extNo;
}

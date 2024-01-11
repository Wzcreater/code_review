package com.tphy.peis.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * peis 对外接口实体DTO （ pacs 申请登记）
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeisToPacsSqdjDTO {
    /**
     * 住院号/门诊号/住院号/体检号
      */
    public String  admId;
    /**
     * HISID号
      */
    public String  hisId;
    /**
     * 申请单号
      */
    public String  accessionNo;
    /**
     * 已登记、已检查、取消 REGISTERED(已登记)、SUCCESS(已检查)、REGISTER_CANCEL(取消登记)、(只用已登记)
      */
    public String  status;
    /**
     * 操作人
      */
    public String  operator;
    /**
     * 扩展字段
      */
    public String  extNo;
    /**
     * 扩展字段
      */
    public String  applyParam;
    /**
     * 患者来源（0：住院，1：门诊，2：体检）    （体检为2）
      */
    public String  admIdIss;
    /**
     * 医真检查号
      */
    public String  checkNo;
    /**
     * 患者姓名
      */
    public String  patientName;
    /**
     * 性别(F/M/O)
      */
    public String  sex;
    /**
     * 科室号
      */
    public String  departmentId;
    /**
     * HIS类型（用于区分HIS）
      */
    public String  hisType;
    /**
     * 影像号
      */
    public String  imageNo;
    /**
     * 患者拼音名
      */
    public String  engName;
    /**
     * 患者年龄
      */
    public String  patientAge;
    /**
     * 设备类型
      */
    public String  modalityName;
    /**
     * 部位名称
      */
    public String  partName;
    /**
     * 检查项目名称
      */
    public String  itemName;
}

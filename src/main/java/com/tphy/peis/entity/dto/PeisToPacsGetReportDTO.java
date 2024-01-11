package com.tphy.peis.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * peis 对外接口实体DTO （ 获取pacs 检查报告、所见、意见）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeisToPacsGetReportDTO {
    /**
     * 住院号/门诊号/体检号 示例：100812
     */
    public String admId;
    /**
     * 申请单号/条码 示例：
     */
    public String accessionNo;
    /**
     * 病人ID 示例：100022
     */
    public String hisId;
    /**
     * 病人姓名 示例：张三
     */
    public String patientName;
    /**
     * 诊断医生 示例：
     */
    public String doctorNameD;
    /**
     * 审核医生 示例：
     */
    public String doctorNameR;
    /**
     * 报告时间 示例：
     */
    public String reportTime;
    /**
     * 检查方法 示例：
     */
    public String method;
    /**
     * 影像学所见 示例：
     */
    public String eyesee;
    /**
     * 影像学意见 示例：
     */
    public String result;
    /**
     * 阴阳性 示例：
     */
    public String masculine;
    /**
     * 是否危急值 示例：
     */
    public String isReportCrisis;
    /**
     * 报告路径 示例：http://XXX/XX.PDF
     */
    public String reportPath;
    /**
     * 疾病印象 示例：
     */
    public String diseaseImpress;
    /**
     * 病理结果 示例：
     */
    public String pathologyResult;
    /**
     * 疾病名称 示例：
     */
    public String diseaseName;
    /**
     * 检查号 示例：
     */
    public String checkNo;
    /**
     * 影像号 示例：
     */
    public String imageNo;
    /**
     * 扩展字段 示例：
     */
    public String extNo;
    /**
     * 检查日期 示例：2016-01-08
     */
    public String checkTime;
    /**
     * 审核日期 示例：2016-01-09
     */
    public String reviewTime;
    /**
     * 患者来源（0：住院，1：门诊，2：体检） 示例：
     */
    public String admIdIss;
    /**
     * 检查部位 示例：
     */
    public String checkPartName;
    /**
     * 检查项目 示例：
     */
    public String checkItemName;
    /**
     * 扩展字段 示例：
     */
    public String applyParam;
    /**
     * 卡号/条码号 示例：
     */
    public String barcode;
    /**
     * 检查科室 示例：
     */
    public String departmentName;
    /**
     * 床号 示例：
     */
    public String bedNo;
    /**
     * 检查类型 示例：
     */
    public String modalityName;
    /**
     * 设备AE 示例：
     */
    public String armariumAE;
    /**
     * 申请医生 示例：
     */
    public String applyDoctor;
    /**
     * 申请科室 示例：
     */
    public String applyDepartment;
    /**
     * 登记时间（yyyy-MM-dd HH:mm:ss） 示例：
     */
    public String enrolTime;
    /**
     * 登记医生 示例：
     */
    public String enrolDoctor;
    /**
     * 检查技师 示例：
     */
    public String checkDoctor;
    /**
     * 拼音或英文名 示例：
     */
    public String engName;
    /**
     * 生日（yyyy-MM-dd） 示例：
     */
    public String birthday;
    /**
     * 性别(F女M男O其它) 示例：
     */
    public String sex;
    /**
     * 临床诊断 示例：
     */
    public String diagnosis;
    /**
     * 保存的所有图片 示例：XXX.jpg,XXX.jpg
     */
    public String allImage;
    /**
     * 加在报告中分关键图片 示例：XXX.jpg,XXX.jpg
     */
    public String keyImage;
    /**
     * 患者年龄 示例：30
     */
    public String patientAge;
    /**
     * 记录医生 示例：XX
     */
    public String doctorNameW;
    /**
     * 影像室名称 示例：CT检查室
     */
    public String videoRoomName;
    /**
     * 镜头编号(内镜使用) 示例：
     */
    public String lensNumber;
    /**
     * 检查项目编码 示例：
     */
    public String checkItemCode;
    /**
     * 科室号 示例：111
     */
    public String departmentId;
    /**
     * HIS类型 示例：
     */
    public String hisType;
}

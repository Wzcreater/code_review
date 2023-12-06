package com.tphy.peis.entity.dto;


import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PacsItemDTO implements Serializable {

    String name;
    String sex;
    String age;
    String ageType;
    @TableField("age_his")
    String ageHis;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    Date birthday;
    @TableField("card")
    String identityid;
    String address;
    @TableField("phone")
    String mobile;
    String medicalCardId;
    String outpatientNo;
    String hospitalNo;
    @TableField("physical_no")
    String peisNo;
    @TableField("pat_type")
    String patientType;
    String roomNo;
    String bedNo;
    String applyNo;
    @TableField("app_dep")
    String appDept;
    @TableField("app_doc")
    String appPhysician;
    @TableField("item_modality")
    String modality;
    @TableField("chargingItemCode")
    String itemCode;
    @TableField("item_name")
    String itemName;
    @TableField("item_price")
    String itemPrice;
    @TableField("item_count")
    Integer itemCount;
    @TableField("scheduled_date")
    DateTime scheduledDate;
    String clinSymp;
    String physSign;
    String clinDiag;
    String relevantDiag;
    String relevantLabTest;
    String notice;
    String appHsp;
    @TableField("app_date")
    @DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
    DateTime appDate;
    String medicalRecordNo;
    String feetype;
    Integer invoiceNo;
    String utId;
    String subModality;
    String appPhysicianNumber;
    String appDeptCode;
    Integer status;
    String itemId;
}

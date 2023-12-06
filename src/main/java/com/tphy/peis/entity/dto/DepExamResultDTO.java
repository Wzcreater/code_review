package com.tphy.peis.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepExamResultDTO {
    private String ksmc;
    private String dep_name;
    private String dep_num;
    private String item_name;
    private long item_id;
    private long id;
    private String exam_result;
    private String health_level;
    private String exam_doctor;
    private String exam_date;
    private String dep_category;
    private String ref_value;
    private String item_unit;
    private long sam_demo_id;
    private String exam_desc;
    private long critical_id;
    private int data_source;
    private long dep_id;
    private long charging_item_id;
    private long exam_item_id;
    private String item_code;
    private String item_num = "";
    private String defaultResult;
    private String dep = "";
    private long exam_info_id;
    private String exam_status = "";
    private String exam_status_y = "";
    private String arch_num = "";
    private String exam_num = "";
    private String join_date = "";
    private String req_id = "";
    private String charge_item_id = "";

    private long result_type;
    private String occ_item_flag = "N";
    //检查项目结果文字类型
    private String item_category;
    public void setExam_status(String exam_status) {
        this.exam_status = exam_status;
        if ("N".equals(exam_status)) {
            setExam_status_y("未检");
        } else if ("Y".equals(exam_status)) {
            setExam_status_y("已检");
        } else if ("G".equals(exam_status)) {
            setExam_status_y("弃检");
        } else if ("D".equals(exam_status)) {
            setExam_status_y("延期");
        } else if ("C".equals(exam_status)) {
            setExam_status_y("已登记");
        }
    }

}



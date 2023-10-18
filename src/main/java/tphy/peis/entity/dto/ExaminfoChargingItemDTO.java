package tphy.peis.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExaminfoChargingItemDTO
        implements Serializable {
    private static final long serialVersionUID = -97502163798576023L;
    private long id;
    private long examinfo_id;
    private long charge_item_id;
    private String exam_indicator;
    private String exam_indicators;
    private String item_name;
    private String item_code;
    private double item_amount;
    private double discount;
    private double amount;
    private String item_type;
    private String isActive;
    private String final_exam_date;
    private String pay_status;
    private String pay_statuss;
    private String exam_status;
    private String exam_statuss;
    private long is_new_added;
    private String exam_date;
    private long creater;
    private String create_time;
    private long updater;
    private String update_time;
    private long check_status;
    private long exam_doctor_id;
    private String exam_doctor_name;
    private String add_status;
    private double calculation_amount;
    private String is_application;
    private String is_applications;
    private String change_item;
    private double team_pay;
    private double personal_pay;
    private String team_pay_status;
    private String team_pay_statuss;
    private String his_req_status;
    private String his_req_statuss;
    private long dep_id;
    private String dep_name;
    private String dep_num;
    private String dep_category;
    private String sample_status;
    private String sample_statuss;
    private long sample_id;
    private String app_type = "1";
    private String app_typename = "";

    private int itemnum = 1;
    private String his_num;
    private int calculation_rate;
    private String del_bz;
    private String exam_num;
    private long item_discount;
    private String charging_item_code;
    private long introducer;
    private String introducer_name;
    private long inputter;
    private String item_category;

    public void setApp_type(String app_type) {
        this.app_type = app_type;
        if ("1".equals(this.app_type)) {
            setApp_typename("普通");
        } else if ("2".equals(this.app_type)) {
            setApp_typename("职业病");
        } else {
            setApp_typename("普通");
        }
    }
    public void setExam_indicator(String exam_indicator) {
        this.exam_indicator = exam_indicator;

        if (exam_indicator == null) {
            setExam_indicators("未知");
        } else if ("G".equals(exam_indicator)) {
            setExam_indicators("个人付费");
        } else if ("T".equals(exam_indicator)) {
            setExam_indicators("团体付费");
        } else if ("M".equals(exam_indicator)) {
            setExam_indicators("免费");
        } else if ("GT".equals(exam_indicator)) {
            setExam_indicators("混合付费");
        } else if ("TG".equals(exam_indicator)) {
            setExam_indicators("混合付费");
        } else {
            setExam_indicators("未知");
        }
    }
    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;

        if (pay_status == null) {
            setPay_statuss("未知");
        } else if ("Y".equals(pay_status)) {
            setPay_statuss("已付费");
        } else if ("R".equals(pay_status)) {
            setPay_statuss("预付费");
        } else if ("N".equals(pay_status)) {
            setPay_statuss("未付费");
        } else {
            setPay_statuss("未知");
        }
    }
   public void setExam_status(String exam_status) {
        this.exam_status = exam_status;

        if (exam_status == null) {
            setExam_statuss("未知");
        } else if ("Y".equals(exam_status)) {
            setExam_statuss("已检查");
        } else if ("G".equals(exam_status)) {
            setExam_statuss("弃检");
        } else if ("C".equals(exam_status)) {
            setExam_statuss("已登记");
        } else if ("D".equals(exam_status)) {
            setExam_statuss("延期");
        } else if ("N".equals(exam_status)) {
            setExam_statuss("未检查");
        } else {
            setExam_statuss("未知");
        }
    }
    public void setIs_application(String is_application) {
        this.is_application = is_application;

        if (is_application == null) {
            setIs_applications("未知");
        } else if ("N".equals(is_application)) {
            setIs_applications("未发");
        } else if ("Y".equals(is_application)) {
            setIs_applications("已发");
        } else {
            setIs_applications("未知");
        }
    }
    public void setSample_status(String sample_status) {
        this.sample_status = sample_status;
        if ("W".equals(sample_status)) {
            setSample_statuss("未采样");
        } else if ("Y".equals(sample_status)) {
            setSample_statuss("已采样");
        } else if ("E".equals(sample_status)) {
            setSample_statuss("已检查");
        } else if ("H".equals(sample_status)) {
            setSample_statuss("已核收");
        }
    }

    public void setHis_req_status(String his_req_status) {
        this.his_req_status = his_req_status;
        if ("Y".equals(his_req_status)) {
            this.his_req_statuss = "已发";
        } else {
            this.his_req_statuss = "未发";
        }
    }
}



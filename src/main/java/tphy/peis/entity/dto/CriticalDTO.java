package tphy.peis.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriticalDTO implements Serializable {
    private static final long serialVersionUID = -97502163798576023L;
    private long id;
    private String exam_result;
    private String note;
    private String check_date;
    private String exam_num;
    private String user_name;
    private String arch_num;
    private String check_doctor;
    private String dep_name;
    private String item_name;
    private long done_flag;
    private String done_flag_s;
    private String done_date;
    private String examination_item_name;
    private long userid;
    private long dept_id;
    private long charging_item_id;
    private int data_source;
    private long creater;
    private String creater_name;
    private String create_time;
    private String disease_name;
    private String sex;
    private int age;
    private String phone;
    private String company;
    private int status;
    private long item_code;
    private long exam_item_id;
    private String critical_type;
    private String critical_type_s;
    private String old_results;
    private String parent_critical_class_name;
    private String data_name;
    private String critical_class_parent_name;
    private String critical_class_name;
    private String critical_class_level;
    private int critical_level_id;
    private String data_code_children;
    private long exam_info_id;
    private String visit_num;
    private String customer_feedback;
    private String visit_date;
    private String chi_name;
    private int give_notice_type;
    private String company_name;


    public void setDone_flag(long done_flag) {
        if (done_flag == 0L) {
            this.done_flag_s = "未通知";
        } else if (done_flag == 1L) {
            this.done_flag_s = "已通知";
        } else {
            this.done_flag_s = "未联系上";
        }
    }

}



package tphy.peis.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 公共检查明细
 */
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonExamDetailDTO implements Serializable {
    private static final long serialVersionUID = 9165522337204355286L;
    private long id;
    private long exam_info_id;
    private String exam_item_id;
    private String exam_doctor;
    private String center_num;
    private String health_level;
    private String exam_result;
    private String exam_date;
    private long creater;
    private String create_time;
    private long updater;
    private String update_time;
    private String join_date;
    private String suggestion;
    private String item_name;
    private String charging_item_id;
    private String exam_num;
    private String item_code;
    private String charging_item_code;
    private String item_num;
    private String exam_result_back;
    private String charging_item_name = "";

    private long dep_id;
    private String dep_name = "";

    private long seq_code;
    private double ref_Mmax;
    private double ref_Mmin;
    private double ref_Fmin;
    private double ref_Fmax;
    private String item_unit = "";
    private String item_category = "";

}



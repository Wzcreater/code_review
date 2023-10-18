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
public class SampleExamDetailDTO
        implements Serializable {
    private static final long serialVersionUID = -97502163798576023L;
    private long id;
    private long creater;
    private String create_time;
    private long updater;
    private String update_time;
    private long exam_info_id;
    private long sample_id;
    private String sample_barcode;
    private String status;
    private String status_y;
    private String pic_path;
    private String center_num;
    private String approver;
    private String approve_date;
    private String demo_name;
    private long item_id;
    private String item_ids;
    private String item_name;
    private String item_code;
    private long is_binding;
    private String is_binding_y;
    private String demo_color;
    private String demo_indicator;
    private long BarCode_Class;
    private String is_application;
    private String is_application_y;
    private long print_num;
    private long print_dep;
    private String dep_type;
    private String dep_name;

    private String exam_num;

    public void setStatus(String status) {
        this.status = status;
        if ("W".equals(status)) {
            setStatus_y("未采样");
        } else if ("Y".equals(status)) {
            setStatus_y("已采样");
        } else if ("E".equals(status)) {
            setStatus_y("已检查");
        } else if ("H".equals(status)) {
            setStatus_y("已核收");
        } else if ("N".equals(status)) {
            setStatus_y("未采样");
        }
    }

    public void setIs_binding(long is_binding) {
        this.is_binding = is_binding;
        if (is_binding == 0L) {
            setIs_binding_y("未绑管");
        } else if (is_binding == 1L) {
            setIs_binding_y("已绑管");
        }
    }

    public void setIs_application(String is_application) {
        this.is_application = is_application;
        if ("Y".equals(is_application)) {
            this.is_application_y = "已申请";
        } else {
            this.is_application_y = "未申请";
        }
    }

}



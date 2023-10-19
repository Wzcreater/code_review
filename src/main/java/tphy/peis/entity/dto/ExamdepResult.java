package tphy.peis.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: ExamdepResult
 * @Description: 体检科室结果
 * @Date: 2023/10/19 10:56
 * @Author: ZCZ
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("exam_dep_result")
public class ExamdepResult implements Serializable {
    @TableId(type = IdType.AUTO)
    private long id;

    private long exam_info_id;

    private String exam_doctor;

    private String dep_id;

    private String exam_result_summary;

    private String suggestion;

    private String center_num;

    private long approver;

    private Date approve_date;

    private long creater;

    private String create_time;

    private long updater;

    private Date update_time;

    private String Special_setup;
    private String dep_num;

    private String app_type;

    private String exam_num;
}

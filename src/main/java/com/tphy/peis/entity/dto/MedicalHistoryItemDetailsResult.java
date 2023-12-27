package com.tphy.peis.entity.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("medical_history_item_details_result")
public class MedicalHistoryItemDetailsResult {
    @TableId(type = IdType.AUTO)
    public Integer id;
    public String item_details_sum;
    public String item_details_text;
    public Integer item_details_id;
    public String exam_num;
    public Integer item_id;
    public String input_type;
}

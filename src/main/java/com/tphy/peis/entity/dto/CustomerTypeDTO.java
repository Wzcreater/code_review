package com.tphy.peis.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: CustomerTypeDTO
 * @Description: 顾客类型
 * @Date: 2023/10/18 15:41
 * @Author: ZCZ
 **/
@Data
public class CustomerTypeDTO implements Serializable {
    private static final long serialVersionUID = -97502163798576023L;
    private long id;
    private String type_name;
    private String type_code;
    private String type_comment;
    private String creater;
    private String create_time;
    private String updater;
    private String update_time;

}

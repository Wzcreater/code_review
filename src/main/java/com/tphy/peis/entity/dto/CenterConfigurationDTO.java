package com.tphy.peis.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class CenterConfigurationDTO implements Serializable {
    private static final long serialVersionUID = -97502163798576023L;
    private String center_num;
    private String center_name;
    private String config_key;
    private String config_value;
    private String is_active;
    private String common;
    private String center_type;
    private String data_name;

}



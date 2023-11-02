package com.tphy.peis.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SfxpItemDetail {
    /**
     *  项目名称
     */
    String itemname;
    /**
     *  数量
     */
    String number;
    /**
     *  零售价
     */
    String price;
    /**
     *  折扣率
     */
    String zk;
    /**
     *  折扣后
     */
    String zkprice;
}
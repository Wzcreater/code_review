package com.tphy.peis.entity.vo;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SfxpdyVO {
    /**
     * 体检号
     */
    String tjh;
    /**
     * 体检卡号
     */
    String tjkh;
    /**
     * 体检患者
     */
    String tjhz;
    /**
     * 性别
     */
    String sex;
    /**
     * 年龄
     */
    String age;
    /**
     * 操作员
     */
    String czy;
    /**
     * 体检单位
     */
    String tjdw;
    /**
     * 卡余额
     */
    String kye;
    /**
     * 总价格
     */
    String zjg;
    /**
     * 打印时间
     */
    String dysj;
    /**
     * 项目列表
     */
    List<SfxpItemDetail> sfxpItemDetails;
}

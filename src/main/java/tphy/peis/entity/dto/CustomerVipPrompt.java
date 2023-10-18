package tphy.peis.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: CustomerVipPrompt
 * @Description: 顾客VIP提示表
 * @Date: 2023/10/18 15:49
 * @Author: ZCZ
 **/
@Data
public class CustomerVipPrompt implements Serializable {
    private static final long serialVersionUID = -97502163798576023L;
//    @Id
//    @GeneratedValue(generator = "paymentableGenerator")
//    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
//    @Column(name = "id", unique = true, nullable = false, length = 50)
    private String id;
//    @Column(name = "exam_num")
    private String exam_num;
//    @Column(name = "user_name")
    private String user_name;
//    @Column(name = "com_name")
    private String com_name;
//    @Column(name = "prompt_status")
    private String prompt_status;
//    @Column(name = "prompt_user")
    private long prompt_user;
//    @Column(name = "creater")
    private long creater;
//    @Column(name = "create_time")
    private Date create_time;
}

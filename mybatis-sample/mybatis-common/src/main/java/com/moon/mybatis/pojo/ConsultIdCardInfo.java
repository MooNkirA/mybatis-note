package com.moon.mybatis.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * consult_idcardinfo 表映射实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2021-3-13 09:30
 * @description
 */
@Alias("consultIdCardInfo") // 设置mybatis的类型别名
@Data
public class ConsultIdCardInfo implements Serializable {

    private static final long serialVersionUID = -3509992727837716565L;

    public Integer innerId;
    public String psptId;
    public String name;
    public String sex;
    public String birthday;
    public String address;
    public String picture;
    public String activeTime;
    public String nation;

}

package com.xukai.netshop.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/26 14:28
 * @modified By:
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "buyer_info")
public class BuyerInfo {

    /**
     * 买家ID
     */
    @Id
    private String buyerId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    public BuyerInfo(String username, String password, String phone, String email) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    public BuyerInfo() {
        super();
    }
}

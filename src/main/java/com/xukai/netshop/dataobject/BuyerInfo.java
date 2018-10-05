package com.xukai.netshop.dataobject;

import lombok.Data;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/9/26 14:28
 * @modified By:
 */
@Data
public class BuyerInfo {

    /**
     * 买家ID
     */
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

package com.xukai.netshop.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/7/9 13:35
 * @modified By:
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "seller_info")
public class SellerInfo {

    @Id
    private Integer id;

    private String username;

    private String password;
}

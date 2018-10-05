package com.xukai.netshop.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xukai.netshop.enums.ProductStatusEnum;
import com.xukai.netshop.utils.EnumUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/21 10:21
 * Modified By:
 */
@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "product_info")
public class ProductInfo {

    @Id
    private String productId;

    /**
     * 名字
     */
    private String productName;

    /**
     * 单价
     */
    private BigDecimal productPrice;

    /**
     * 商品颜色
     */
    private String productColor;

    /**
     * 商品尺码
     */
    private String productSize;

    /**
     * 库存
     */
    private Integer productStock;

    /**
     * 描述
     */
    private String productDescription;

    /**
     * 主图
     */
    private String productImgMd;

    /**
     * 详情展示图
     */
    private String productDetailImg;

    /**
     * 状态, 0正常/1下架
     */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /**
     * 类目编号
     */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtils.getMsgByCode(productStatus, ProductStatusEnum.class);
    }

    public ProductInfo() {
        super();
    }

}

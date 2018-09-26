package com.xukai.netshop.form;

import lombok.Data;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/7/9 11:20
 * @modified By:
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    /**
     * 类目名字.
     */
    private String categoryName;

    /**
     * 类目编号.
     */
    private Integer categoryType;
}

package com.xukai.netshop.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

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
    @NotEmpty(message = "类目名称不能为空")
    private String categoryName;

    /**
     * 类目编号.
     */
    @NotNull
    private String categoryType;
}

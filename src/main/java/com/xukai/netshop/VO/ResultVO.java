package com.xukai.netshop.VO;

import lombok.Data;

/**
 * Author: Xukai
 * Description: http请求返回的最外层对象
 * CreateDate: 2018/6/21 17:04
 * Modified By:
 */
@Data
public class ResultVO<T> {

    /**
     * 错误码.
     */
    private Integer code;

    /**
     * 提示信息.
     */
    private String msg;

    /**
     * 具体内容.
     */
    private T data;
}

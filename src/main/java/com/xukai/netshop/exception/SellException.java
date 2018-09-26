package com.xukai.netshop.exception;

import com.xukai.netshop.enums.ResultEnum;
import lombok.Getter;

/**
 * Author: Xukai
 * Description:
 * CreateDate: 2018/6/22 14:25
 * Modified By:
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}

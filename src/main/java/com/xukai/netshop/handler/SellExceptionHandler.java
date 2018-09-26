package com.xukai.netshop.handler;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.exception.SellerAuthorizeException;
import com.xukai.netshop.utils.ResultVOUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/7/10 13:41
 * @modified By:
 */
@ControllerAdvice
public class SellExceptionHandler {

    /**
     * SellerAuthorizeException异常处理
     * 如果登录验证不通过，先跳转至错误页，再跳转至扫码登录页
     * http:localhost/netshop/seller/
     */
    @ExceptionHandler({SellerAuthorizeException.class})
    public ModelAndView handlerAuthorizeException() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:" + "http:localhost/netshop/seller/");
        return mav;
    }

    @ExceptionHandler({SellException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultVO handlerSellerException(SellException exception) {
        return ResultVOUtil.error(exception.getCode(), exception.getMessage());
    }
}

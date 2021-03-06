package com.xukai.netshop.handler;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.config.BaseUrlConfig;
import com.xukai.netshop.exception.SellException;
import com.xukai.netshop.exception.SellerAuthorizeException;
import com.xukai.netshop.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/7/10 13:41
 * @modified By:
 */
@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private BaseUrlConfig baseUrlConfig;

    /**
     * SellerAuthorizeException异常处理
     * 如果登录验证不通过，跳转登录页
     * http:localhost/sell/
     */
    @ExceptionHandler({SellerAuthorizeException.class})
    public ModelAndView handlerAuthorizeException(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String sellRequestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        session.setAttribute("sellRequestURI", sellRequestURI);
        mav.setViewName("redirect:" + baseUrlConfig.getBackBaseUrl() + "/seller/");
        return mav;
    }

    @ExceptionHandler({SellException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultVO handlerSellerException(SellException exception) {
        return ResultVOUtil.error(exception.getCode(), exception.getMessage());
    }
}

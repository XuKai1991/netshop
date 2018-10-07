package com.xukai.netshop.handler;

import com.xukai.netshop.VO.ResultVO;
import com.xukai.netshop.config.BaseUrlConfig;
import com.xukai.netshop.enums.ResultEnum;
import com.xukai.netshop.exception.BuyException;
import com.xukai.netshop.exception.BuyerAuthorizeException;
import com.xukai.netshop.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Xukai
 * @description:
 * @createDate: 2018/7/10 13:41
 * @modified By:
 */
@RestControllerAdvice
public class BuyExceptionHandler {

    @Autowired
    private BaseUrlConfig baseUrlConfig;

    /**
     * SellerAuthorizeException异常处理
     * 如果登录验证不通过，跳转登录页
     * http:localhost/netshop/buyer/
     */
    @ExceptionHandler({BuyerAuthorizeException.class})
    public ResultVO handlerAuthorizeException(HttpServletRequest request) {
        // ModelAndView mav = new ModelAndView();
        // String buyRequestURI = request.getRequestURI();
        // HttpSession session = request.getSession();
        // session.setAttribute("buyRequestURI", buyRequestURI);
        // mav.setViewName("redirect:" + baseUrlConfig.getBack_base_url() + "/netshop/buyer/");
        // return mav;
        return ResultVOUtil.error(ResultEnum.LOGIN_STATUS_ERROR.getCode(), ResultEnum.LOGIN_STATUS_ERROR.getMessage());
    }

    @ExceptionHandler({BuyException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultVO handlerSellerException(BuyException exception) {
        return ResultVOUtil.error(exception.getCode(), exception.getMessage());
    }
}

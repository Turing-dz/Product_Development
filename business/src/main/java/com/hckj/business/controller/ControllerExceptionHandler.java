package com.hckj.business.controller;

import com.hckj.business.exception.BusinessException;
import com.hckj.business.resp.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理、数据预处理等
 */
@Slf4j//打印日志
@ControllerAdvice//针对controller的统一处理
public class ControllerExceptionHandler {

    /**
     * 所有异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody//返回json数据
    public CommonResp<Object> exceptionHandler(Exception e) throws Exception {
        CommonResp<Object> commonResp = new CommonResp<>();
        log.error("系统异常：", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统出现异常，请联系管理员");
        return commonResp;
    }
//    @ExceptionHandler(value = RuntimeException.class)
//    @ResponseBody//返回json数据
//    public CommonResp<Object> exceptionHandler(RuntimeException e) {
//        CommonResp<Object> commonResp = new CommonResp<>();
//        log.error("业务异常：", e);
//        commonResp.setSuccess(false);
//        commonResp.setMessage(e.getMessage());
//        return commonResp;
//    }



    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody//返回json数据
    public CommonResp<Object> exceptionHandler(BusinessException e) {
        CommonResp<Object> commonResp = new CommonResp<>();
        log.error("business业务异常：", e);
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getDesc());
        return commonResp;
    }
    /**
     * 校验异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResp<Object> exceptionHandler(BindException e) {
        CommonResp<Object> commonResp = new CommonResp<>();
        log.error("校验异常：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }
}

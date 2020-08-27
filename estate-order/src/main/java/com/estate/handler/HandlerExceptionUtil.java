package com.estate.handler;

import com.estate.exception.OrderException;
import com.estate.util.Result;
import com.estate.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义异常捕获,返回json
 * @author mq
 * @date 2020/7/29 14:07
 * @description 捕获异常
 */
@Slf4j
@ControllerAdvice
public class HandlerExceptionUtil {

    @ExceptionHandler(OrderException.class)
    @ResponseBody
    public Result handlerException(OrderException orderException){

        log.error(orderException.getMessage(),orderException);

        return ResultUtil.error(orderException.getMessage(),orderException.getCode());
    }
}

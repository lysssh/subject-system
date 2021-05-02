package com.liye.servicebase.exceptionhandler;

import com.liye.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    //统一异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理..");
    }


    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了返回数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理..");
    }



    //自定义异常
    @ExceptionHandler(SubjectSystemException.class)
    @ResponseBody //为了返回数据
    public R error(SubjectSystemException e) {
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}

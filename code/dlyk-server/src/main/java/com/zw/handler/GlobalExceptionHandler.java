package com.zw.handler;

import com.zw.result.R;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // aop 拦截标注了@RestController的controller中的所有方法
//@ControllerAdvice // aop 拦截标注了@Controller的controller中的所有方法
public class GlobalExceptionHandler {
    /**
     * 只要发生异常都会被这个处理方法所捕获
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public R handException(Exception e){
        // e.printStackTrace();
        // System.out.println("handException执行.....Exception");
        return R.error(e.getMessage());
    }

    /**
     * 精确的异常处理，只捕获DataAccessException类型的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = DataAccessException.class)
    public R handDataAccessException(DataAccessException e){
        // e.printStackTrace();
        // System.out.println("handException执行.....Exception");
        return R.error("数据库操作异常");
    }
}

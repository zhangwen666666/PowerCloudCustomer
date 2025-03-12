package com.zw.result;

import com.zw.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class R<T> {
    private int code; // 结果码
    private String msg; // 结果信息
    private T data; // 后端结果数据

    public static <T> R<T> OK(){
        return  R.<T> builder().code(CodeEnum.OK.getCode()).msg(CodeEnum.OK.getMsg()).data(null).build();
    }

    public static <T> R<T> OK(T data){
        return  R.<T> builder().code(CodeEnum.OK.getCode()).msg(CodeEnum.OK.getMsg()).data(data).build();
    }

    public static <T> R<T> OK(String msg, T data){
        return  R.<T> builder().code(CodeEnum.OK.getCode()).msg(msg).data(data).build();
    }

    public static <T> R<T> error(){
        return R.<T> builder().code(CodeEnum.FAIL.getCode()).msg(CodeEnum.FAIL.getMsg()).data(null).build();
    }

    public static <T> R<T> error(String msg){
        return R.<T> builder().code(CodeEnum.FAIL.getCode()).msg(msg).data(null).build();
    }

    public static <T> R<T> error(int code, String msg){
        return R.<T> builder().code(code).msg(msg).data(null).build();
    }
}

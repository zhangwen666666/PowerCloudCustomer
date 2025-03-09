package com.zw.util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {
    /**
     * 使用response，把结果写出到前端
     * @param response
     * @param result
     */
    public static void write(HttpServletResponse response, String result){
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = null;
        try{
            writer = response.getWriter();
            writer.write(result);
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            if (writer != null){
                writer.close();
            }
        }
    }
}

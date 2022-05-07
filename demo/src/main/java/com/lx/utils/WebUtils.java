package com.lx.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtils {

    /**
     * 将字符串渲染到客户端
     * @param resp 渲染对象
     * @param str 待渲染的字符串
     * @retrun null
     */
    public static String renderString(HttpServletResponse resp,String str) {
        try {
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.runjing.resolve_excel_auto.config;


import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : forestSpringH
 * @description: 预备设计ThreadLocal做线程隔离处理，隔离用户查询，修改，排序，联表等操作。
 * @date : Created in 2023/8/22
 * @modified By: 黄林春
 * @project: resolve_excel_auto
 */

@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer , HandlerInterceptor {
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(this);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request,@NonNull  HttpServletResponse response,@NonNull  Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull Object handler) throws Exception {
        String sql = request.getParameter("sql");
        String tableName = request.getParameter("tableName");
        if (StringUtils.isNotEmpty(sql)){
            if (!sql.contains("test") || !tableName.contains("test")){
                log.error("过滤非法请求：{}",sql);
                return false;
            }
            if (sql.contains("DELETE") || sql.contains("delete") || sql.contains("UPDATE") || sql.contains("update")){
                log.error("过滤非法请求：{}",sql);
                return false;
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

package com.jmal.clouddisk.annotation;

import cn.hutool.core.text.CharSequenceUtil;
import jakarta.annotation.PostConstruct;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description 注解工具
 * @blame jmal
 * @Date 2021/1/8 8:38 下午
 */
@Component
public class AnnoManageUtil {

    /***
     * 权限标识列表
     */
    public static final List<String> AUTHORITIES = new ArrayList<>();

    /**
     * 通过反射获取所有的权限标识
     *
     * @return
     */
    @PostConstruct
    public void getAllAuthorities() {
        Reflections reflections = new Reflections("com.jmal.clouddisk.controller", "com.jmal.clouddisk.oss.web");
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(RestController.class);
        List<String> arrayList = new ArrayList<>();
        for (Class<?> classes : classesList) {
            //得到该类下面的所有方法
            Method[] methods = classes.getDeclaredMethods();
            for (Method method : methods) {
                //得到该类下面的Permission注解
                Permission permission = method.getAnnotation(Permission.class);
                if(permission == null){
                    continue;
                }
                if(CharSequenceUtil.isBlank(permission.value())){
                    continue;
                }
                if(arrayList.contains(permission.value())){
                    continue;
                }
                arrayList.add(permission.value());
            }
        }
        AUTHORITIES.addAll(arrayList.stream().sorted().toList());
    }

}

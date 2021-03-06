package com.taisf.services.device.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by JK on 2017/9/7.
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext; // Spring应用上下文环境

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }



    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public  <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }


}

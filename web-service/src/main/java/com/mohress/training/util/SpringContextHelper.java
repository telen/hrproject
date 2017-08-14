package com.mohress.training.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by youtao.wan on 2017/8/13.
 */
@Component
public class SpringContextHelper implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    public static BeanFactory getBeanFactory(){
       return applicationContext.getAutowireCapableBeanFactory();
    }

    public static <T> T getBean(Class<T> type){
        return applicationContext.getBean(type);
    }

    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    public static boolean contains(String beanName){
        return applicationContext.getBean(beanName) != null;
    }
}

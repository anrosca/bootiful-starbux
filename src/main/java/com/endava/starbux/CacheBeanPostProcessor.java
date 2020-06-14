package com.endava.starbux;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> cachedBeanClassesByName = new HashMap<>();
    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        for (Method method : ReflectionUtils.getDeclaredMethods(beanClass)) {
            if (method.isAnnotationPresent(Cache.class)) {
                cachedBeanClassesByName.put(beanName, beanClass);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (cachedBeanClassesByName.containsKey(beanName)) {
            Class<?> beanClass = cachedBeanClassesByName.get(beanName);
            return Enhancer.create(beanClass, new CacheInvocationHandler(beanClass, bean,beanName));
        }
        return bean;
    }

    private class CacheInvocationHandler implements InvocationHandler {
        private final Class<?> beanClass;
        private final Object bean;
        private final String beanName;

        public CacheInvocationHandler(Class<?> beanClass, Object bean, String beanName) {
            this.beanClass = beanClass;
            this.bean = bean;
            this.beanName = beanName;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (beanClass.getMethod(method.getName(), method.getParameterTypes())
                    .isAnnotationPresent(Cache.class)) {
                String key = makeCacheKey(method, args);
                if (cache.containsKey(key)) {
                    return cache.get(key);
                }
                Object result = ReflectionUtils.invokeMethod(method, bean, args);
                cache.put(key, result);
                return result;
            }
            return ReflectionUtils.invokeMethod(method, bean, args);
        }

        private String makeCacheKey(Method method, Object[] args) {
            return beanName + "_" + method.getName() + "_" +
                    Arrays.deepToString(args);
        }
    }
}


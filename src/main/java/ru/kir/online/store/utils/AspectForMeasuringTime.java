package ru.kir.online.store.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class AspectForMeasuringTime {
    private Map<String, Long> mapTime;

    @PostConstruct
    public void init(){
        mapTime = new HashMap<>();
        mapTime.put("CategoryService", 0L);
        mapTime.put("OrderService", 0L);
        mapTime.put("ProductService", 0L);
        mapTime.put("RoleService", 0L);
        mapTime.put("UserService", 0L);
    }

    @Around("execution(public * ru.kir.online.store.services.CategoryService.*(..))")
    public Object timeMeasuringForCategoryService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        sumTime("CategoryService", duration);
        return out;
    }

    @Around("execution(public * ru.kir.online.store.services.OrderService.*(..))")
    public Object timeMeasuringForOrderService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        sumTime("OrderService", duration);
        return out;
    }

    @Around("execution(public * ru.kir.online.store.services.ProductService.*(..))")
    public Object timeMeasuringForProductService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        sumTime("ProductService", duration);
        return out;
    }

    @Around("execution(public * ru.kir.online.store.services.RoleService.*(..))")
    public Object timeMeasuringForRoleService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        sumTime("RoleService", duration);
        return out;
    }

    @Around("execution(public * ru.kir.online.store.services.UserService.*(..))")
    public Object timeMeasuringForUserService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        sumTime("UserService", duration);
        return out;
    }

    private void sumTime(String nameKey, long duration){
        long currentDuration = mapTime.get(nameKey) + duration;
        mapTime.put(nameKey, currentDuration);
    }

    public Map<String, Long> getMapTime() {
        return Collections.unmodifiableMap(mapTime);
    }

}

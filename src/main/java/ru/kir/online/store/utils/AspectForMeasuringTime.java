package ru.kir.online.store.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Aspect
public class AspectForMeasuringTime {
    private Map<String, Long> mapTime;

    @PostConstruct
    public void init(){
        mapTime = new ConcurrentHashMap<>();
    }

    @Around("execution(public * ru.kir.online.store.services.*.*(..))")
    public Object timeMeasuringForServices(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        String serviceName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
        mapTime.merge(serviceName, duration, Long::sum);
        return out;
    }


    public Map<String, Long> getMapTime() {
        return Collections.unmodifiableMap(mapTime);
    }

}

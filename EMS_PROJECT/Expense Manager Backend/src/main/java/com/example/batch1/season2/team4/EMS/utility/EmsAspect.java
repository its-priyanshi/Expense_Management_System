package com.example.batch1.season2.team4.EMS.utility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class EmsAspect {
    public static final Log LOGGER = LogFactory.getLog(EmsAspect.class);

    @AfterThrowing(pointcut = "execution(* com.example.batch1.season2.team4.EMS.*Application.*(..))", throwing = "exception")
    public void logServiceException(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
    }
}
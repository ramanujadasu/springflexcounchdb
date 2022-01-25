//package com.springflexcounchdb.filter;
//
//import java.util.Objects;
//import java.util.concurrent.atomic.AtomicReference;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//
//import lombok.experimental.var;
//import lombok.extern.slf4j.Slf4j;
//import reactor.core.publisher.Mono;
//
//@Aspect
//@Slf4j
//public class LoggerAspect {
//
//    @Around("@annotation(Loggable)")
//    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//
//        long start = System.currentTimeMillis();
//        var result = joinPoint.proceed();
//        if (result instanceof Mono) {
//            var monoResult = (Mono) result;
//            AtomicReference<String> traceId = new AtomicReference<>("");
//
//            return monoResult
//                    .doOnSuccess(o -> {
//                        var response = "";
//                        if (Objects.nonNull(o)) {
//                            response = o.toString();
//                        }
//                        log.info("Enter: {}.{}() with argument[s] = {}",
//                                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
//                                joinPoint.getArgs());
//                        log.info("Exit: {}.{}() had arguments = {}, with result = {}, Execution time = {} ms",
//                                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
//                                joinPoint.getArgs()[0],
//                                response, (System.currentTimeMillis() - start));
//                    });
//        }
//		return result;
//    }
//}

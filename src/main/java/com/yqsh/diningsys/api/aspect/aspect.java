package com.yqsh.diningsys.api.aspect;

import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import org.apache.commons.collections.map.HashedMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 对方法的必填参数进行验证
 *
 * @author zhshuo create on 2016-12-07 9:40
 */
@SuppressWarnings("ALL")
@Component
@Aspect
public class aspect {

    @Around("@annotation(com.wordnik.swagger.annotations.ApiOperation)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable{
        String paramRequired = null,methodName,APIName = null;
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = sra.getRequest();

            Map<String, String[]> parameterMap = request.getParameterMap();

            methodName = joinPoint.getSignature().getName();
            Object target = joinPoint.getTarget();
            Method method = getMethodByClassAndName(target.getClass(), methodName);
            APIName = getAPIName(method);
            List<Annotation> apiParams = getAPIAnnotationByMethod(method ,ApiParam.class);
            paramRequired = validateParamRequired(parameterMap,apiParams);
        } catch (Exception e) {
            paramRequired = "S999";
        } finally {
            Map<String,Object> map = new HashedMap();
            if(StringUtils.isEmpty(paramRequired)){
                return joinPoint.proceed();
            }else if(paramRequired.equalsIgnoreCase("S999")){
                map.put("result", "S999");
                map.put("msg", APIEnumDefine.enumValue("S999"));
                return map;
            }else if(!paramRequired.equalsIgnoreCase("S999")){
                map.put("result", "S007");
                map.put("msg", APIEnumDefine.enumValue("S007"));
                map.put("body","接口"+APIName+"的参数"+paramRequired+"不能为空");
                return map;
            }else{
                return null;
            }
        }
    }


    private String getAPIName(Method method){
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        for(Annotation annotation:declaredAnnotations){
            if(annotation.annotationType() == RequestMapping.class){
                RequestMapping requestMapping = (RequestMapping)annotation;
                return requestMapping.value()[0].replace("/","");
            }
        }
        return null;
    }

    private String validateParamRequired(Map<String, String[]> parameterMap,List<Annotation> apiParams){
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, String[]> next = iterator.next();
            String key = next.getKey();
            String[] value = next.getValue();
            for(Annotation annotation:apiParams){
                ApiParam apiParam = (ApiParam)annotation;
                if(apiParam.name().equalsIgnoreCase(key) && apiParam.required()){
                    if(StringUtils.isEmpty(value[0])){
                        return apiParam.name();
                    }
                }
            }
        }
        return null;
    }

    private List<Annotation> getAPIAnnotationByMethod(Method method , Class annoClass){
        List<Annotation> annotations = new ArrayList<>();
        Annotation[][] methodAnnon =  method.getParameterAnnotations();
        for (Annotation[] annotation : methodAnnon) {
            for(Annotation annotation1:annotation){
                if(annotation1.annotationType() == annoClass){
                    annotations.add(annotation1);
                }
            }
        }
        return annotations;
    }

    private Method getMethodByClassAndName(Class c , String methodName){
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().equals(methodName)){
                return method ;
            }
        }
        return null;
    }
}
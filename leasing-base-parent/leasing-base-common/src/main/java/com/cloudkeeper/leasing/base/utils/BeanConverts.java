package com.cloudkeeper.leasing.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import javax.annotation.Nonnull;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * bean 转换 工具类
 * @author jerry
 */
@Slf4j
public class BeanConverts {

    private BeanConverts() {
    }

    /**
     * 将对象转换为map
     * @param obj 对象
     * @return 键值对
     */
    public static Map<String, Object> objToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj != null) {
            BeanMap beanMap = BeanMap.create(obj);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * bean 转换
     * @param obj 源对象
     * @param clazz 目标类型
     * @param <T> 泛型
     * @return 目标对象
     */
    public static <T> T convert(@Nonnull Object obj, @Nonnull Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(obj, t);
        return t;
    }

    /**
     * bean 集合转换
     * @param collection 源对象集合
     * @param clazz 目标类型
     * @param <T> 泛型
     * @return 目标对象集合
     */
    @Nonnull
    public static <T> List<T> convert(@Nonnull Collection<?> collection, @Nonnull Class<T> clazz) {
        return collection.stream().map(item -> convert(item, clazz)).collect(Collectors.toList());
    }

    /**
     * map 转对象
     * @param clazz 目标类型
     * @param collection 键值对集合
     * @param <T> 泛型
     * @return 目标对象集合
     */
    @Nonnull
    public static <T> List<T> mapToObj(@Nonnull Class<T> clazz, @Nonnull Collection<Map<String, Object>> collection) {
        return collection.stream().map(map ->  BeanConverts.mapToObj(clazz, map)).collect(Collectors.toList());
    }

    /**
     * map 转对象
     * @param clazz 目标类型
     * @param map 键值对
     * @param <T> 泛型
     * @return 目标对象
     */
    public static <T> T mapToObj(@Nonnull Class<T> clazz, @Nonnull Map<String, Object> map) {
        T d = BeanUtils.instantiateClass(clazz);
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(d.getClass());
        } catch (IntrospectionException e) {
            log.warn(e.getMessage(), e);
            return null;
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter == null) {
                continue;
            }
            Object value = map.get(property.getName());
            if (value instanceof Timestamp) {
                if (property.getPropertyType().equals(LocalDate.class)) {
                    value = ((Timestamp) value).toLocalDateTime().toLocalDate();
                } else {
                    value = ((Timestamp) value).toLocalDateTime();
                }
            } else if (property.getPropertyType().equals(BigDecimal.class)) {
                value = BigDecimal.valueOf((double)value);
            }else if(value instanceof Date){
                Calendar cal = Calendar.getInstance();
                cal.setTime(((Date) value));
                value  = LocalDate.of(cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH) + 1,
                        cal.get(Calendar.DAY_OF_MONTH));;
            }
            try {
                setter.invoke(d, value);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
            }
        }
        return d;
    }

    /**
     * 对象集合中取出两个字段，组成map
     * @param collection 对象集合
     * @param keyProperty key属性值
     * @param valueProperty value属性值
     * @return 属性值map
     */
    @Nonnull
    public static Map<String, String> propertyToMap(@Nonnull Collection collection, @Nonnull String keyProperty, @Nonnull String valueProperty) {
        Map<String, String> map = new LinkedHashMap<>();
        if (collection.isEmpty()) {
            return map;
        }
        Object obj = collection.iterator().next();
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(obj.getClass());
        } catch (IntrospectionException e) {
            log.warn(e.getMessage(), e);
            return map;
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Method keyGetter = null;
        Method valueGetter = null;
        for (PropertyDescriptor property : propertyDescriptors) {
            if (keyProperty.equals(property.getName()) && property.getReadMethod() != null) {
                keyGetter = property.getReadMethod();
            } else if (valueProperty.equals(property.getName()) && property.getReadMethod() != null) {
                valueGetter = property.getReadMethod();
            }
        }
        if (keyGetter == null || valueGetter == null) {
            return map;
        }
        for (Object item : collection) {
            try {
                map.put(keyGetter.invoke(item).toString(), valueGetter.invoke(item).toString());
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.warn(e.getMessage(), e);
            }
        }
        return map;
    }
}

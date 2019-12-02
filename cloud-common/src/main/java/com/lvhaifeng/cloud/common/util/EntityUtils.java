package com.lvhaifeng.cloud.common.util;

import com.ace.cache.utils.ReflectionUtils;
import com.lvhaifeng.cloud.common.annotation.*;
import com.lvhaifeng.cloud.common.context.BaseContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.reflect.Field;
import java.util.*;


/**
 * 1、快速对实体的常驻字段，如：crtUser、crtHost、updUser等值快速注入
 */
@Slf4j
public class EntityUtils {

    public static <T> void setCreatAndUpdatInfo(T entity) {
        setCreateInfo(entity);
        setUpdatedInfo(entity);
    }

    public static <T> void setCreateInfo(T entity) {
        String userName = BaseContextHandler.getUserName();
        String userId = BaseContextHandler.getUserID();

        // 默认属性
        String[] fieldNames = {"crtUserName", "crtUserId", "crtTime", "departId"};
        if (entity.getClass().getAnnotation(AceAudit.class) != null) {
            Field[] fields = entity.getClass().getDeclaredFields();
            if (fields != null) {
                for (Field field : fields) {
                    if (field.getAnnotation(CrtUserName.class) != null) {
                        fieldNames[0] = field.getName();
                        continue;
                    }
                    if (field.getAnnotation(CrtUserId.class) != null) {
                        fieldNames[1] = field.getName();
                        continue;
                    }
                    if (field.getAnnotation(CrtTime.class) != null) {
                        fieldNames[2] = field.getName();
                        continue;
                    }
                }
            }
        }
        Field field = ReflectionUtils.getAccessibleField(entity, "crtTime");
        // 默认值
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{userName, userId, new LocalDateTime()};
        }
        // 填充默认属性值
        setDefaultValues(entity, fieldNames, value);
    }

    public static <T> void setUpdatedInfo(T entity) {
        String userName = BaseContextHandler.getUserName();
        String userId = BaseContextHandler.getUserID();
        // 默认属性
        String[] fieldNames = {"updUserName", "updUserId", "updTime"};
        if (entity.getClass().getAnnotation(AceAudit.class) != null) {
            Field[] fields = entity.getClass().getDeclaredFields();
            if (fields != null) {
                for (Field field : fields) {
                    if (field.getAnnotation(ModifiedUserName.class) != null) {
                        fieldNames[0] = field.getName();
                        continue;
                    }
                    if (field.getAnnotation(ModifiedUserId.class) != null) {
                        fieldNames[1] = field.getName();
                        continue;
                    }
                    if (field.getAnnotation(ModifiedTime.class) != null) {
                        fieldNames[2] = field.getName();
                        continue;
                    }
                }
            }
        }
        Field field = ReflectionUtils.getAccessibleField(entity, "updTime");
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{userName, userId, new LocalDateTime()};
        }
        // 填充默认属性值
        setDefaultValues(entity, fieldNames, value);
    }

    private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            try {
                if (ReflectionUtils.hasField(entity, field)) {
                    ReflectionUtils.invokeSetter(entity, field, value[i]);
                }
            } catch (Exception e) {
                sb.append(field).append(" ");
            }
        }
        if (!sb.toString().isEmpty()) {
            // log.error(entity.getClass().getName() + ",部分字段审计失败: " + sb.toString());
        }
    }

    public static <T> boolean isPKNotNull(T entity, String field) {
        if (!ReflectionUtils.hasField(entity, field)) {
            return false;
        }
        Object value = ReflectionUtils.getFieldValue(entity, field);
        return value != null && !"".equals(value);
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static Map<String, Object> getPropertyMap(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Map<String, Object> resultMap = new HashMap<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null && !srcValue.toString().equals("")) {
                resultMap.put(pd.getName(), srcValue);
            }
        }
        return resultMap;
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static void copyPropertyFromMap(Map<String, Object> srcMap, Object target) {
        if (srcMap != null && !srcMap.isEmpty()) {
            final BeanWrapper tar = new BeanWrapperImpl(target);
            for (Map.Entry<String, Object> entry : srcMap.entrySet()) {
                tar.setPropertyValue(entry.getKey(), entry.getValue());
            }
        }
    }

}


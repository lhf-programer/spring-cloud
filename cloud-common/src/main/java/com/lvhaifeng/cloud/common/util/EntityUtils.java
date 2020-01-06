package com.lvhaifeng.cloud.common.util;

import com.lvhaifeng.cloud.common.context.BaseContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * @Description 快速注入
 * @Author haifeng.lv
 * @Date 2020/1/4 16:28
 */
@Slf4j
public class EntityUtils {
    private static final String SETTER_PREFIX = "set";
    private static final String GETTER_PREFIX = "get";

    public static <T> void setDefaultValue(T entity) {
        String userName = BaseContextHandler.getUserName() != null?BaseContextHandler.getUserName():"guest";
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if (ReflectionUtils.hasField(entity, name)) {
                switch (name) {
                    case "crtUser":
                        // 创建人
                        String crtName = (String) ReflectionUtils.invokeGetter(entity, name);
                        if (StringUtils.isBlank(crtName)) {
                            ReflectionUtils.invokeSetter(entity, name, userName);
                        }
                        break;
                    case "crtTime":
                        // 创建人
                        LocalDateTime crtTime = (LocalDateTime) ReflectionUtils.invokeGetter(entity, name);
                        if (null == crtTime) {
                            ReflectionUtils.invokeSetter(entity, name, LocalDateTime.now());
                        }
                        break;
                    case "updUser":
                        // 修改人
                        ReflectionUtils.invokeSetter(entity, name, userName);
                        break;
                    case "updTime":
                        // 修改时间
                        ReflectionUtils.invokeSetter(entity, name, LocalDateTime.now());
                        break;
                    default:
                        break;
                }
            }
        }
    }

}


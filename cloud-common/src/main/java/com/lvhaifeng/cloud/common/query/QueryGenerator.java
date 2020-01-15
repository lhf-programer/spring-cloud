package com.lvhaifeng.cloud.common.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lvhaifeng.cloud.common.constant.CommonConstant;
import com.lvhaifeng.cloud.common.constant.OrderTypeConstant;
import com.lvhaifeng.cloud.common.util.ConvertUtils;
import com.lvhaifeng.cloud.common.util.SqlInjectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * @Description
 * @Author haifeng.lv
 * @Date 2020/1/10 16:07
 */
@Slf4j
public class QueryGenerator {
    /**
     * @Description 获取查询条件构造器QueryWrapper实例 通用查询条件已被封装完成
     * @Author haifeng.lv
     * @param: searchObj
     * @param: sortProp
     * @param: sortType
     * @Date 2020/1/13 17:04
     * @return: com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T>
     */
    public static <T> QueryWrapper<T> initQueryWrapper(T searchObj, String sortProp, String sortType) {
        long start = System.currentTimeMillis();
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        installPlus(queryWrapper, searchObj, sortProp, sortType);
        log.debug("---查询条件构造器初始化完成,耗时:" + (System.currentTimeMillis() - start) + "毫秒----");
        return queryWrapper;
    }

    public static void installPlus(QueryWrapper<?> queryWrapper, Object searchObj, String sortProp, String sortType) {
        PropertyDescriptor originDescriptors[] = PropertyUtils.getPropertyDescriptors(searchObj);

        String name;
        for (int i = 0; i < originDescriptors.length; i++) {
            name = originDescriptors[i].getName();
            try {
                if (judgedIsUselessField(name) || !PropertyUtils.isReadable(searchObj, name)) {
                    continue;
                }

                Object value = PropertyUtils.getSimpleProperty(searchObj, name);
                if (null != value) {
                    final String field = ConvertUtils.camelToUnderline(name);
                    queryWrapper.like(field, value);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        // 排序逻辑 处理
        doMultiFieldsOrder(queryWrapper, sortProp, sortType);
    }

    public static void doMultiFieldsOrder(QueryWrapper<?> queryWrapper, String sortProp, String sortType) {
        log.debug("排序规则>>列:" + sortProp + ",排序方式:" + sortType);
        if (ConvertUtils.isNotEmpty(sortProp) && ConvertUtils.isNotEmpty(sortType)) {
            String[] props = sortProp.split(",");
            String[] types = sortType.split(",");
            if (props.length == types.length) {
                for (int i = 0; i < props.length; i++) {
                    //SQL注入check
                    SqlInjectionUtil.filterContent(props[i]);

                    if (types[i].indexOf(OrderTypeConstant.ORDER_TYPE_ASC) >= 0) {
                        queryWrapper.orderByAsc(ConvertUtils.camelToUnderline(props[i]));
                    } else {
                        queryWrapper.orderByDesc(ConvertUtils.camelToUnderline(props[i]));
                    }
                }
            }
        }
    }

    private static boolean judgedIsUselessField(String name) {
        return "class".equals(name) || "pageNo".equals(name) || "pageSize".equals(name);
    }

}

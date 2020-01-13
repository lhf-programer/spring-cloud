package com.lvhaifeng.cloud.common.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lvhaifeng.cloud.common.constant.CommonConstant;
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
     * 排序列
     */
    private static final String ORDER_COLUMN = "sortProp";

    /**
     * 排序方式
     */
    private static final String ORDER_TYPE = "sortType";

    private static final String ORDER_TYPE_ASC = "ascending";

    /**
     * @Description 获取查询条件构造器QueryWrapper实例 通用查询条件已被封装完成
     * @Author haifeng.lv
     * @param: searchObj
     * @param: parameterMap
     * @Date 2020/1/10 16:02
     * @return: com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T>
     */
    public static <T> QueryWrapper<T> initQueryWrapper(T searchObj, Map<String, String[]> parameterMap) {
        long start = System.currentTimeMillis();
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        installPlus(queryWrapper, searchObj, parameterMap);
        log.debug("---查询条件构造器初始化完成,耗时:" + (System.currentTimeMillis() - start) + "毫秒----");
        return queryWrapper;
    }

    public static void installPlus(QueryWrapper<?> queryWrapper, Object searchObj, Map<String, String[]> parameterMap) {
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
        doMultiFieldsOrder(queryWrapper, parameterMap);
    }

    public static void doMultiFieldsOrder(QueryWrapper<?> queryWrapper, Map<String, String[]> parameterMap) {
        String column = null, order = null;
        if (parameterMap != null && parameterMap.containsKey(ORDER_COLUMN)) {
            column = parameterMap.get(ORDER_COLUMN)[0];
        }
        if (parameterMap != null && parameterMap.containsKey(ORDER_TYPE)) {
            order = parameterMap.get(ORDER_TYPE)[0];
        }
        log.debug("排序规则>>列:" + column + ",排序方式:" + order);
        if (ConvertUtils.isNotEmpty(column) && ConvertUtils.isNotEmpty(order)) {
            //SQL注入check
            SqlInjectionUtil.filterContent(column);

            if (order.indexOf(ORDER_TYPE_ASC) >= 0) {
                queryWrapper.orderByAsc(ConvertUtils.camelToUnderline(column));
            } else {
                queryWrapper.orderByDesc(ConvertUtils.camelToUnderline(column));
            }
        }
    }

    private static boolean judgedIsUselessField(String name) {
        return "class".equals(name) || "pageNo".equals(name) || "pageSize".equals(name);
    }

}

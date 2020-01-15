package com.lvhaifeng.cloud.common.util;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.lvhaifeng.cloud.common.constant.OrderTypeConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 排序工具类
 * @Author haifeng.lv
 * @Date 2020/1/13 18:13
 */
public class SortUtils {
    /**
     * @Description 解析排序
     * @Author haifeng.lv
     * @param: sortProp 字段
     * @param: sortType 类型
     * @Date 2020/1/14 11:33
     * @return: java.util.List<com.baomidou.mybatisplus.core.metadata.OrderItem>
     */
    public static List<OrderItem> resolverSort(String sortProp, String sortType) {
        List<OrderItem> orderItems = new ArrayList<>();

        if (StringUtils.isNotEmpty(sortProp) && StringUtils.isNotEmpty(sortType)) {
            String[] props = sortProp.split(",");
            String[] types = sortType.split(",");
            if (props.length == types.length) {
                for (int i = 0; i < props.length; i++) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setColumn(props[i]);
                    orderItem.setAsc(types[i].indexOf(OrderTypeConstant.ORDER_TYPE_ASC) >= 0?true:false);
                    orderItems.add(orderItem);
                }
            }
        }

        return orderItems;
    }

}

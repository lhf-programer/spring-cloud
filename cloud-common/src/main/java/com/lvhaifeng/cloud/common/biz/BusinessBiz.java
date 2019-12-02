package com.lvhaifeng.cloud.common.biz;


import com.lvhaifeng.cloud.common.mapper.CommonMapper;
import com.lvhaifeng.cloud.common.util.EntityUtils;

/**
 * 基础业务类
 * @author haifeng.lv
 * @version 2018/1/13.
 */
public abstract class BusinessBiz<M extends CommonMapper<T>, T>  extends BaseBiz<M, T>  {
    @Override
    public void insertSelective(T entity) {
        EntityUtils.setCreatAndUpdatInfo(entity);
        super.insertSelective(entity);
    }

    @Override
    public void updateById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        super.updateById(entity);
    }

    @Override
    public void updateSelectiveById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        super.updateSelectiveById(entity);
    }
}

package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.entity.User;
import com.lvhaifeng.cloud.admin.mapper.UserMapper;
import com.lvhaifeng.cloud.admin.service.IUserService;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 用户
 * @Author: haifeng.lv
 * @Date: 2020-01-06 11:34
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(User user) {
        EntityUtils.setDefaultValue(user);
        return super.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(User user) {
        User userEntity = baseMapper.selectById(user.getId());
        if(userEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(user, userEntity);
        }
        EntityUtils.setDefaultValue(userEntity);
        return super.updateById(userEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        if(ids.isEmpty()) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        }else {
            return super.removeByIds(ids);
        }
    }

    @Override
    public User getById(Serializable id) {
        User user = super.getById(id);
        if (null == user) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return user;
        }
    }
}

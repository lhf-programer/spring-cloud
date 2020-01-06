package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.entity.Button;
import com.lvhaifeng.cloud.admin.mapper.ButtonMapper;
import com.lvhaifeng.cloud.admin.service.IButtonService;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:20
 */
@Service
public class ButtonServiceImpl extends ServiceImpl<ButtonMapper, Button> implements IButtonService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<Button> pageButtonList(Button button, Integer pageNo, Integer pageSize) {
        QueryWrapper<Button> queryWrapper = new QueryWrapper<>();
        Page<Button> page = new Page<>(pageNo, pageSize);
        IPage<Button> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveButton(Button button) {
        EntityUtils.setDefaultValue(button);
        return super.save(button);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByButtonId(Button button) {
        Button buttonEntity = baseMapper.selectById(button.getId());
        if(buttonEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(button, buttonEntity);
        }
        EntityUtils.setDefaultValue(buttonEntity);
        return super.updateById(buttonEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByButtonId(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByButtonIds(Collection<? extends Serializable> ids) {
        if(ids.isEmpty()) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        }else {
            return super.removeByIds(ids);
        }
    }

    @Override
    public Button getByButtonId(Serializable id) {
        Button button = super.getById(id);
        if (null == button) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return button;
        }
    }
}

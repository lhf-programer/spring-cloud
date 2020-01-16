package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.entity.Button;
import com.lvhaifeng.cloud.admin.mapper.ButtonMapper;
import com.lvhaifeng.cloud.admin.service.IButtonService;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.query.QueryGenerator;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:28
 */
@Service
public class ButtonServiceImpl extends ServiceImpl<ButtonMapper, Button> implements IButtonService {
    @Resource
    private ButtonMapper buttonMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<Button> findButtonPageList(Button button, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<Button> queryWrapper = QueryGenerator.initQueryWrapper(button, sortProp, sortType);
        Page<Button> page = new Page<>(pageNo, pageSize);
        IPage<Button> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createButton(Button button) {
        EntityUtils.setDefaultValue(button);
        return super.save(button);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterButtonById(Button button) {
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
    public boolean dropButtonById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropButtonBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public Button findButtonById(Serializable id) {
        Button button = super.getById(id);
        if (null == button) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return button;
        }
    }

    /**
     * @Description 查询所有按钮通过角色 id
     * @Author haifeng.lv
     * @param: roleId 角色 id
     * @param: menuId 菜单 id
     * @Date 2020/1/15 17:03
     * @return: java.util.List<com.lvhaifeng.cloud.admin.entity.Button>
     */
    @Override
    public List<Button> findAllButtonsById(String roleId, String menuId) {
        return buttonMapper.selectAllButtonsByRoleId(roleId, menuId);
    }
}

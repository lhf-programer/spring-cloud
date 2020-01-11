package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.entity.Menu;
import com.lvhaifeng.cloud.admin.entity.Role;
import com.lvhaifeng.cloud.admin.entity.User;
import com.lvhaifeng.cloud.admin.entity.UserRole;
import com.lvhaifeng.cloud.admin.mapper.UserMapper;
import com.lvhaifeng.cloud.admin.service.IMenuService;
import com.lvhaifeng.cloud.admin.service.IRoleService;
import com.lvhaifeng.cloud.admin.service.IUserRoleService;
import com.lvhaifeng.cloud.admin.service.IUserService;
import com.lvhaifeng.cloud.admin.vo.response.MenuInfo;
import com.lvhaifeng.cloud.admin.vo.response.UserInfo;
import com.lvhaifeng.cloud.auth.user.service.AuthUserService;
import com.lvhaifeng.cloud.common.auth.AuthHelper;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.error.ErrorCode;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.exception.auth.NonLoginException;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import com.lvhaifeng.cloud.common.vo.AuthInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 用户
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<User> pageUserList(User user, Integer pageNo, Integer pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Page<User> page = new Page<>(pageNo, pageSize);
        IPage<User> pageList = baseMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(User user) {
        EntityUtils.setDefaultValue(user);
        return super.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByUserId(User user) {
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
    public boolean removeByUserId(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByUserIds(Collection<? extends Serializable> ids) {
        if(ids.isEmpty()) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return super.removeByIds(ids);
        }
    }

    @Override
    public User getByUserId(Serializable id) {
        User user = super.getById(id);
        if (null == user) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            return user;
        }
    }

    /**
     * @Description 通过token 来获取用户信息
     * @Author haifeng.lv
     * @param: token
     * @Date 2020/1/6 17:06
     * @return: com.lvhaifeng.cloud.admin.vo.response.UserInfo
     */
    @Override
    public UserInfo findUserInfoByToken(String token) throws Exception {
        UserInfo userInfo = new UserInfo();

        AuthInfo authInfo = authUserService.getInfoFromToken(token);
        User user = baseMapper.selectById(authInfo.getId());
        userInfo.setName(user.getUsername());

        QueryWrapper queryRoleWrapper = new QueryWrapper();
        queryRoleWrapper.eq("user_id", user.getId());
        // 查询所有用户角色
        List<UserRole> userRoles = userRoleService.list(queryRoleWrapper);
        StringBuilder roleNames = new StringBuilder();
        List<MenuInfo> menus = new ArrayList<>();

        userRoles.forEach(userRole -> {
            // 查询角色
            Role role = roleService.findRoleById(userRole.getRoleId());
            roleNames.append(role.getName() + ",");
            List<MenuInfo> menuByRoleId = menuService.getMenuByRoleId(role.getId());
            menus.addAll(menuByRoleId);
        });
        // 用户权限菜单
        userInfo.setMenusList(menus);
        // 用户角色名称
        userInfo.setRoleName(StringUtils.isNotBlank(roleNames.toString())?roleNames.toString().substring(0, roleNames.toString().length() - 1):"");
        // 描述
        userInfo.setDescription(user.getDescription());

        return userInfo;
    }
}

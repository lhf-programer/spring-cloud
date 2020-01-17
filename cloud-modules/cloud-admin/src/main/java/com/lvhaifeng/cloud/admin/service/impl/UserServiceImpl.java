package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.constant.ErrCodeConstant;
import com.lvhaifeng.cloud.admin.entity.Role;
import com.lvhaifeng.cloud.admin.entity.User;
import com.lvhaifeng.cloud.admin.entity.UserRole;
import com.lvhaifeng.cloud.admin.mapper.UserMapper;
import com.lvhaifeng.cloud.admin.mapper.UserRoleMapper;
import com.lvhaifeng.cloud.admin.service.IMenuService;
import com.lvhaifeng.cloud.admin.service.IRoleService;
import com.lvhaifeng.cloud.admin.service.IUserRoleService;
import com.lvhaifeng.cloud.admin.service.IUserService;
import com.lvhaifeng.cloud.admin.vo.response.MenuInfo;
import com.lvhaifeng.cloud.admin.vo.response.UserInfo;
import com.lvhaifeng.cloud.auth.user.service.AuthUserService;
import com.lvhaifeng.cloud.common.encoder.Sha256PasswordEncoder;
import com.lvhaifeng.cloud.common.error.ErrCodeBaseConstant;
import com.lvhaifeng.cloud.common.exception.BusinessException;
import com.lvhaifeng.cloud.common.query.QueryGenerator;
import com.lvhaifeng.cloud.common.util.EntityUtils;
import com.lvhaifeng.cloud.common.vo.AuthInfo;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 用户
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private IUserRoleService userRoleService;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    private Sha256PasswordEncoder sha256PasswordEncoder = new Sha256PasswordEncoder();
    private static final String ADMIN = "admin";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IPage<UserInfo> findUserPageList(User user, Integer pageNo, Integer pageSize, String sortProp, String sortType) {
        QueryWrapper<User> queryWrapperUser = QueryGenerator.initQueryWrapper(user, sortProp, sortType);
        Page<User> page = new Page<>(pageNo, pageSize);
        IPage<User> pageList = baseMapper.selectPage(page, queryWrapperUser);
        List<User> userList = pageList.getRecords();
        List<UserInfo> userInfos = userList.stream().map(record -> {
            UserInfo userInfo = getRoleInfo(record);
            return userInfo;
        }).collect(Collectors.toList());
        IPage<UserInfo> response = new Page<>();
        BeanUtils.copyProperties(pageList, response);
        response.setRecords(userInfos);

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createUser(UserInfo userInfo) {
        QueryWrapper queryWrapper = verify(userInfo);
        List<User> users = baseMapper.selectList(queryWrapper);
        if (!Collections.isEmpty(users)) {
            throw new BusinessException(ErrCodeConstant.REPEAT_USER_ERR);
        }
        if (StringUtils.isBlank(userInfo.getPassword())) {
            throw new BusinessException(ErrCodeConstant.NO_PASSWORD_ERR);
        }

        User user = new User();
        BeanUtils.copyProperties(userInfo, user);
        // 密码加密
        user.setPassword(sha256PasswordEncoder.encode(userInfo.getPassword()));
        EntityUtils.setDefaultValue(user);
        if (super.save(user)) {
            List<String> roleIds = userInfo.getRoleId();
            userRoleService.insertBatch(roleIds, user.getId());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean alterUserById(UserInfo userInfo) {
        QueryWrapper queryWrapperUser = verify(userInfo);
        queryWrapperUser.ne("id", userInfo.getId());
        List<User> users = baseMapper.selectList(queryWrapperUser);
        if (!Collections.isEmpty(users)) {
            throw new BusinessException(ErrCodeConstant.REPEAT_USER_ERR);
        }

        User userEntity = baseMapper.selectById(userInfo.getId());
        if(userEntity == null) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            BeanUtils.copyProperties(userInfo, userEntity);
            // 密码加密
            userEntity.setPassword(sha256PasswordEncoder.encode(userInfo.getPassword()));
            QueryWrapper queryWrapperUserRole = new QueryWrapper();
            queryWrapperUserRole.eq("user_id", userInfo.getId());
            userRoleMapper.delete(queryWrapperUserRole);

            List<String> roleIds = userInfo.getRoleId();
            userRoleService.insertBatch(roleIds, userInfo.getId());
        }
        EntityUtils.setDefaultValue(userEntity);
        return super.updateById(userEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropUserById(String id) {
        User user = baseMapper.selectById(id);
        if (null == user || ADMIN.equals(user.getUsername())) {
            throw new BusinessException(ErrCodeConstant.NO_ADMIN_PERMIT_ERR);
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", id);
        userRoleMapper.delete(queryWrapper);
        return super.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean dropUserBatch(String ids) {
        if(StringUtils.isBlank(ids)) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            QueryWrapper queryWrapperUser = new QueryWrapper();
            queryWrapperUser.in("id", Arrays.asList(ids.split(",")));
            List<User> users = baseMapper.selectList(queryWrapperUser);
            if (Collections.isEmpty(users)) {
                throw new BusinessException(ErrCodeConstant.NO_USER_ERR);
            } else {
                users.forEach(user -> {
                    if (ADMIN.equals(user.getUsername())) {
                        throw new BusinessException(ErrCodeConstant.NO_ADMIN_PERMIT_ERR);
                    }
                });
            }

            QueryWrapper queryWrapperUserRole = new QueryWrapper();
            queryWrapperUserRole.in("user_id", Arrays.asList(ids.split(",")));
            userRoleMapper.delete(queryWrapperUserRole);
            return super.removeByIds(Arrays.asList(ids.split(",")));
        }
    }

    @Override
    public UserInfo findUserById(String id) {
        User user = super.getById(id);
        if (null == user) {
            throw new BusinessException(ErrCodeBaseConstant.COMMON_PARAM_ERR);
        } else {
            UserInfo userInfo = getRoleInfo(user);
            return userInfo;
        }
    }

    /**
     * @Description 获取用户信息
     * @Author haifeng.lv
     * @param: user
     * @Date 2020/1/17 11:26
     * @return: com.lvhaifeng.cloud.admin.vo.response.UserInfo
     */
    public UserInfo getRoleInfo(User user) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        QueryWrapper queryWrapperUserRole = new QueryWrapper();
        queryWrapperUserRole.eq("user_id", user.getId());
        List<UserRole> userRoles = userRoleService.list(queryWrapperUserRole);
        if (!Collections.isEmpty(userRoles)) {
            List<String> roleIds = new ArrayList<>();
            StringBuilder roleNames = new StringBuilder();
            userRoles.forEach(userRole -> {
                Role role = roleService.findRoleById(userRole.getRoleId());
                roleIds.add(role.getId());
                roleNames.append(role.getName() + ",");
            });
            userInfo.setRoleId(roleIds);
            userInfo.setRoleName(StringUtils.isNotBlank(roleNames.toString())?roleNames.toString().substring(0, roleNames.toString().length() - 1):"");
        }
        return userInfo;
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
        userInfo.setUsername(user.getUsername());

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

    /**
     * @Description 用户密码修改
     * @Author haifeng.lv
     * @param: userInfo
     * @Date 2020/1/17 15:41
     */
    @Override
    public void alterPasswordById(UserInfo userInfo) {
        User user = getById(userInfo.getId());
        if (null == user) {
            throw new BusinessException(ErrCodeConstant.NO_USER_ERR);
        }
        // 密码加密
        user.setPassword(sha256PasswordEncoder.encode(userInfo.getPassword()));
        baseMapper.updateById(user);
    }

    /**
     * @Description 验证 获取操作 Wrapper
     * @Author haifeng.lv
     * @Date 2020/1/17 10:46
     */
    public QueryWrapper verify(UserInfo userInfo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", userInfo.getUsername());
        return queryWrapper;
    }
}

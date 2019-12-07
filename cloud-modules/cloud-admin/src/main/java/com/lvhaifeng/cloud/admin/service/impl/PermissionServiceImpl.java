package com.lvhaifeng.cloud.admin.service.impl;

import com.lvhaifeng.cloud.admin.entity.Permission;
import com.lvhaifeng.cloud.admin.mapper.PermissionMapper;
import com.lvhaifeng.cloud.admin.service.IPermissionService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 权限
 * @Author: haifeng.lv
 * @Date:   2019-12-06
 * @Version: V1.0
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}

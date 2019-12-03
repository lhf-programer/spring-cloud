package com.lvhaifeng.cloud.admin.biz;


import com.lvhaifeng.cloud.admin.entity.BaseUser;
import com.lvhaifeng.cloud.admin.mapper.BaseRoleMapper;
import com.lvhaifeng.cloud.admin.mapper.BaseUserMapper;
import com.lvhaifeng.cloud.common.biz.BusinessBiz;
import com.lvhaifeng.cloud.common.util.Sha256PasswordEncoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author haifeng.lv
 */
@Service
public class BaseUserBiz extends BusinessBiz<BaseUserMapper, BaseUser> {
    @Autowired
    BaseUserRoleBiz userRoleBiz;

    private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();

    /**
     * 根据用户名称获取用户信息
     *
     * @author haifeng.lv
     * @date 2019-07-27 14:44
     */
    public BaseUser getUserByUsername(String userName) {
        if (StringUtils.isBlank(userName)) {
            return null;
        }
        BaseUser user = new BaseUser();
        user.setLoginName(userName);
        return mapper.selectOne(user);
    }

}

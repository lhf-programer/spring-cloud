package com.lvhaifeng.cloud.admin.mapper;

import com.lvhaifeng.cloud.admin.entity.BaseUser;
import com.lvhaifeng.cloud.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户处理类
 *
 * @author admin
 */
public interface BaseUserMapper extends CommonMapper<BaseUser> {
    /**
     * 获取用户列表
     *
     * @author haifeng.lv
     * @date 2019-07-30 16:14
     */
    List<BaseUser> selectUserPage(@Param("keyword") String keyword);

    /**
     * 获取用户列表
     *
     * @author haifeng.lv
     * @date 2019-07-30 20:14
     */
    List<BaseUser> selectUserListByIds(@Param("userIds") List<String> userIds);

    /**
     * 新建用户保存,保存成功返回主键
     *
     * @author haifeng.lv
     * @date 2019-08-03 15:34
     */
    boolean insertUser(@Param("param") BaseUser user);
}

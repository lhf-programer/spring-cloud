package com.lvhaifeng.cloud.admin.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lvhaifeng.cloud.admin.vo.response.UserInfo;
import com.lvhaifeng.cloud.api.vo.system.AuthUser;
import com.lvhaifeng.cloud.auth.client.annotation.IgnoreClientToken;
import com.lvhaifeng.cloud.auth.user.annotation.IgnoreUserToken;
import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.User;
import com.lvhaifeng.cloud.admin.service.IUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.lvhaifeng.cloud.auth.client.annotation.CheckClientToken;
import com.lvhaifeng.cloud.auth.user.annotation.CheckUserToken;

 /**
 * @Description: 用户
 * @Author: haifeng.lv
 * @Date: 2020-01-13 14:37
 */
@Slf4j
@Api(tags="用户")
@RestController
@RequestMapping("/user")
@CheckClientToken
@CheckUserToken
public class UserController {
	@Autowired
	private IUserService userService;

	/**
	* @Description 登入通过用户名获取用户信息
	* @Author haifeng.lv
	* @param: username
	* @Date 2019/12/16 16:43
	* @return: com.lvhaifeng.cloud.common.vo.Result<com.lvhaifeng.cloud.api.vo.system.AuthUser>
	*/
	@IgnoreClientToken
	@IgnoreUserToken
	@PostMapping("/getUserInfoByUsername")
	public Result<AuthUser> getUserInfoByUsername(@RequestParam("username") String username) {
		Result<AuthUser> result = new Result();
		AuthUser authUser = new AuthUser();

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("username", username);
		List<User> users = userService.list(queryWrapper);
		if (!users.isEmpty() && users.size() == 1) {
			User user = users.get(0);
			authUser.setId(user.getId());
			authUser.setUsername(username);
			authUser.setPassword(user.getPassword());
			authUser.setName(user.getRealname());
			result.setResult(authUser);
		} else {
			result.error500("没有查询到该用户");
		}
		return result;
	}

	/**
	 * @Description 通过token 来获取用户信息
	 * @Author haifeng.lv
	 * @param: token
	 * @Date 2020/1/6 17:06
	 * @return: com.lvhaifeng.cloud.common.vo.Result<com.lvhaifeng.cloud.admin.vo.response.UserInfo>
	 */
	@IgnoreClientToken
	@IgnoreUserToken
	@GetMapping("/getUserInfoByToken")
	public Result<UserInfo> getUserInfoByToken(@RequestParam("token") String token) throws Exception {
		Result<UserInfo> result = new Result();
		UserInfo userInfo = userService.findUserInfoByToken(token);
		result.setResult(userInfo);
		result.setSuccess(true);
		return result;
	}

	/**
	 * 分页列表查询
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="用户-分页列表查询", notes="用户-分页列表查询")
	@GetMapping(value = "/getUserPageList")
	public Result<IPage<User>> getUserPageList(User user,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
        Result<IPage<User>> result = new Result<>();
		IPage<User> pageList = userService.findUserPageList(user, pageNo, pageSize, req);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	 * 添加
	 * @param user
	 * @return
	 */
	@ApiOperation(value="用户-添加", notes="用户-添加")
	@PostMapping(value = "/generateUser")
	public Result<User> generateUser(@RequestBody User user) {
		Result<User> result = new Result<>();
		try {
			userService.createUser(user);
			result.success("添加成功！");
		} catch (Exception e) {
            e.printStackTrace();
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	 * 编辑
	 * @param user
	 * @return
	 */
	@ApiOperation(value="用户-编辑", notes="用户-编辑")
	@PutMapping(value = "/changeUserById")
	public Result<User> changeUserById(@RequestBody User user) {
		Result<User> result = new Result<>();
		try {
            userService.alterUserById(user);
            result.success("编辑成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }

		return result;
	}
	
	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="用户-通过id删除", notes="用户-通过id删除")
	@DeleteMapping(value = "/expurgateUserById")
	public Result<?> expurgateUserById(@RequestParam(name="id",required=true) String id) {
		try {
			userService.dropUserById(id);
		} catch (Exception e) {
		    e.printStackTrace();
			log.error("删除失败", e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="用户-批量删除", notes="用户-批量删除")
	@DeleteMapping(value = "/expurgateUserBatch")
	public Result<?> expurgateUserBatch(@RequestParam(name="ids",required=true) String ids) {
        try {
            userService.dropUserBatch(ids);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
		return Result.ok("删除成功!");
	}
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="用户-通过id查询", notes="用户-通过id查询")
	@GetMapping(value = "/getUserById")
	public Result<User> getUserById(@RequestParam(name="id",required=true) String id) {
		Result<User> result = new Result<>();
		User user = userService.findUserById(id);
        result.setResult(user);
        result.setSuccess(true);
		return result;
	}

}

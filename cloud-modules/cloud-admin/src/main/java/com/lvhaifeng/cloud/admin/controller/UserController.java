package com.lvhaifeng.cloud.admin.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lvhaifeng.cloud.admin.vo.response.UserInfo;
import com.lvhaifeng.cloud.api.vo.system.AuthUser;
import com.lvhaifeng.cloud.auth.user.annotation.IgnoreUserToken;
import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.User;
import com.lvhaifeng.cloud.admin.service.IUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.jsonwebtoken.lang.Collections;
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
 * @Date: 2020-01-13 17:27
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
	@IgnoreUserToken
	@PostMapping("/getUserInfoByUsername")
	public Result<AuthUser> getUserInfoByUsername(@RequestParam("username") String username) {
		Result<AuthUser> result = new Result();
		AuthUser authUser = new AuthUser();

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("username", username);
		List<User> users = userService.list(queryWrapper);
		if (!Collections.isEmpty(users) && users.size() == 1) {
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
	 * @param sortProp
	 * @param sortType
	 * @return
	 */
	@ApiOperation(value="用户-分页列表查询", notes="用户-分页列表查询")
	@GetMapping(value = "/getUserPageList")
	public Result<IPage<UserInfo>> getUserPageList(User user,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                      @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                      @RequestParam(name="sortProp", required = false) String sortProp,
                                      @RequestParam(name="sortType", required = false) String sortType) {
        Result<IPage<UserInfo>> result = new Result<>();
		IPage<UserInfo> pageList = userService.findUserPageList(user, pageNo, pageSize, sortProp, sortType);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	 * 添加
	 * @param userInfo
	 * @return
	 */
	@ApiOperation(value="用户-添加", notes="用户-添加")
	@PostMapping(value = "/generateUser")
	public Result<UserInfo> generateUser(@RequestBody UserInfo userInfo) {
		Result<UserInfo> result = new Result<>();
		userService.createUser(userInfo);
		result.success("添加成功！");
		return result;
	}
	
	/**
	 * 编辑
	 * @param userInfo
	 * @return
	 */
	@ApiOperation(value="用户-编辑", notes="用户-编辑")
	@PutMapping(value = "/changeUserById")
	public Result<User> changeUserById(@RequestBody UserInfo userInfo) {
		Result<User> result = new Result<>();
		userService.alterUserById(userInfo);
		result.success("编辑成功！");
		return result;
	}

	 /**
	  * 用户密码修改
	  * @param userInfo
	  * @return
	  */
	 @ApiOperation(value="用户密码-修改", notes="用户密码-修改")
	 @PutMapping(value = "/changePasswordById")
	 public Result<User> changePasswordById(@RequestBody UserInfo userInfo) {
		 Result<User> result = new Result<>();
		 userService.alterPasswordById(userInfo);
		 result.success("编辑成功！");
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
		userService.dropUserById(id);
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
		userService.dropUserBatch(ids);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="用户-通过id查询", notes="用户-通过id查询")
	@GetMapping(value = "/getUserById")
	public Result<UserInfo> getUserById(@RequestParam(name="id",required=true) String id) {
		Result<UserInfo> result = new Result<>();
		UserInfo userInfo = userService.findUserById(id);
        result.setResult(userInfo);
        result.setSuccess(true);
		return result;
	}

}

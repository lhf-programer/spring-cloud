package com.lvhaifeng.cloud.admin.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import com.lvhaifeng.cloud.api.vo.system.AuthUser;
import com.lvhaifeng.cloud.auth.client.annotation.CheckClientToken;
import com.lvhaifeng.cloud.auth.client.annotation.CheckUserToken;
import com.lvhaifeng.cloud.auth.client.annotation.IgnoreClientToken;
import com.lvhaifeng.cloud.auth.client.annotation.IgnoreUserToken;
import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.User;
import com.lvhaifeng.cloud.admin.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 用户
 * @Author: haifeng.lv
 * @Date:   2019-12-06
 */
@Slf4j
@Api(tags="用户")
@RestController
@CheckClientToken
@CheckUserToken
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;

	 /**
	  * 登入通过用户名获取用户信息
	  * @author haifeng.lv
	  * @date 2019-07-30 10:06
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
	  * 分页列表查询
	 * @param user
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="用户-分页列表查询", notes="用户-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<User>> queryPageList(User user,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<User>> result = new Result<IPage<User>>();
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		Page<User> page = new Page<User>(pageNo, pageSize);
		IPage<User> pageList = userService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param user
	 * @return
	 */
	@ApiOperation(value="用户-添加", notes="用户-添加")
	@PostMapping(value = "/add")
	public Result<User> add(@RequestBody User user) {
		Result<User> result = new Result<User>();
		try {
			userService.save(user);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param user
	 * @return
	 */
	@ApiOperation(value="用户-编辑", notes="用户-编辑")
	@PutMapping(value = "/edit")
	public Result<User> edit(@RequestBody User user) {
		Result<User> result = new Result<User>();
		User userEntity = userService.getById(user.getId());
		if(userEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = userService.updateById(user);
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="用户-通过id删除", notes="用户-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			userService.removeById(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="用户-批量删除", notes="用户-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<User> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<User> result = new Result<User>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.userService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="用户-通过id查询", notes="用户-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<User> queryById(@RequestParam(name="id",required=true) String id) {
		Result<User> result = new Result<User>();
		User user = userService.getById(id);
		if(user==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(user);
			result.setSuccess(true);
		}
		return result;
	}

}

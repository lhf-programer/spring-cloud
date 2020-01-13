package com.lvhaifeng.cloud.admin.controller;

import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.UserRole;
import com.lvhaifeng.cloud.admin.service.IUserRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.lvhaifeng.cloud.auth.client.annotation.CheckClientToken;
import com.lvhaifeng.cloud.auth.user.annotation.CheckUserToken;

 /**
 * @Description: 用户角色
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
@Slf4j
@Api(tags="用户角色")
@RestController
@RequestMapping("/userRole")
@CheckClientToken
@CheckUserToken
public class UserRoleController {
	@Autowired
	private IUserRoleService userRoleService;
	
	/**
	 * 分页列表查询
	 * @param userRole
	 * @param pageNo
	 * @param pageSize
	 * @param sortProp
	 * @param sortType
	 * @return
	 */
	@ApiOperation(value="用户角色-分页列表查询", notes="用户角色-分页列表查询")
	@GetMapping(value = "/getUserRolePageList")
	public Result<IPage<UserRole>> getUserRolePageList(UserRole userRole,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                      @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                      @RequestParam(name="sortProp", required = false) String sortProp,
                                      @RequestParam(name="sortType", required = false) String sortType) {
        Result<IPage<UserRole>> result = new Result<>();
		IPage<UserRole> pageList = userRoleService.findUserRolePageList(userRole, pageNo, pageSize, sortProp, sortType);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	 * 添加
	 * @param userRole
	 * @return
	 */
	@ApiOperation(value="用户角色-添加", notes="用户角色-添加")
	@PostMapping(value = "/generateUserRole")
	public Result<UserRole> generateUserRole(@RequestBody UserRole userRole) {
		Result<UserRole> result = new Result<>();
		try {
			userRoleService.createUserRole(userRole);
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
	 * @param userRole
	 * @return
	 */
	@ApiOperation(value="用户角色-编辑", notes="用户角色-编辑")
	@PutMapping(value = "/changeUserRoleById")
	public Result<UserRole> changeUserRoleById(@RequestBody UserRole userRole) {
		Result<UserRole> result = new Result<>();
		try {
            userRoleService.alterUserRoleById(userRole);
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
	@ApiOperation(value="用户角色-通过id删除", notes="用户角色-通过id删除")
	@DeleteMapping(value = "/expurgateUserRoleById")
	public Result<?> expurgateUserRoleById(@RequestParam(name="id",required=true) String id) {
		try {
			userRoleService.dropUserRoleById(id);
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
	@ApiOperation(value="用户角色-批量删除", notes="用户角色-批量删除")
	@DeleteMapping(value = "/expurgateUserRoleBatch")
	public Result<?> expurgateUserRoleBatch(@RequestParam(name="ids",required=true) String ids) {
        try {
            userRoleService.dropUserRoleBatch(ids);
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
	@ApiOperation(value="用户角色-通过id查询", notes="用户角色-通过id查询")
	@GetMapping(value = "/getUserRoleById")
	public Result<UserRole> getUserRoleById(@RequestParam(name="id",required=true) String id) {
		Result<UserRole> result = new Result<>();
		UserRole userRole = userRoleService.findUserRoleById(id);
        result.setResult(userRole);
        result.setSuccess(true);
		return result;
	}

}

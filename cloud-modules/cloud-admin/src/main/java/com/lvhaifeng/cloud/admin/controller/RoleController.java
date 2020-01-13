package com.lvhaifeng.cloud.admin.controller;

import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.Role;
import com.lvhaifeng.cloud.admin.service.IRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.lvhaifeng.cloud.auth.client.annotation.CheckClientToken;
import com.lvhaifeng.cloud.auth.user.annotation.CheckUserToken;

 /**
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:24
 */
@Slf4j
@Api(tags="角色")
@RestController
@RequestMapping("/role")
@CheckClientToken
@CheckUserToken
public class RoleController {
	@Autowired
	private IRoleService roleService;
	
	/**
	 * 分页列表查询
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="角色-分页列表查询", notes="角色-分页列表查询")
	@GetMapping(value = "/getRolePageList")
	public Result<IPage<Role>> getRolePageList(Role role,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                      @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                      @RequestParam(name="sortProp", required = false) String sortProp,
                                      @RequestParam(name="sortType", required = false) String sortType) {
        Result<IPage<Role>> result = new Result<>();
		IPage<Role> pageList = roleService.findRolePageList(role, pageNo, pageSize, sortProp, sortType);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	@ApiOperation(value="角色-添加", notes="角色-添加")
	@PostMapping(value = "/generateRole")
	public Result<Role> generateRole(@RequestBody Role role) {
		Result<Role> result = new Result<>();
		try {
			roleService.createRole(role);
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
	 * @param role
	 * @return
	 */
	@ApiOperation(value="角色-编辑", notes="角色-编辑")
	@PutMapping(value = "/changeRoleById")
	public Result<Role> changeRoleById(@RequestBody Role role) {
		Result<Role> result = new Result<>();
		try {
            roleService.alterRoleById(role);
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
	@ApiOperation(value="角色-通过id删除", notes="角色-通过id删除")
	@DeleteMapping(value = "/expurgateRoleById")
	public Result<?> expurgateRoleById(@RequestParam(name="id",required=true) String id) {
		try {
			roleService.dropRoleById(id);
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
	@ApiOperation(value="角色-批量删除", notes="角色-批量删除")
	@DeleteMapping(value = "/expurgateRoleBatch")
	public Result<?> expurgateRoleBatch(@RequestParam(name="ids",required=true) String ids) {
        try {
            roleService.dropRoleBatch(ids);
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
	@ApiOperation(value="角色-通过id查询", notes="角色-通过id查询")
	@GetMapping(value = "/getRoleById")
	public Result<Role> getRoleById(@RequestParam(name="id",required=true) String id) {
		Result<Role> result = new Result<>();
		Role role = roleService.findRoleById(id);
        result.setResult(role);
        result.setSuccess(true);
		return result;
	}

}

package com.lvhaifeng.cloud.admin.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.RolePermission;
import com.lvhaifeng.cloud.admin.service.IRolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.lvhaifeng.cloud.auth.client.annotation.CheckClientToken;
import com.lvhaifeng.cloud.auth.user.annotation.CheckUserToken;

 /**
 * @Description: 角色权限
 * @Author: haifeng.lv
 * @Date: 2019-12-19 10:36
 */
@Slf4j
@Api(tags="角色权限")
@RestController
@RequestMapping("/rolePermission")
@CheckClientToken
@CheckUserToken
public class RolePermissionController {
	@Autowired
	private IRolePermissionService rolePermissionService;
	
	/**
	 * 分页列表查询
	 * @param rolePermission
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="角色权限-分页列表查询", notes="角色权限-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RolePermission>> queryPageList(RolePermission rolePermission,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<RolePermission>> result = new Result<>();
		QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
		Page<RolePermission> page = new Page<RolePermission>(pageNo, pageSize);
		IPage<RolePermission> pageList = rolePermissionService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 * 添加
	 * @param rolePermission
	 * @return
	 */
	@ApiOperation(value="角色权限-添加", notes="角色权限-添加")
	@PostMapping(value = "/add")
	public Result<RolePermission> add(@RequestBody RolePermission rolePermission) {
		Result<RolePermission> result = new Result<>();
		try {
			rolePermissionService.save(rolePermission);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	/**
	 * 编辑
	 * @param rolePermission
	 * @return
	 */
	@ApiOperation(value="角色权限-编辑", notes="角色权限-编辑")
	@PutMapping(value = "/edit")
	public Result<RolePermission> edit(@RequestBody RolePermission rolePermission) {
		Result<RolePermission> result = new Result<>();
		RolePermission rolePermissionEntity = rolePermissionService.getById(rolePermission.getId());
		if(rolePermissionEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = rolePermissionService.updateById(rolePermission);
			if(ok) {
				result.success("修改成功!");
			}
		}

		return result;
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="角色权限-通过id删除", notes="角色权限-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			rolePermissionService.removeById(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="角色权限-批量删除", notes="角色权限-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<RolePermission> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<RolePermission> result = new Result<>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.rolePermissionService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="角色权限-通过id查询", notes="角色权限-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RolePermission> queryById(@RequestParam(name="id",required=true) String id) {
		Result<RolePermission> result = new Result<>();
		RolePermission rolePermission = rolePermissionService.getById(id);
		if(rolePermission==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(rolePermission);
			result.setSuccess(true);
		}
		return result;
	}

}

package com.lvhaifeng.cloud.admin.controller;

import com.lvhaifeng.cloud.admin.vo.request.AddRoleResource;
import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.RoleResource;
import com.lvhaifeng.cloud.admin.service.IRoleResourceService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.lvhaifeng.cloud.auth.client.annotation.CheckClientToken;
import com.lvhaifeng.cloud.auth.user.annotation.CheckUserToken;

import java.util.List;

/**
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:26
 */
@Slf4j
@Api(tags="角色资源")
@RestController
@RequestMapping("/roleResource")
@CheckClientToken
@CheckUserToken
public class RoleResourceController {
	@Autowired
	private IRoleResourceService roleResourceService;
	
	/**
	 * 分页列表查询
	 * @param roleResource
	 * @param pageNo
	 * @param pageSize
	 * @param sortProp
	 * @param sortType
	 * @return
	 */
	@ApiOperation(value="角色资源-分页列表查询", notes="角色资源-分页列表查询")
	@GetMapping(value = "/getRoleResourcePageList")
	public Result<IPage<RoleResource>> getRoleResourcePageList(RoleResource roleResource,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                      @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                      @RequestParam(name="sortProp", required = false) String sortProp,
                                      @RequestParam(name="sortType", required = false) String sortType) {
        Result<IPage<RoleResource>> result = new Result<>();
		IPage<RoleResource> pageList = roleResourceService.findRoleResourcePageList(roleResource, pageNo, pageSize, sortProp, sortType);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 * 添加
	 * @param roleResource
	 * @return
	 */
	@ApiOperation(value="角色资源-添加", notes="角色资源-添加")
	@PostMapping(value = "/generateRoleResource")
	public Result<RoleResource> generateRoleResource(@RequestBody RoleResource roleResource) {
		Result<RoleResource> result = new Result<>();
		roleResourceService.createRoleResource(roleResource);
		result.success("添加成功！");
		return result;
	}
	
	/**
	 * 编辑
	 * @param request
	 * @return
	 */
	@ApiOperation(value="角色资源-编辑", notes="角色资源-编辑")
	@PutMapping(value = "/changeRoleResourceByRoleId")
	public Result<RoleResource> changeRoleResourceByRoleId(@RequestBody AddRoleResource request) {
		Result<RoleResource> result = new Result<>();
		roleResourceService.alterRoleResourceByRoleId(request);
		result.success("编辑成功！");
		return result;
	}

	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="角色资源-通过id删除", notes="角色资源-通过id删除")
	@DeleteMapping(value = "/expurgateRoleResourceById")
	public Result<?> expurgateRoleResourceById(@RequestParam(name="id",required=true) String id) {
		roleResourceService.dropRoleResourceById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="角色资源-批量删除", notes="角色资源-批量删除")
	@DeleteMapping(value = "/expurgateRoleResourceBatch")
	public Result<?> expurgateRoleResourceBatch(@RequestParam(name="ids",required=true) String ids) {
		roleResourceService.dropRoleResourceBatch(ids);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="角色资源-通过id查询", notes="角色资源-通过id查询")
	@GetMapping(value = "/getRoleResourceById")
	public Result<RoleResource> getRoleResourceById(@RequestParam(name="id",required=true) String id) {
		Result<RoleResource> result = new Result<>();
		RoleResource roleResource = roleResourceService.findRoleResourceById(id);
        result.setResult(roleResource);
        result.setSuccess(true);
		return result;
	}

}

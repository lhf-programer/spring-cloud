package com.lvhaifeng.cloud.admin.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
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

 /**
 * @Description: 角色资源
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:26
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
	 * @param req
	 * @return
	 */
	@ApiOperation(value="角色资源-分页列表查询", notes="角色资源-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RoleResource>> queryPageList(RoleResource roleResource,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
        Result<IPage<RoleResource>> result = new Result<>();
		IPage<RoleResource> pageList = roleResourceService.pageRoleResourceList(roleResource, pageNo, pageSize);
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
	@PostMapping(value = "/add")
	public Result<RoleResource> add(@RequestBody RoleResource roleResource) {
		Result<RoleResource> result = new Result<>();
		try {
			roleResourceService.saveRoleResource(roleResource);
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
	 * @param roleResource
	 * @return
	 */
	@ApiOperation(value="角色资源-编辑", notes="角色资源-编辑")
	@PutMapping(value = "/edit")
	public Result<RoleResource> edit(@RequestBody RoleResource roleResource) {
		Result<RoleResource> result = new Result<>();
		try {
            roleResourceService.updateByRoleResourceId(roleResource);
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
	@ApiOperation(value="角色资源-通过id删除", notes="角色资源-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			roleResourceService.removeByRoleResourceId(id);
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
	@ApiOperation(value="角色资源-批量删除", notes="角色资源-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) List<String> ids) {
        try {
            roleResourceService.removeByRoleResourceIds(ids);
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
	@ApiOperation(value="角色资源-通过id查询", notes="角色资源-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RoleResource> queryById(@RequestParam(name="id",required=true) String id) {
		Result<RoleResource> result = new Result<>();
		RoleResource roleResource = roleResourceService.getByRoleResourceId(id);
        result.setResult(roleResource);
        result.setSuccess(true);
		return result;
	}

}

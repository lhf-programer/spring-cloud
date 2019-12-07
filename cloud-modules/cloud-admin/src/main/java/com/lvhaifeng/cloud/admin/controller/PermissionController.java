package com.lvhaifeng.cloud.admin.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.Permission;
import com.lvhaifeng.cloud.admin.service.IPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 权限
 * @Author: haifeng.lv
 * @Date:   2019-12-06
 * @Version: V1.0
 */
@Slf4j
@Api(tags="权限")
@RestController
@RequestMapping("/permission")
public class PermissionController {
	@Autowired
	private IPermissionService permissionService;
	
	/**
	  * 分页列表查询
	 * @param permission
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="权限-分页列表查询", notes="权限-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Permission>> queryPageList(Permission permission,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Permission>> result = new Result<IPage<Permission>>();
		QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
		Page<Permission> page = new Page<Permission>(pageNo, pageSize);
		IPage<Permission> pageList = permissionService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param permission
	 * @return
	 */
	@ApiOperation(value="权限-添加", notes="权限-添加")
	@PostMapping(value = "/add")
	public Result<Permission> add(@RequestBody Permission permission) {
		Result<Permission> result = new Result<Permission>();
		try {
			permissionService.save(permission);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param permission
	 * @return
	 */
	@ApiOperation(value="权限-编辑", notes="权限-编辑")
	@PutMapping(value = "/edit")
	public Result<Permission> edit(@RequestBody Permission permission) {
		Result<Permission> result = new Result<Permission>();
		Permission permissionEntity = permissionService.getById(permission.getId());
		if(permissionEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = permissionService.updateById(permission);
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
	@ApiOperation(value="权限-通过id删除", notes="权限-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			permissionService.removeById(id);
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
	@ApiOperation(value="权限-批量删除", notes="权限-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<Permission> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Permission> result = new Result<Permission>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.permissionService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="权限-通过id查询", notes="权限-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Permission> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Permission> result = new Result<Permission>();
		Permission permission = permissionService.getById(id);
		if(permission==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(permission);
			result.setSuccess(true);
		}
		return result;
	}

}

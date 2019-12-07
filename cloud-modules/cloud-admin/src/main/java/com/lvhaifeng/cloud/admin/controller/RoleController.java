package com.lvhaifeng.cloud.admin.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.Role;
import com.lvhaifeng.cloud.admin.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 角色
 * @Author: haifeng.lv
 * @Date:   2019-12-06
 * @Version: V1.0
 */
@Slf4j
@Api(tags="角色")
@RestController
@RequestMapping("/role")
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
	@GetMapping(value = "/list")
	public Result<IPage<Role>> queryPageList(Role role,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Role>> result = new Result<IPage<Role>>();
		QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
		Page<Role> page = new Page<Role>(pageNo, pageSize);
		IPage<Role> pageList = roleService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param role
	 * @return
	 */
	@ApiOperation(value="角色-添加", notes="角色-添加")
	@PostMapping(value = "/add")
	public Result<Role> add(@RequestBody Role role) {
		Result<Role> result = new Result<Role>();
		try {
			roleService.save(role);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param role
	 * @return
	 */
	@ApiOperation(value="角色-编辑", notes="角色-编辑")
	@PutMapping(value = "/edit")
	public Result<Role> edit(@RequestBody Role role) {
		Result<Role> result = new Result<Role>();
		Role roleEntity = roleService.getById(role.getId());
		if(roleEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = roleService.updateById(role);
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
	@ApiOperation(value="角色-通过id删除", notes="角色-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			roleService.removeById(id);
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
	@ApiOperation(value="角色-批量删除", notes="角色-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<Role> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Role> result = new Result<Role>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.roleService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="角色-通过id查询", notes="角色-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Role> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Role> result = new Result<Role>();
		Role role = roleService.getById(id);
		if(role==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(role);
			result.setSuccess(true);
		}
		return result;
	}

}

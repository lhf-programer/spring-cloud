package com.lvhaifeng.cloud.admin.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
 * @Date: 2020-01-06 14:26
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
	@GetMapping(value = "/list")
	public Result<IPage<Role>> queryPageList(Role role,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
        Result<IPage<Role>> result = new Result<>();
		IPage<Role> pageList = roleService.pageRoleList(role, pageNo, pageSize);
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
	@PostMapping(value = "/add")
	public Result<Role> add(@RequestBody Role role) {
		Result<Role> result = new Result<>();
		try {
			roleService.saveRole(role);
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
	@PutMapping(value = "/edit")
	public Result<Role> edit(@RequestBody Role role) {
		Result<Role> result = new Result<>();
		try {
            roleService.updateByRoleId(role);
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
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			roleService.removeByRoleId(id);
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
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) List<String> ids) {
        try {
            roleService.removeByRoleIds(ids);
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
	@GetMapping(value = "/queryById")
	public Result<Role> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Role> result = new Result<>();
		Role role = roleService.getByRoleId(id);
        result.setResult(role);
        result.setSuccess(true);
		return result;
	}

}

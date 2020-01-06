package com.lvhaifeng.cloud.admin.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.UserRole;
import com.lvhaifeng.cloud.admin.service.IUserRoleService;
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
 * @Description: 用户角色
 * @Author: haifeng.lv
 * @Date: 2020-01-06 11:34
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
	 * @param req
	 * @return
	 */
	@ApiOperation(value="用户角色-分页列表查询", notes="用户角色-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<UserRole>> queryPageList(UserRole userRole,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<UserRole>> result = new Result<>();
		QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
		Page<UserRole> page = new Page<>(pageNo, pageSize);
		IPage<UserRole> pageList = userRoleService.page(page, queryWrapper);
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
	@PostMapping(value = "/add")
	public Result<UserRole> add(@RequestBody UserRole userRole) {
		Result<UserRole> result = new Result<>();
		try {
			userRoleService.save(userRole);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
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
	@PutMapping(value = "/edit")
	public Result<UserRole> edit(@RequestBody UserRole userRole) {
		Result<UserRole> result = new Result<>();
		try {
            userRoleService.updateById(userRole);
            result.success("编辑成功！");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
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
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			userRoleService.removeById(id);
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
	@ApiOperation(value="用户角色-批量删除", notes="用户角色-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) List<String> ids) {
        try {
            userRoleService.removeByIds(ids);
        } catch (Exception e) {
            log.error("删除失败",e.getMessage());
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
	@GetMapping(value = "/queryById")
	public Result<UserRole> queryById(@RequestParam(name="id",required=true) String id) {
		Result<UserRole> result = new Result<>();
		UserRole userRole = userRoleService.getById(id);
        result.setResult(userRole);
        result.setSuccess(true);
		return result;
	}

}

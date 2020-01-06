package com.lvhaifeng.cloud.admin.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.lvhaifeng.cloud.admin.vo.request.MenuInfo;
import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.Menu;
import com.lvhaifeng.cloud.admin.service.IMenuService;
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
 * @Description: 菜单
 * @Author: haifeng.lv
 * @Date: 2020-01-06 11:17
 */
@Slf4j
@Api(tags="菜单")
@RestController
@RequestMapping("/menu")
@CheckClientToken
@CheckUserToken
public class MenuController {
	@Autowired
	private IMenuService menuService;
	
	/**
	 * 分页列表查询
	 * @param menu
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="菜单-分页列表查询", notes="菜单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Menu>> queryPageList(Menu menu,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Menu>> result = new Result<>();
		QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
		Page<Menu> page = new Page<>(pageNo, pageSize);
		IPage<Menu> pageList = menuService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	 * 添加
	 * @param menuInfo
	 * @return
	 */
	@ApiOperation(value="菜单-添加", notes="菜单-添加")
	@PostMapping(value = "/add")
	public Result<Menu> add(@RequestBody MenuInfo menuInfo) {
		Result<Menu> result = new Result<>();
		try {
			menuService.save(menuInfo);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	 * 编辑
	 * @param menuInfo
	 * @return
	 */
	@ApiOperation(value="菜单-编辑", notes="菜单-编辑")
	@PutMapping(value = "/edit")
	public Result<Menu> edit(@RequestBody MenuInfo menuInfo) {
		Result<Menu> result = new Result<>();
		try {
            menuService.updateById(menuInfo);
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
	@ApiOperation(value="菜单-通过id删除", notes="菜单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			menuService.removeById(id);
		} catch (Exception e) {
			e.printStackTrace();
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
	@ApiOperation(value="菜单-批量删除", notes="菜单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) List<String> ids) {
        try {
            menuService.removeByIds(ids);
        } catch (Exception e) {
        	e.printStackTrace();
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
	@ApiOperation(value="菜单-通过id查询", notes="菜单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Menu> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Menu> result = new Result<>();
		Menu menu = menuService.getById(id);
        result.setResult(menu);
        result.setSuccess(true);
		return result;
	}

}

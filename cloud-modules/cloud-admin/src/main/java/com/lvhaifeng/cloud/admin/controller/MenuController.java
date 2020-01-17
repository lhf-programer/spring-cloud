package com.lvhaifeng.cloud.admin.controller;

import java.util.List;

import com.lvhaifeng.cloud.admin.vo.response.MenuInfo;
import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.Menu;
import com.lvhaifeng.cloud.admin.service.IMenuService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
 * @Date: 2020-01-13 17:30
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
	 * @param sortProp
	 * @param sortType
	 * @return
	 */
	@ApiOperation(value="菜单-分页列表查询", notes="菜单-分页列表查询")
	@GetMapping(value = "/getMenuPageList")
	public Result<IPage<MenuInfo>> getMenuPageList(Menu menu,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                      @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                      @RequestParam(name="sortProp", required = false) String sortProp,
                                      @RequestParam(name="sortType", required = false) String sortType) {
        Result<IPage<MenuInfo>> result = new Result<>();
		IPage<MenuInfo> pageList = menuService.findMenuPageList(menu, pageNo, pageSize, sortProp, sortType);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	/**
	 * @Description 查询所有菜单 通过角色id
	 * @Author haifeng.lv
	 * @param: id 角色id
	 * @Date 2020/1/15 17:44
	 * @return: com.lvhaifeng.cloud.common.vo.Result<java.util.List<com.lvhaifeng.cloud.admin.vo.response.MenuInfo>>
	 */
	@ApiOperation(value="所有菜单 通过角色id-查询", notes="所有菜单通过 角色id-查询")
	@GetMapping(value = "/getAllMenusByRoleId")
	public Result<List<MenuInfo>> getAllMenusByRoleId(@RequestParam(name="id") String id) {
		Result<List<MenuInfo>> result = new Result<>();
		List<MenuInfo> response = menuService.getAllMenusByRoleId(id);
		result.setSuccess(true);
		result.setResult(response);
		return result;
	}

	/**
	 * @Description 查询所有菜单
	 * @Author haifeng.lv
	 * @Date 2020/1/15 17:44
	 * @return: com.lvhaifeng.cloud.common.vo.Result<java.util.List<com.lvhaifeng.cloud.admin.vo.response.MenuInfo>>
	 */
	@ApiOperation(value="所有菜单-查询", notes="所有菜单-查询")
	@GetMapping(value = "/getAllMenus")
	public Result<List<MenuInfo>> getAllMenus() {
		Result<List<MenuInfo>> result = new Result<>();
		List<MenuInfo> response = menuService.findAllMenus();
		result.setSuccess(true);
		result.setResult(response);
		return result;
	}

	/**
	 * 添加
	 * @param menu
	 * @return
	 */
	@ApiOperation(value="菜单-添加", notes="菜单-添加")
	@PostMapping(value = "/generateMenu")
	public Result<Menu> generateMenu(@RequestBody Menu menu) {
		Result<Menu> result = new Result<>();
		menuService.createMenu(menu);
		result.success("添加成功！");
		return result;
	}
	
	/**
	 * 编辑
	 * @param menu
	 * @return
	 */
	@ApiOperation(value="菜单-编辑", notes="菜单-编辑")
	@PutMapping(value = "/changeMenuById")
	public Result<Menu> changeMenuById(@RequestBody Menu menu) {
		Result<Menu> result = new Result<>();
		menuService.alterMenuById(menu);
		result.success("编辑成功！");
		return result;
	}
	
	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="菜单-通过id删除", notes="菜单-通过id删除")
	@DeleteMapping(value = "/expurgateMenuById")
	public Result<?> expurgateMenuById(@RequestParam(name="id",required=true) String id) {
		menuService.dropMenuById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="菜单-批量删除", notes="菜单-批量删除")
	@DeleteMapping(value = "/expurgateMenuBatch")
	public Result<?> expurgateMenuBatch(@RequestParam(name="ids",required=true) String ids) {
		menuService.dropMenuBatch(ids);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="菜单-通过id查询", notes="菜单-通过id查询")
	@GetMapping(value = "/getMenuById")
	public Result<MenuInfo> getMenuById(@RequestParam(name="id",required=true) String id) {
		Result<MenuInfo> result = new Result<>();
		MenuInfo menuInfo = menuService.findMenuById(id);
        result.setResult(menuInfo);
        result.setSuccess(true);
		return result;
	}

}

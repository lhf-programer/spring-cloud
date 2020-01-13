package com.lvhaifeng.cloud.admin.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.lvhaifeng.cloud.admin.vo.request.ResourceInfo;
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
 * @Date: 2020-01-13 14:44
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
	@GetMapping(value = "/getMenuPageList")
	public Result<IPage<Menu>> getMenuPageList(Menu menu,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
        Result<IPage<Menu>> result = new Result<>();
		IPage<Menu> pageList = menuService.findMenuPageList(menu, pageNo, pageSize, req);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	 @ApiOperation(value="菜单-列表查询", notes="菜单-列表查询")
	 @GetMapping(value = "/getAllMenus")
	 public Result<List<Menu>> getAllMenu() {
		 Result<List<Menu>> result = new Result<>();
		 List<Menu> response = menuService.list();
		 result.setSuccess(true);
		 result.setResult(response);
		 return result;
	 }

	/**
	 * 添加
	 * @param resourceInfo
	 * @return
	 */
	@ApiOperation(value="菜单-添加", notes="菜单-添加")
	@PostMapping(value = "/generateMenu")
	public Result<Menu> generateMenu(@RequestBody ResourceInfo resourceInfo) {
		Result<Menu> result = new Result<>();
		try {
			menuService.createMenu(resourceInfo);
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
	 * @param resourceInfo
	 * @return
	 */
	@ApiOperation(value="菜单-编辑", notes="菜单-编辑")
	@PutMapping(value = "/changeMenuById")
	public Result<Menu> changeMenuById(@RequestBody ResourceInfo resourceInfo) {
		Result<Menu> result = new Result<>();
		try {
            menuService.alterMenuById(resourceInfo);
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
	@ApiOperation(value="菜单-通过id删除", notes="菜单-通过id删除")
	@DeleteMapping(value = "/expurgateMenuById")
	public Result<?> expurgateMenuById(@RequestParam(name="id",required=true) String id) {
		try {
			menuService.dropMenuById(id);
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
	@ApiOperation(value="菜单-批量删除", notes="菜单-批量删除")
	@DeleteMapping(value = "/expurgateMenuBatch")
	public Result<?> expurgateMenuBatch(@RequestParam(name="ids",required=true) String ids) {
        try {
            menuService.dropMenuBatch(ids);
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
	@ApiOperation(value="菜单-通过id查询", notes="菜单-通过id查询")
	@GetMapping(value = "/getMenuById")
	public Result<Menu> getMenuById(@RequestParam(name="id",required=true) String id) {
		Result<Menu> result = new Result<>();
		Menu menu = menuService.findMenuById(id);
        result.setResult(menu);
        result.setSuccess(true);
		return result;
	}

}

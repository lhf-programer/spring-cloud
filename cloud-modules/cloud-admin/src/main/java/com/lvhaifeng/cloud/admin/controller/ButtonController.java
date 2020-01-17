package com.lvhaifeng.cloud.admin.controller;

import com.lvhaifeng.cloud.admin.vo.response.ButtonInfo;
import com.lvhaifeng.cloud.common.vo.Result;
import com.lvhaifeng.cloud.admin.entity.Button;
import com.lvhaifeng.cloud.admin.service.IButtonService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.lvhaifeng.cloud.auth.client.annotation.CheckClientToken;
import com.lvhaifeng.cloud.auth.user.annotation.CheckUserToken;

 /**
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:28
 */
@Slf4j
@Api(tags="按钮")
@RestController
@RequestMapping("/button")
@CheckClientToken
@CheckUserToken
public class ButtonController {
	@Autowired
	private IButtonService buttonService;
	
	/**
	 * 分页列表查询
	 * @param button
	 * @param pageNo
	 * @param pageSize
	 * @param sortProp
	 * @param sortType
	 * @return
	 */
	@ApiOperation(value="按钮-分页列表查询", notes="按钮-分页列表查询")
	@GetMapping(value = "/getButtonPageList")
	public Result<IPage<ButtonInfo>> getButtonPageList(Button button,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                      @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                      @RequestParam(name="sortProp", required = false) String sortProp,
                                      @RequestParam(name="sortType", required = false) String sortType) {
        Result<IPage<ButtonInfo>> result = new Result<>();
		IPage<ButtonInfo> pageList = buttonService.findButtonPageList(button, pageNo, pageSize, sortProp, sortType);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	 * 添加
	 * @param button
	 * @return
	 */
	@ApiOperation(value="按钮-添加", notes="按钮-添加")
	@PostMapping(value = "/generateButton")
	public Result<Button> generateButton(@RequestBody Button button) {
		Result<Button> result = new Result<>();
		buttonService.createButton(button);
		result.success("添加成功！");
		return result;
	}
	
	/**
	 * 编辑
	 * @param button
	 * @return
	 */
	@ApiOperation(value="按钮-编辑", notes="按钮-编辑")
	@PutMapping(value = "/changeButtonById")
	public Result<Button> changeButtonById(@RequestBody Button button) {
		Result<Button> result = new Result<>();
		buttonService.alterButtonById(button);
		result.success("编辑成功！");
		return result;
	}
	
	/**
	 * 通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="按钮-通过id删除", notes="按钮-通过id删除")
	@DeleteMapping(value = "/expurgateButtonById")
	public Result<?> expurgateButtonById(@RequestParam(name="id",required=true) String id) {
		buttonService.dropButtonById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="按钮-批量删除", notes="按钮-批量删除")
	@DeleteMapping(value = "/expurgateButtonBatch")
	public Result<?> expurgateButtonBatch(@RequestParam(name="ids",required=true) String ids) {
		buttonService.dropButtonBatch(ids);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="按钮-通过id查询", notes="按钮-通过id查询")
	@GetMapping(value = "/getButtonById")
	public Result<Button> getButtonById(@RequestParam(name="id",required=true) String id) {
		Result<Button> result = new Result<>();
		Button button = buttonService.findButtonById(id);
        result.setResult(button);
        result.setSuccess(true);
		return result;
	}

}

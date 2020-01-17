package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Button;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.admin.vo.response.ButtonInfo;

import java.util.List;

/**
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:28
 */
public interface IButtonService extends IService<Button> {
    IPage<ButtonInfo> findButtonPageList(Button button, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createButton(Button button);
    boolean alterButtonById(Button button);
    boolean dropButtonById(String id);
    boolean dropButtonBatch(String ids);
    Button findButtonById(String id);
    List<Button> findAllButtonsById(String roleId, String menuId);
    void removeByMenuIds(List<String> ids);
}

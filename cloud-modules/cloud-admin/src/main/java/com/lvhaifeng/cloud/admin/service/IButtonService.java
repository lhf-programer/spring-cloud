package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Button;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:20
 */
public interface IButtonService extends IService<Button> {
    IPage<Button> pageButtonList(Button button, Integer pageNo, Integer pageSize);
    boolean saveButton(Button button);
    boolean updateByButtonId(Button button);
    boolean removeByButtonId(Serializable id);
    boolean removeByButtonIds(Collection<? extends Serializable> ids);
    Button getByButtonId(Serializable id);
}

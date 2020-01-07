package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Button;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.admin.vo.request.ResourceInfo;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-06 14:20
 */
public interface IButtonService extends IService<Button> {
    IPage<Button> pageButtonList(Button button, Integer pageNo, Integer pageSize);
    boolean saveButton(ResourceInfo resourceInfo);
    boolean updateByButtonId(ResourceInfo resourceInfo);
    boolean removeByButtonId(Serializable id);
    boolean removeByButtonIds(Collection<? extends Serializable> ids);
    Button getByButtonId(Serializable id);
}

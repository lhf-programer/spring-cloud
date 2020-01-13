package com.lvhaifeng.cloud.admin.service;

import com.lvhaifeng.cloud.admin.entity.Button;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lvhaifeng.cloud.admin.vo.request.ResourceInfo;

import java.io.Serializable;

/**
 * @Description: 按钮
 * @Author: haifeng.lv
 * @Date: 2020-01-13 17:28
 */
public interface IButtonService extends IService<Button> {
    IPage<Button> findButtonPageList(Button button, Integer pageNo, Integer pageSize, String sortProp, String sortType);
    boolean createButton(ResourceInfo resourceInfo);
    boolean alterButtonById(ResourceInfo resourceInfo);
    boolean dropButtonById(Serializable id);
    boolean dropButtonBatch(String ids);
    Button findButtonById(Serializable id);
}

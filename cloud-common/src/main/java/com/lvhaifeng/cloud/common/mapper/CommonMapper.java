package com.lvhaifeng.cloud.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;

public interface CommonMapper<T> extends SelectByIdsMapper<T>, Mapper<T> {

}

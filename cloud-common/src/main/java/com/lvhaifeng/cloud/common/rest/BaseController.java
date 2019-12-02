package com.lvhaifeng.cloud.common.rest;

import com.lvhaifeng.cloud.common.biz.BaseBiz;
import com.lvhaifeng.cloud.common.context.BaseContextHandler;
import com.lvhaifeng.cloud.common.msg.ObjectRestResponse;
import com.lvhaifeng.cloud.common.msg.TableResultResponse;
import com.lvhaifeng.cloud.common.util.Query;
import com.lvhaifeng.cloud.common.util.StringEscapeEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * controller抽象类
 *
 * @author haifeng.lv
 * @version 2018-06-15 8:48
 */
public class BaseController<Biz extends BaseBiz, Entity, PK> {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringEscapeEditor());
        binder.registerCustomEditor(String[].class, new StringEscapeEditor());
    }

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected Biz baseBiz;

    @PostMapping("")
    @ResponseBody
    public ObjectRestResponse<Entity> add(@RequestBody Entity entity) {
        baseBiz.insertSelective(entity);
        return new ObjectRestResponse<Entity>().data(entity);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ObjectRestResponse<Entity> get(@PathVariable PK id) {
        ObjectRestResponse<Entity> entityObjectRestResponse = new ObjectRestResponse<>();
        Object o = baseBiz.selectById(id);
        entityObjectRestResponse.data((Entity) o);
        return entityObjectRestResponse;
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ObjectRestResponse<Entity> update(@RequestBody Entity entity) {
        baseBiz.updateSelectiveById(entity);
        return new ObjectRestResponse<Entity>().data(entity);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ObjectRestResponse<Entity> remove(@PathVariable PK id) {
        baseBiz.deleteById(id);
        return new ObjectRestResponse<Entity>();
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public List<Entity> all() {
        return baseBiz.selectListAll();
    }

    @GetMapping(value = "/page")
    @ResponseBody
    public TableResultResponse<Entity> list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return baseBiz.selectByQuery(query);
    }

    public String getCurrentUserName() {
        return BaseContextHandler.getUserName();
    }
}

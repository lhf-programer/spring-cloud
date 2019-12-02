package com.lvhaifeng.cloud.common.msg;

import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author haifeng.lv
 * @version 2018-06-14 22:40
 */
public class TableResultResponse<T> extends BaseResponse {

    TableData<T> data;

    Map<String,Object> extendMap;

    public TableResultResponse(long total, List<T> rows) {
        this.data = new TableData<T>(total, rows);
    }

    public TableResultResponse() {
        this.data = new TableData<T>();
    }

    TableResultResponse<T> total(int total) {
        this.data.setTotal(total);
        return this;
    }

    TableResultResponse<T> total(List<T> rows) {
        this.data.setRows(rows);
        return this;
    }

    public TableData<T> getData() {
        return data;
    }

    public void setData(TableData<T> data) {
        this.data = data;
    }

    public Map<String, Object> getExtendMap() {
        return extendMap;
    }

    public void setExtendMap(Map<String, Object> extendMap) {
        this.extendMap = extendMap;
    }

    public class TableData<T> {
        long total;
        List<T> rows;

        public TableData(long total, List<T> rows) {
            this.total = total;
            this.rows = rows;
        }

        public TableData() {
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public List<T> getRows() {
            return rows;
        }

        public void setRows(List<T> rows) {
            this.rows = rows;
        }
    }
}

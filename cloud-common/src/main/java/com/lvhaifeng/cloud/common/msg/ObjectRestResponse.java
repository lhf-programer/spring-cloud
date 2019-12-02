package com.lvhaifeng.cloud.common.msg;

public class ObjectRestResponse<T> extends BaseResponse {

    T data;

    public ObjectRestResponse data(T data) {
        this.setData(data);
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}

package cn.springcloud.book.model;

import java.io.Serializable;

public class RequestMessage implements Serializable {

    private CommonInfo commonInfo;

    private Object value;

    public CommonInfo getCommonInfo() {
        return commonInfo;
    }

    public void setCommonInfo(CommonInfo commonInfo) {
        this.commonInfo = commonInfo;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


}

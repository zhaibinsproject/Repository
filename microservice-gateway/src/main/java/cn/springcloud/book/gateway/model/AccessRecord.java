package cn.springcloud.book.gateway.model;

import org.springframework.util.MultiValueMap;

public class AccessRecord {
    private String path;
    private String body;
    private MultiValueMap<String,String> queryString;
    private long expendTime;
    private int httpCode;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MultiValueMap<String, String> getQueryString() {
        return queryString;
    }

    public void setQueryString(MultiValueMap<String, String> queryString) {
        this.queryString = queryString;
    }

    public long getExpendTime() {
        return expendTime;
    }

    public void setExpendTime(long expendTime) {
        this.expendTime = expendTime;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
}

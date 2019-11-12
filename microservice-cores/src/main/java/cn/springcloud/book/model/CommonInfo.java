package cn.springcloud.book.model;

import java.io.Serializable;
import java.util.Date;

public class CommonInfo implements Serializable {

    private String userCode;

    private String functionCode;

    private Long requestId;

    private Date requestTime;

    private String dbShardingFlag;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getDbShardingFlag() {
        return dbShardingFlag;
    }

    public void setDbShardingFlag(String dbShardingFlag) {
        this.dbShardingFlag = dbShardingFlag;
    }

}

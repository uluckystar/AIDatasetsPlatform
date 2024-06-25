package com.localaihub.platform.framework.common.result.jwt;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/14 21:07
 */
public class ResultUser {
    public boolean success;
    public ResultToken data;

    public ResultUser(boolean success, ResultToken data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ResultToken getData() {
        return data;
    }

    public void setData(ResultToken data) {
        this.data = data;
    }
}

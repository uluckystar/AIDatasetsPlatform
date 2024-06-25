package com.localaihub.platform.framework.common.result;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/13 18:01
 */
public class ResultBase {
    public boolean success;
    public Object data;

    public ResultBase() {}

    public ResultBase(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
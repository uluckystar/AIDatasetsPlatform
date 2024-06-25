package com.localaihub.platform.framework.common.result;

import java.util.List;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/13 16:24
 */
public class ResultBaseList<T> {
    public boolean success;
    public List<T> data;

    public ResultBaseList() {}

    public ResultBaseList(boolean success, List<T> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> list) {
        this.data = data;
    }
}

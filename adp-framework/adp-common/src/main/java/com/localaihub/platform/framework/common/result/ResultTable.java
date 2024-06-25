package com.localaihub.platform.framework.common.result;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/12 16:47
 */
public class ResultTable<T> {
    public boolean success;
    public ResultData<T> data;

    public ResultTable() {}

    public ResultTable(boolean success, ResultData<T> data) {
        this.success = success;
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(ResultData<T> data) {
        this.data = data;
    }
}

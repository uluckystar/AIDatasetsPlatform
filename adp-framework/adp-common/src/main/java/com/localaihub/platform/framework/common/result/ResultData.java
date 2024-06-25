package com.localaihub.platform.framework.common.result;

import java.util.List;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/12 16:47
 */
public class ResultData<T>  {
    public List<T> list;
    public long total;
    public Integer pageSize;
    public Integer currentPage;

    public ResultData(List<T> list, long total, Integer pageSize, Integer currentPage) {
        this.list = list;
        this.total = total;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}

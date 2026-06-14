package com.drone.management.common;

import java.util.List;

/**
 * 分页查询结果封装
 *
 * @param <T> 数据类型
 */
public class PageResult<T> extends Result<List<T>> {

    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 当前页码 */
    private int pageNum;

    /** 每页大小 */
    private int pageSize;

    /** 总页数 */
    private int totalPages;

    public PageResult() {
    }

    public PageResult(int code, String message, List<T> data,
                      long total, int pageNum, int pageSize) {
        super(code, message, data);
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPages = pageSize > 0 ? (int) ((total + pageSize - 1) / pageSize) : 0;
    }

    public static <T> PageResult<T> success(List<T> data, long total, int pageNum, int pageSize) {
        return new PageResult<>(0, "查询成功", data, total, pageNum, pageSize);
    }

    // ==================== Getters / Setters ====================

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}

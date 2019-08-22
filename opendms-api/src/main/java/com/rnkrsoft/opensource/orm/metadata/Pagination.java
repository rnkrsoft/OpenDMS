package com.rnkrsoft.opensource.orm.metadata;

import com.rnkrsoft.opensource.orm.metadata.Headers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rnkrsoft.com on 2019/5/12.
 */
public class Pagination<Row> {
    /**
     * 获得当前页号
     * @param total 总记录条数
     * @param pageSize 分页大小
     * @param curPageNo 当前页号
     * @return 符合条件的当前页号
     */
    public static int curPageNo(int total, int pageSize, int curPageNo) {
        int curPageNo0 = curPageNo;
        //如果超过最大页数，则重设总条数
        if (total == 0) {
            curPageNo0 = 1;
        } else if (((total - 1) / pageSize) + 1 < curPageNo) {
            curPageNo0 = ((total - 1) / pageSize) + 1;
        }
        return curPageNo0;
    }

    /**
     * 获取分页数
     * @param total 总记录条数
     * @param pageSize 分页大小
     * @return 分页数
     */
    public static int pageNum(int total, int pageSize) {
        int pageNum = (total + pageSize - 1) / pageSize;
        return pageNum;
    }
    /**
     * 总条数
     */
    int total = 0;
    /**
     * 分页大小
     */
    int pageSize = 15;
    /**
     * 当前第几页
     */
    int curPageNo = 1;
    /**
     * 分页数
     */
    int pageNum = 0;
    /**
     * 头信息
     */
    final Headers header = new Headers();
    /**
     * 当前分页的数据
     */
    final List<Row> records = new ArrayList();
    /**
     * 是否已准备好数据
     */
    boolean ready = false;
    /**
     * 是否统计总条数
     */
    boolean statsTotal = true;
    private Map<String, Object> params = new HashMap<String, Object>();

    public Pagination(int pageSize, int curPageNo) {
        validate(pageSize, curPageNo);
        this.pageSize = pageSize;
        this.curPageNo = curPageNo;
    }

    public Pagination(int pageSize, int curPageNo, int total) {
        validate(pageSize, curPageNo);
        this.pageSize = pageSize;
        this.curPageNo = curPageNo;
        this.setTotal(total);
    }

    /**
     * 进行逻辑分页
     *
     * @param data      数据
     * @param pageSize  分页大小
     * @param curPageNo 当前页
     */
    public Pagination(List<Row> data, int pageSize, int curPageNo) {
        this(pageSize, curPageNo);
        setTotal(data.size());
        List<Row> result = new ArrayList();
        int offset = (curPageNo - 1) * pageSize;
        for (int i = offset; i < offset + pageSize && i < data.size(); i++) {
            result.add(data.get(i));
        }
        this.ready = true;
        this.records.clear();
        this.records.addAll(result);
    }

    /**
     * 如果超过最大页数，则重设总条数
     *
     * @param total
     */
    public void ifOverPageNumResetCurPageNoAndTotal(int total) {
        this.total = total;
        this.curPageNo = curPageNo(total, pageSize, curPageNo);
        this.pageNum = pageNum(total, pageSize);
    }


    public void setTotal(int total) {
        this.total = total;
        //如果设置了总条数，不执行统计
        if (total > 0) {
            //如果传入的总条数超过了分页大小乘以总页数，则使用统计
            if (((total - 1) / pageSize) + 1 < curPageNo) {
                statsTotal = true;
            } else {
                this.pageNum = (total + pageSize - 1) / pageSize;
                statsTotal = false;
            }
        } else {
            statsTotal = true;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurPageNo() {
        return curPageNo;
    }

    public int getPageNum() {
        return pageNum;
    }

    public boolean isReady() {
        return ready;
    }

    void validate(int pageSize, int pageNo) {
        if (pageSize < 1 || pageSize > 1000) {
            throw new IllegalArgumentException("无效分页大小");
        }
        if (pageNo < 1) {
            throw new IllegalArgumentException("无效页数");
        }
        if (pageNum < 0) {
            throw new IllegalArgumentException("无效页数");
        }
        if (total < 0) {
            throw new IllegalArgumentException("无效总条数");
        }
    }

    /**
     * 满足条件的记录总条数
     *
     * @return
     */
    public int getTotal() {
        if (!this.ready) {
            throw new IllegalArgumentException("分页数据未准备就绪");
        }
        return total;
    }

    /**
     * @return
     */
    public List<Row> getRecords() {
        if (!this.ready) {
            throw new IllegalArgumentException("分页数据未准备就绪");
        }
        return records;
    }

    /**
     * 当前页是否为最后一页
     *
     * @return
     */
    public boolean isLastPage() {
        if (!this.ready) {
            throw new IllegalArgumentException("分页数据未准备就绪");
        }
        return ((total - 1) / pageSize) + 1 == curPageNo;
    }

    /**
     * 当前页是否为第一页
     *
     * @return
     */
    public boolean isFristPage() {
        if (!this.ready) {
            throw new IllegalArgumentException("分页数据未准备就绪");
        }
        return curPageNo == 1;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Headers getHeader() {
        return header;
    }

    public void addColumn(String name, String jdbcType, Class javaType) {
        this.header.addColumn(name, jdbcType, javaType);
    }

    public void setRecords(List records) {
        if (this.ready) {
            throw new IllegalArgumentException("已设置过数据");
        }
        if (records.size() != pageSize && records.size() != total % pageSize) {
            throw new IllegalArgumentException("无效的数据条数");
        }
        this.ready = true;
        this.records.clear();
        this.records.addAll(records);
    }

    public boolean isStatsTotal() {
        return statsTotal;
    }
}


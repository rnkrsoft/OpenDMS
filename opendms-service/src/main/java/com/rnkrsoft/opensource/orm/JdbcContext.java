package com.rnkrsoft.opensource.orm;

import com.rnkrsoft.opensource.orm.filter.JdbcFilterService;
import com.rnkrsoft.opensource.orm.metadata.Pagination;

import java.util.List;

/**
 * Created by rnkrsoft.com on 2019/5/12.
 */
public interface JdbcContext {
    boolean isQuery();
    boolean isPaginationQuery();
    Pagination getPagination();
    String getStatement();
    List<Object> getArguments();
    int getChanges();
    void setChanges(int changes);
    boolean isExecuted();
    void setExecuted(boolean executed);
    String getBiz();
    JdbcFilterService getFilterService();
}

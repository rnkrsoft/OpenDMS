package com.rnkrsoft.opensource.orm.filter;

/**
 * Created by rnkrsoft.com on 2019/5/12.
 */
public interface JdbcFilterService {
    boolean accept(String databaseName);
    boolean accept(String databaseName, String tableName);
    boolean accept(String databaseName, String tableName, String columnName);
}

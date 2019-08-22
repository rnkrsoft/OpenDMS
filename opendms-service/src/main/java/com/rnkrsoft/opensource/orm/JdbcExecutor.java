package com.rnkrsoft.opensource.orm;


import java.sql.SQLException;

/**
 * Created by rnkrsoft.com on 2019/5/12.
 */
public interface JdbcExecutor {
    /**
     * 执行上下文
     * @param context 上下文
     */
    void execute(JdbcContext context) throws SQLException;
}

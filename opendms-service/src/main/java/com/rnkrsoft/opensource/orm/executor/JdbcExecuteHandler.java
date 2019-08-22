package com.rnkrsoft.opensource.orm.executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by rnkrsoft.com on 2019/5/12.
 */
public class JdbcExecuteHandler {
    Connection connection;

    public JdbcExecuteHandler(Connection connection) {
        this.connection = connection;
    }

    public int execute(String sql) throws SQLException {
        int changes = 0;
        Statement jdbcSt = null;
        try {
            jdbcSt = connection.createStatement();
            changes = jdbcSt.executeUpdate(sql);
        } finally {
            if (jdbcSt != null) {
                jdbcSt.close();
                jdbcSt = null;
            }
        }
        return changes;
    }
}

package com.rnkrsoft.opensource.orm.executor;

import com.rnkrsoft.opensource.orm.JdbcContext;
import com.rnkrsoft.opensource.orm.JdbcExecutor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by rnkrsoft.com on 2019/5/12.
 */
public class SimpleJdbcExecutor implements JdbcExecutor {
    Connection connection;
    public SimpleJdbcExecutor(Connection connection) {
        this.connection = connection;
    }


    public void execute(JdbcContext context) throws SQLException {
        //如果是查询语句
        if (context.isQuery()){
            JdbcQueryHandler queryHandler = new JdbcQueryHandler(connection);
            queryHandler.total(context.getStatement(), context.getArguments(), context.getPagination());
            queryHandler.records(context.getStatement(), context.getArguments(), context.getPagination());
        }else{//如果不是查询语句
            JdbcExecuteHandler executeHandler = new JdbcExecuteHandler(connection);
            int changes = executeHandler.execute(context.getStatement());
            context.setChanges(changes);
        }
    }
}

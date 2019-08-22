package com.rnkrsoft.opensource.orm.executor;

import com.rnkrsoft.opensource.orm.metadata.Pagination;
import com.rnkrsoft.time.DateStyle;
import com.rnkrsoft.utils.DateUtils;

import javax.web.data.sql.Row;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnkrsoft.com on 2019/5/12.
 */
public class JdbcQueryHandler {

    Connection connection;

    public JdbcQueryHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     * 根据查询SQL语句获取记录总条数
     *
     * @param querySql 查询SQL
     * @return 总条数
     * @throws SQLException
     */
    public int total(String querySql, List<Object> args) throws SQLException {
        int total = 0;
        Statement jdbcSt = null;
        ResultSet jdbcRs = null;
        try {
            jdbcSt = connection.createStatement();
            String tempSql = "select count(1) from (" + querySql + ") count_table";
            jdbcRs = jdbcSt.executeQuery(tempSql);
            if (jdbcRs.next()) {
                total = jdbcRs.getInt(1);
            }
        } finally {
            if (jdbcRs != null) {
                jdbcRs.close();
                jdbcRs = null;
            }
            if (jdbcSt != null) {
                jdbcSt.close();
                jdbcSt = null;
            }
        }
        return total;
    }

    /**
     * 根据查询SQL语句获取记录总条数
     *
     * @param querySql 查询SQL
     * @param pagination 分页对象
     * @throws SQLException
     */
    public void total(String querySql, List<Object> args, Pagination pagination) throws SQLException {
        pagination.setTotal(total(querySql, args));
    }

    /**
     * 根据查询SQL语句和分页参数获取结果记录
     *
     * @param querySql   查询SQL
     * @param pagination 分页大小
     * @return 结果记录
     * @throws SQLException
     */
    public void records(String querySql, List<Object> args, Pagination pagination) throws SQLException {
        int total = 0;
        Statement jdbcSt = null;
        ResultSet jdbcRs = null;
        final List<Row> rows = new ArrayList<Row>();
        try {
            jdbcSt = connection.createStatement();
            //计算第一条记录的位置，Mysql中记录的位置是从0开始的。
            int offset = (pagination.getCurPageNo() - 1) * pagination.getPageSize();
            String tempSql = querySql + " limit " + offset + "," + pagination.getPageSize();
            jdbcRs = jdbcSt.executeQuery(tempSql);
            ResultSetMetaData resultSetMetaData = jdbcRs.getMetaData();
            //返回字段数
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                pagination.addColumn(resultSetMetaData.getColumnLabel(i), resultSetMetaData.getColumnTypeName(i), Class.forName(resultSetMetaData.getColumnClassName(i)));
            }
            while (jdbcRs.next()) {
                Row row = new Row();
                rows.add(row);
                for (int i = 1; i <= columnCount; i++) {
                    Object value = jdbcRs.getObject(i);
                    row.addValue(value);
                }
            }
            pagination.setRecords(rows);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (jdbcRs != null) {
                jdbcRs.close();
                jdbcRs = null;
            }
            if (jdbcSt != null) {
                jdbcSt.close();
                jdbcSt = null;
            }
        }
    }
}

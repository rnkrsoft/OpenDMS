package com.rnkrsoft.opensource.orm;

import com.rnkrsoft.opensource.orm.filter.JdbcFilterService;
import com.rnkrsoft.opensource.orm.metadata.Pagination;
import lombok.Getter;
import lombok.Setter;

import javax.web.data.sql.Row;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by rnkrsoft.com on 2019/5/12.
 */
public class DefaultJdbcContext implements JdbcContext {
    /**
     * 是否为分页查询
     */
    @Getter
    boolean paginationQuery;
    /**
     * 查询
     */
    @Getter
    boolean query;
    /**
     * 分页对象
     */
    @Getter
    Pagination<Row> pagination;
    /**
     * SQL 语句
     */
    @Getter
    String statement;
    /**
     * 参数
     */
    @Getter
    final List<Object> arguments = new ArrayList();
    /**
     * 变更条数
     */
    @Getter
    @Setter
    int changes;
    /**
     * 是否已经执行语句
     */
    @Getter
    @Setter
    boolean executed = false;

    /**
     * 登录用户号
     **/
    @Getter
    @Setter
    String loginUserId;

    /**
     * 登录用户姓名
     **/
    @Getter
    @Setter
    String loginUserName;

    /**
     * 登录用户手机号
     **/
    @Getter
    @Setter
    String loginUserMobile;

    /**
     * 业务类型
     */
    @Getter
    @Setter
    String biz;

    @Getter
    @Setter
    JdbcFilterService filterService;


    public static JdbcContext parse(String sql, List<Object> args, Integer pageSize, Integer pageNo) {
        DefaultJdbcContext context = new DefaultJdbcContext();
        //解析是否为查询
        if (JdbcStatmentDetector.isQuery(sql)) {
            context.query = true;
        }
        if (context.query && pageSize != null && pageNo != null && pageSize > 0 && pageNo > 0) {
            context.paginationQuery = true;
            context.pagination = new Pagination<Row>(pageSize, pageNo);
        }
        context.statement = sql;
        return context;
    }
}

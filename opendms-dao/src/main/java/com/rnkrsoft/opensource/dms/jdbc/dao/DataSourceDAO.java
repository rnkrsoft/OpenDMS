package com.rnkrsoft.opensource.dms.jdbc.dao;

import com.rnkrsoft.opensource.dms.jdbc.entity.DataSourceEntity;
import com.rnkrsoft.framework.orm.Pagination;
import com.rnkrsoft.framework.orm.jdbc.JdbcMapper;

/**
 * Created by rnkrsoft.com on 2019/6/30.
 */
public interface DataSourceDAO extends JdbcMapper<DataSourceEntity, Integer>{
    Pagination<DataSourceEntity> selectDataSource(Pagination<DataSourceEntity> pagination);
}

package com.rnkrsoft.opensource.dms.jdbc.dao;

import com.rnkrsoft.opensource.dms.jdbc.bo.QueryTaskBO;
import com.rnkrsoft.opensource.dms.jdbc.entity.TaskEntity;
import com.rnkrsoft.framework.orm.Pagination;
import com.rnkrsoft.framework.orm.jdbc.JdbcMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by rnkrsoft.com on 2019/6/30.
 */
public interface TaskDAO extends JdbcMapper<TaskEntity, Integer>{
    Pagination<QueryTaskBO> selectTask(Pagination<QueryTaskBO> pagination);
    int decreaseAllowDownloadCount(@Param("taskId")Integer taskId);
}

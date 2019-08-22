package com.rnkrsoft.opensource.dms.jdbc.dao;

import com.rnkrsoft.opensource.dms.jdbc.bo.QueryTemplateBO;
import com.rnkrsoft.opensource.dms.jdbc.entity.TemplateEntity;
import com.rnkrsoft.framework.orm.Pagination;
import com.rnkrsoft.framework.orm.jdbc.JdbcMapper;

/**
 * Created by rnkrsoft.com on 2019/6/30.
 */
public interface TemplateDAO extends JdbcMapper<TemplateEntity, Integer>{
    Pagination<QueryTemplateBO> selectTemplate(Pagination<QueryTemplateBO> pagination);
}

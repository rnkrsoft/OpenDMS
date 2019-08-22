package com.rnkrsoft.opensource.dms.services;


import com.rnkrsoft.opensource.orm.metadata.Pagination;

import javax.web.data.sql.Row;

/**
 * Created by woate on 2019/8/22.
 */
public interface SegmentalCallback {
    Pagination<Row> call(int pageIndex);
}

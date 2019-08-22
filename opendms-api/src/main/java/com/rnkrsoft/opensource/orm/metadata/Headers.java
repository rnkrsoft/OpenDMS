package com.rnkrsoft.opensource.orm.metadata;

import javax.web.data.sql.Header;
import java.util.*;

/**
 * Created by rnkrsoft.com on 2019/5/12.
 */
public class Headers {
    final List<String> columns = new ArrayList<String>();
    final Map<String,Header> columnMap = new HashMap();
    public List<String> getColumns() {
        return columns;
    }

    public Header getColumn(String column){
        return columnMap.get(column);
    }
    public void addColumn(String name, String jdbcType, Class javaType) {
        columns.add(name);
        columnMap.put(name,Header.builder().title(name).jdbcType(jdbcType).javaType(javaType).build());
    }
}

package com.rnkrsoft.opensource.dms.xls;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SheetMetadata {
    String desc;
    Sheet sheet;
    final List<Column> columns = new ArrayList();

    public SheetMetadata addColumn(Column metadata) {
        metadata.setIndex(columns.size());
        columns.add(metadata);
        return this;
    }
}

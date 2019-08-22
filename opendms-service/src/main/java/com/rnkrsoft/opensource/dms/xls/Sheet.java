package com.rnkrsoft.opensource.dms.xls;

import lombok.Getter;
import lombok.Setter;

import javax.web.data.sql.Row;
import java.util.ArrayList;
import java.util.List;

public class Sheet {
    @Setter
    @Getter
    SheetMetadata metadata;
    @Getter
    final List<Row> data = new ArrayList();

    public Sheet(SheetMetadata metadata){
        setMetadata(metadata);
    }

    public int getRowSize(){
        return data.size();
    }

    public Sheet add(Row record){
        data.add(record);
        return this;
    }
}

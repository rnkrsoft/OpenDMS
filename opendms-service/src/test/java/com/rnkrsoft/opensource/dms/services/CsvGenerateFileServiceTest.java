package com.rnkrsoft.opensource.dms.services;

import com.rnkrsoft.opensource.orm.metadata.Pagination;
import org.junit.Test;

import javax.web.data.sql.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnkrsoft.com on 2019/7/2.
 */
public class CsvGenerateFileServiceTest {

    @Test
    public void testGenerate() throws Exception {
        GenerateFileService generateFileService = new CsvGenerateFileService();

        generateFileService.generate("./target/demo.csv", 1, 2, new SegmentalCallback() {
            @Override
            public Pagination<Row> call(int pageIndex) {
                List<Row> rows = new ArrayList();
                for (int i = 0; i < 20; i++) {
                    Row row = new Row();
                    row.addValue("张三" + i);
                    row.addValue(Integer.toString(i));
                    rows.add(row);
                }
                Pagination<Row> pagination = new Pagination<Row>(rows, 2, 1);
                pagination.addColumn("姓名", "VARCHAR", String.class);
                pagination.addColumn("年龄", "INTEGER", String.class);
                return pagination;
            }
        });
    }
}
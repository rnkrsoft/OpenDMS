package com.rnkrsoft.opensource.dms.services;

import com.rnkrsoft.opensource.orm.metadata.Pagination;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.junit.Test;

import javax.web.data.sql.Row;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by woate on 2019/8/22.
 */
public class XlsGenerateFileServiceTest {

    @Test
    public void testGenerate() throws Exception {
        GenerateFileService generateFileService = new XlsGenerateFileService();

        generateFileService.generate("./target/demo.xls", 1, 20, new SegmentalCallback() {
            @Override
            public Pagination<Row> call(int pageIndex) {
                List<Row> rows = new ArrayList();
                for (int i = 0; i < 20; i++) {
                    Row row = new Row();
                    row.addValue("张三" + i);
                    row.addValue(Integer.toString(i));
                    rows.add(row);
                }
                Pagination<Row> pagination = new Pagination<Row>(rows, 20, 2);
                pagination.addColumn("姓名", "VARCHAR", String.class);
                pagination.addColumn("年龄", "INTEGER", String.class);
                return pagination;
            }
        });
    }

    @Test
    public void test1() throws IOException, BiffException, WriteException {
        Workbook workbook = Workbook.getWorkbook(new File("./target/demo.xls"));
        System.out.println(workbook);
        int row = workbook.getSheet(0).getRows();
        System.out.println(row);
        WritableWorkbook writableWorkbook = Workbook.createWorkbook(new File("./target/demo.xls"), workbook);
        WritableSheet writableSheet = writableWorkbook.getSheet(0);
        WritableFont font2 = new WritableFont(WritableFont.TAHOMA, 11, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
        WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
        cellFormat2.setBackground(Colour.WHITE);
        cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
        cellFormat2.setWrap(true);
        Label label = new Label(0, 22, "1234", cellFormat2);
        writableSheet.addCell(label);
        writableWorkbook.write();
        writableWorkbook.close();
    }
}
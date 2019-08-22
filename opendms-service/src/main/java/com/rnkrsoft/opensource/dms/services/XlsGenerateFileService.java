package com.rnkrsoft.opensource.dms.services;

import com.rnkrsoft.opensource.dms.xls.Column;
import com.rnkrsoft.opensource.dms.xls.Sheet;
import com.rnkrsoft.opensource.dms.xls.SheetMetadata;
import com.rnkrsoft.opensource.orm.metadata.Headers;
import com.rnkrsoft.opensource.orm.metadata.Pagination;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.*;
import jxl.write.*;
import org.springframework.stereotype.Service;

import javax.web.data.sql.Header;
import javax.web.data.sql.Row;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * Created by rnkrsoft.com on 2019/7/2.
 */
@Service("xls:GenerateFileService")
public class XlsGenerateFileService implements GenerateFileService {
    @Override
    public void generate(String fileName, int pageNum, int pageSize, SegmentalCallback callback) {
        File file = new File(fileName);
        File parent = file.getParentFile();
        //如果存放的路径不存在，则创建路径
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        FileOutputStream fos = null;
        WritableWorkbook wb = null;
        WritableSheet writableSheet = null;
        try {
            fos = new FileOutputStream(file);
            wb = Workbook.createWorkbook(fos);
            writableSheet = wb.createSheet("导出数据", 0);
            for (int i = 0; i < pageNum; i++) {
                Pagination<Row> pagination = callback.call(i);
                SheetMetadata sheetMetadata = new SheetMetadata();
                sheetMetadata.setDesc("导出数据");
                Headers headers = pagination.getHeader();
                List<String> columns = headers.getColumns();
                for (String columnName : columns) {
                    Header header = headers.getColumn(columnName);
                    sheetMetadata.addColumn(Column.builder().desc(header.getTitle()).javaType(header.getJavaType()).build());
                }
                Sheet sheet = new Sheet(sheetMetadata);
                for (Row row : pagination.getRecords()) {
                    sheet.add(row);
                }
                sheetMetadata.setSheet(sheet);
                try {
                    if (pagination.isFristPage()) {
                        header(sheet, wb, writableSheet);
                    }
                    data((pagination.getCurPageNo() - 1) * pageSize, sheet, wb, writableSheet);
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (wb != null) {
                    wb.write();
                    wb.close();
                }
                if (fos != null) {
                    fos.close();
                    fos = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    static void data(int offset, Sheet sheet, WritableWorkbook workbook, WritableSheet writableSheet) throws WriteException {
        for (int i = 0; i < sheet.getRowSize(); i++) {
            int idx = offset + i + 1;
            Row record = sheet.getData().get(i);
            for (int j = 0; j < record.getValues().size(); j++) {
                Column column = sheet.getMetadata().getColumns().get(j);
                WritableFont font2 = new WritableFont(WritableFont.TAHOMA, 11, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
                WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
                cellFormat2.setAlignment(getAlignment(column));
                cellFormat2.setBackground(Colour.WHITE);
                cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
                cellFormat2.setWrap(true);
                Object val = record.getValues().get(j);
                String value = val == null ? "" : val.toString();
                Label label = new Label(j, idx, value, cellFormat2);
                writableSheet.addCell(label);
            }
        }
    }

    static void header(Sheet sheet, WritableWorkbook workbook, WritableSheet writableSheet) throws WriteException {
        SheetMetadata metadata = sheet.getMetadata();
        for (int i = 0; i < metadata.getColumns().size(); i++) {
            Column colMetadata = metadata.getColumns().get(i);
            WritableFont font2 = new WritableFont(WritableFont.TAHOMA, 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
            cellFormat2.setAlignment(Alignment.CENTRE);
            cellFormat2.setBackground(Colour.YELLOW2);
            cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellFormat2.setWrap(false);
            Label label = new Label(i, 0, colMetadata.getDesc(), cellFormat2);
            writableSheet.addCell(label);
        }
    }

    static Alignment getAlignment(Column column) {
        if (column.getJavaType() == Integer.TYPE || column.getJavaType() == Integer.class) {
            return Alignment.RIGHT;
        } else if (column.getJavaType() == String.class) {
            return Alignment.LEFT;
        } else {
            return Alignment.CENTRE;
        }
    }
}

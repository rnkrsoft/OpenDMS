package com.rnkrsoft.opensource.dms.services;

import com.rnkrsoft.opensource.orm.metadata.Headers;
import com.rnkrsoft.opensource.orm.metadata.Pagination;
import com.rnkrsoft.time.DateStyle;
import com.rnkrsoft.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.web.data.sql.Header;
import javax.web.data.sql.Row;
import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by rnkrsoft.com on 2019/7/2.
 */
@Service("csv:GenerateFileService")
public class CsvGenerateFileService implements GenerateFileService {

    /**
     * 向文件中写入头信息
     * @param writer
     * @param headers
     * @throws IOException
     */
    void writeRow(BufferedWriter writer, Headers headers) throws IOException {
        StringBuilder buffer = new StringBuilder();
        int count = 0;
        for (String name : headers.getColumns()){
            count++;
            Header header = headers.getColumn(name);
            buffer.append(header.getTitle());
            if (count < headers.getColumns().size()){
                buffer.append(",");
            }
        }
        buffer.append("\n");
        writer.write(buffer.toString());
    }

    /**
     * 向文件中写入行
     * @param writer
     * @param row
     * @throws IOException
     */
    void writeRow(BufferedWriter writer, Row row) throws IOException {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < row.getValues().size() ; i++) {
            Object value = row.getValues().get(i);
            if (value instanceof Date){
                value = DateUtils.toString((Date)value, DateStyle.SECOND_FORMAT2);
            }else if (value instanceof Timestamp){
                value = DateUtils.toString((Timestamp)value, DateStyle.SECOND_FORMAT2);
            }else if (value instanceof Time){
                value = DateUtils.toString((Time)value, DateStyle.SECOND_FORMAT3);
            }else if (value instanceof java.util.Date){
                value = DateUtils.toString((java.util.Date)value, DateStyle.SECOND_FORMAT2);
            }
            //加双引号可以解决内容含逗号的问题
            buffer.append('"')
                    .append(value)
                    .append('"');
            if (i+1 < row.getValues().size() ){
                buffer.append(",");
            }
        }
        buffer.append("\n");
        writer.write(buffer.toString());
    }

    @Override
    public void generate(String fileName, int pageNum, int pageSize, SegmentalCallback callback) {
        File file = null;
        BufferedWriter writer = null;
        try {
            file = new File(fileName);
            File parent = file.getParentFile();
            //如果存放的路径不存在，则创建路径
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            // GBK使正确读取分隔符","
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "GBK"), 1024);
            for (int i = 0; i < pageNum; i++) {
                Pagination<Row> pagination = callback.call(i);
                if (pagination.isFristPage()){
                    writeRow(writer, pagination.getHeader());
                }
                for (Row row : pagination.getRecords()) {
                    writeRow(writer, row);
                }
            }
        } catch (Exception e) {

        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

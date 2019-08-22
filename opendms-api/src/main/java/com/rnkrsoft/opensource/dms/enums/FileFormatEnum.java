package com.rnkrsoft.opensource.dms.enums;

import com.rnkrsoft.interfaces.EnumIntegerCode;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
public enum FileFormatEnum implements EnumIntegerCode{
    CSV(1, "CSV文件", "csv"),
    XLS(2, "XLS文件", "xls");

    FileFormatEnum(int code, String desc, String suffix) {
        this.code = code;
        this.desc = desc;
        this.suffix = suffix;
    }

    int code;
    String desc;
    String suffix;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public String getSuffix() {
        return suffix;
    }

    public static FileFormatEnum valueOfCode(int code){
        for (FileFormatEnum value : values()){
            if (value.code == code){
                return value;
            }
        }
        return CSV;
    }
}

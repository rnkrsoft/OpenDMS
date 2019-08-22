package com.rnkrsoft.opensource.dms.enums;

import com.rnkrsoft.interfaces.EnumIntegerCode;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
public enum DataSourceTypeEnum implements EnumIntegerCode{
    MY_SQL(1, "MySQL", "mysql"),
    HIVE(2, "HIVE", "hive");

    DataSourceTypeEnum(int code, String desc, String jdbc) {
        this.code = code;
        this.desc = desc;
        this.jdbc = jdbc;
    }

    int code;
    String desc;
    String jdbc;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public String getJdbc() {
        return jdbc;
    }

    public static DataSourceTypeEnum valueOfCode(int code){
        for (DataSourceTypeEnum value : values()){
            if (value.code == code){
                return value;
            }
        }
        throw new IllegalArgumentException("无效的编码" + code);
    }
}

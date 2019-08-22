package com.rnkrsoft.opensource.dms.enums;

import com.rnkrsoft.interfaces.EnumIntegerCode;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
public enum TemplateStatusEnum implements EnumIntegerCode{
    DELETED(-1, "删除"),
    NORMAL(1, "正常");

    TemplateStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    int code;
    String desc;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
    public static TemplateStatusEnum valueOfCode(int code){
        for (TemplateStatusEnum value : values()){
            if (value.code == code){
                return value;
            }
        }
        return DELETED;
    }
}

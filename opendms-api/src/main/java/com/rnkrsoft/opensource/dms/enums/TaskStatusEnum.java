package com.rnkrsoft.opensource.dms.enums;

import com.rnkrsoft.interfaces.EnumIntegerCode;

/**
 * Created by rnkrsoft.com on 2019/6/29.
 */
public enum TaskStatusEnum implements EnumIntegerCode{
    INIT(1, "等待执行"),
    EXECUTING(2, "执行中"),
    FAILURE(3, "失败"),
    FINISH(4, "已完成"),
    DELETED(5, "已删除"),
    CANCELING(6, "正在取消");

    TaskStatusEnum(int code, String desc) {
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
    public static TaskStatusEnum valueOfCode(int code){
        for (TaskStatusEnum value : values()){
            if (value.code == code){
                return value;
            }
        }
        return INIT;
    }
}

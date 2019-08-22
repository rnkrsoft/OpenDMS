package com.rnkrsoft.opensource.dms.xls;

import lombok.Builder;
import lombok.Data;

import javax.web.doc.enums.WebLayout;

@Data
@Builder
public class Column {
    /**
     * 描述
     */
    String desc;
    /**
     * 布局
     */
    WebLayout layout;
    /**
     * 颜色
     */
    String color;
    /**
     * Java类型
     */
    Class javaType;
    /**
     * 对应的序号
     */
    int index = -1;
}

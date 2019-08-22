package com.rnkrsoft.opensource.dms.jdbc.bo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rnkrsoft.com on 2019/7/1.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QueryTemplateBO implements Serializable{
    Integer templateId;
    String templateName;
    String sqlStatement;
    Integer maxRowSize;
    Integer dataSourceId;
    String dataSourceName;
    Integer dataSourceType;
    Integer templateStatus;
    Date createDate;
    Date lastUpdateDate;
}

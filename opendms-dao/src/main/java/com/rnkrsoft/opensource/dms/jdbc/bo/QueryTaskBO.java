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
public class QueryTaskBO implements Serializable{
    Integer taskId;
    String taskName;
    Integer templateId;
    String sqlStatement;
    Integer taskProgress;
    String createUserId;
    String createUserName;
    String finishDate;
    Integer taskStatus;
    String failureCause;
    Date createDate;
    Date lastUpdateDate;
}

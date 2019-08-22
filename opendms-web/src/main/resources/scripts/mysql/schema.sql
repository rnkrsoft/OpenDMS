-- TABLE 
drop table if exists tb_data_source;

create table if not exists tb_data_source(
 data_source_id integer not null  auto_increment comment '数据源编号',
 data_source_name varchar(255) not null  comment '数据源名称',
 data_source_type integer not null  comment '数据源类型',
 host varchar(255) not null  comment '地址',
 port integer not null  comment '端口',
 driver_class_name varchar(255) not null  comment '驱动类名',
 schema_name varchar(255) not null  comment '数据库名',
 user_name varchar(255) not null  comment '用户名',
 password varchar(255) not null  comment '密码',
 remark varchar(255) null  comment '备注',
 data_source_status integer not null  default 1 comment '状态',
 create_date timestamp not null  default current_timestamp comment '创建日期',
 last_update_date timestamp not null  default current_timestamp on update current_timestamp comment '更新日期',
 primary key(data_source_id)
) comment = '数据源信息表';


-- TABLE 
drop table if exists tb_task;

create table if not exists tb_task(
 task_id integer not null  auto_increment comment '任务编号',
 task_name varchar(255) not null  comment '任务名称',
 template_id integer not null  comment '模板编号',
 template_sql_statement varchar(255) not null  comment '模板中定义的SQL语句',
 sql_statement varchar(255) not null  comment '实际执行的SQL语句,仅作为展示用途，真实的SQL语句通过占位符和参数实现',
 task_arguments varchar(255) not null  comment '任务参数',
 file_format integer not null  default 1 comment '文件格式 1:CSV文件  2:XLS文件  ',
 task_progress integer not null  default 0 comment '进度（%）',
 create_user_id varchar(255) not null  comment '创建用户号',
 create_user_name varchar(255) not null  comment '创建用户名',
 finish_date varchar(255) null  comment '完成日期 yyy/MM/dd hh:mm:ss',
 task_status integer not null  comment '任务状态 1:等待执行  2:执行中  3:失败  4:已完成  5:已删除  6:正在取消  ',
 failure_cause text null  comment '失败原因',
 generate_file_name text null  comment '生成文件',
 share_code text null  comment '分享验证码',
 allow_download_count integer null  comment '允许下载次数',
 latest_download_date timestamp not null  default current_timestamp comment '最迟下载日期',
 create_date timestamp not null  default current_timestamp comment '创建日期',
 last_update_date timestamp not null  default current_timestamp on update current_timestamp comment '更新日期',
 primary key(task_id)
) comment = '任务信息表';


-- TABLE 
drop table if exists tb_template;

create table if not exists tb_template(
 template_id integer not null  auto_increment comment '模板编号',
 template_name varchar(255) not null  comment '模板名称',
 sql_statement varchar(255) not null  comment 'SQL语句',
 max_row_size integer not null  default 1000 comment '最大行数',
 data_source_id integer not null  comment '数据源编号',
 template_status integer not null  default 1 comment '模板状态',
 create_date timestamp not null  default current_timestamp comment '创建日期',
 last_update_date timestamp not null  default current_timestamp on update current_timestamp comment '更新日期',
 primary key(template_id)
) comment = '模板信息表';



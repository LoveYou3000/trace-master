-- 实例表
CREATE TABLE tb_instances
(
    instance_id   varchar(36) not null comment '实例id',
    app_id        varchar(25) not null comment 'appId',
    access_mode   int         not null comment '接入方式 0:agent，1:attach，2:starter',
    active_status int         not null comment '状态 0:未启用，1:启用',
    create_by     varchar(10) null default '' comment '创建人',
    create_at     datetime    null default current_timestamp() comment '创建时间',
    update_by     varchar(10) null default '' comment '更新人',
    update_at     datetime    null default current_timestamp() comment '更新时间',
    primary key (instance_id)
);

-- 配置表
CREATE TABLE tb_config
(
    app_id    varchar(25) not null comment 'appId',
    -- 其他字段
    create_by varchar(10) null default '' comment '创建人',
    create_at datetime    null default current_timestamp() comment '创建时间',
    update_by varchar(10) null default '' comment '更新人',
    update_at datetime    null default current_timestamp() comment '更新时间',
    primary key (app_id)
);
create table recipe_result
(
    id               varchar(64) null,
    user_id          varchar(64) null,
    r_date           date        null,
    breakfast        text        null,
    lunch            text        null,
    dinner           text        null,
    create_date      datetime    null,
    update_date      datetime    null,
    dietary_advice   text        null comment '膳食建议',
    total_calories   varchar(64) null comment '总热量',
    health_education text        null
);

alter table recipe_result
    add constraint recipe_result_pk
        primary key (id);

alter table recipe_result
    add constraint idx_user_id_r_date
        unique (user_id, r_date);

create table sports_result
(
    id                        varchar(64) null,
    warm_up_before_exercise   text        null comment '运动前热身',
    specific_sports           text        null comment '具体运动',
    stretching_after_exercise text        null comment '运动后拉伸',
    health_education          text        null comment '健康教育',
    r_date                    datetime    null,
    user_id                   varchar(64) null,
    create_date               datetime    null,
    update_date               datetime         null
);

alter table sports_result
    add constraint sports_result_pk
        primary key (id);

alter table sports_result
    add constraint idx_user_id_r_date
        unique (user_id, r_date);


create table psychology_result
(
    id          varchar(64) not null,
    user_id     varchar(64) null,
    result      tinytext    null comment '判断结果',
    advice      text        null,
    create_date datetime    null,
    update_date datetime    null,
    constraint psychology_result_pk
        primary key (id)
);

create index idx_user_id
    on psychology_result (user_id);

alter table psychology_result
    add health_education text null;



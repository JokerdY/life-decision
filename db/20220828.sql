create table psychological_outcome
(
    id          varchar(64) null,
    anxiety     varchar(64) null comment '焦虑',
    depression  varchar(64) null comment '抑郁',
    pressure    varchar(64) null comment '压力',
    user_id     varchar(64) not null,
    create_date datetime    null,
    update_date datetime    null
)
    comment '心理结果';

create index idx_user_id
    on psychological_outcome (user_id);

UPDATE question_information t SET t.question_name = '您的血压(请用/分割高低血压)' WHERE t.id = 175 ;
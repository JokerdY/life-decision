create table questionnaire_group_information
(
    group_id               varchar(64) null,
    questionnaire_id varchar(64) null,
    create_date      datetime    null,
    update_date      datetime    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
    submit_id        varchar(64) null,
    user_id          varchar(64) null
);

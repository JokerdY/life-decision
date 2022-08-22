create table chinese_medicine
(
    id                  varchar(64) null,
    create_date         datetime    null,
    update_date         datetime    null,
    user_id             varchar(64) null,
    acupressure         text        null,
    five_elements_music text        null
);

create index chinese_medicine_user_id_create_date_index
    on chinese_medicine (user_id, create_date);

alter table chinese_medicine
    add group_id varchar(64) null;

alter table psychology_result
    add group_id varchar(64) null;


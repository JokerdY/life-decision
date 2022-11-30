create index idx_question_id_create_date
    on question_answer (question_id, create_date);

create index idx_question_id
    on question_answer (question_id);

alter table psychology_result
    add physical text null;


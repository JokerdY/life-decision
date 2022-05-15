alter table questionnaire_submit_information
    add `is_admin_submit` int default 0 null;

alter table questionnaire_submit_information
    add `questionnaire_status` int default 1 null;

insert into sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUE (51,'questionnaire_submit_information',0,'已保存',1,'questionnaire_status');
insert into sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUE (52,'questionnaire_submit_information',1,'已提交',2,'questionnaire_status');
alter table user_information
    add marital_status int null comment '婚姻状态';

alter table user_information
    add household_income int null comment '家庭收入';

INSERT INTO sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUES ('41', 'user_information', 1, '未婚', 1, 'marital_status');
INSERT INTO sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUES ('42', 'user_information', 2, '已婚', 2, 'marital_status');
INSERT INTO sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUES ('43', 'user_information', 3, '未婚', 3, 'marital_status');
INSERT INTO sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUES ('44', 'user_information', 4, '离婚', 4, 'marital_status');
INSERT INTO sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUES ('45', 'user_information', 5, '未说明婚姻状况', 5, 'marital_status');
INSERT INTO sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUES ('46', 'user_information', 1, '＜2000元/月', 1, 'household_income');
INSERT INTO sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUES ('47', 'user_information', 2, '≥2000且＜5000元/月', 2, 'household_income');
INSERT INTO sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUES ('48', 'user_information', 3, '≥5000且＜8000元/月', 3, 'household_income');
INSERT INTO sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUES ('49', 'user_information', 4, '≥8000且＜10000元/月', 4, 'household_income');
INSERT INTO sys_dict (id, table_name, column_value, convert_name, sort_id, column_name) VALUES ('50', 'user_information', 5, '＞10000元/月', 5, 'household_income');
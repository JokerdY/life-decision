delete
from question_information
where questionnaire_id = '3';
delete
from option_information
where questionnaire_id = '3';

INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (187, '3', 1, '是否食用米饭', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (188, '3', 2, '是否食用粥、稀饭或泡饭', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (189, '3', 3, '是否食用面粉类食物（馒头、面包、面条、饼等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (190, '3', 4, '是否食用甜食、点心、蛋糕', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (191, '3', 5, '是否食用油炸食物（油条、油饼等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (192, '3', 6, '是否食用有馅类食物（包子、馄饨、饺子等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (193, '3', 7, '是否食用食用粗杂粮（包括糙米、小米、玉米、薏米、燕麦、红小豆、绿豆等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (194, '3', 8, '是否食用薯类（包括红薯、土豆、芋头、山药、魔芋等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (195, '3', 9, '是否食用牛奶、酸奶或奶粉', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (196, '3', 10, '是否食用鸡蛋或鸭蛋', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (197, '3', 11, '是否食用红肉类菜肴（猪、牛、羊肉等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (198, '3', 12, '是否食用家禽类菜肴（鸡、鸭、鹅肉等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (199, '3', 13, '是否食用加工肉制品（香肠、熏肉、午餐肉、火腿等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (200, '3', 14, '是否食用河鲜类菜肴（青鱼、鲈鱼、鲢鱼、河虾等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (201, '3', 15, '是否食用海鲜类菜肴（带鱼、鲳鱼、黄鱼、海虾等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (202, '3', 16, '是否食用豆制品菜肴（豆腐、香干、素鸡等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (203, '3', 17, '是否食用坚果类（西瓜子、葵花子、核桃、花生、开心果等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (204, '3', 18, '是否食用深色蔬菜类（青菜、菠菜、空心菜、番茄、青椒、胡萝卜等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (205, '3', 19, '是否食用浅色蔬菜类（白菜、萝卜、黄瓜等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (206, '3', 20, '是否食用菌菇类（香菇、草菇、平菇等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (207, '3', 21, '是否食用水果（苹果、梨、香蕉等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (208, '3', 22, '是否饮用甜饮料（可乐、雪碧、冰红茶、运动饮料等）', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (209, '3', 23, '是否饮用啤酒', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (210, '3', 24, '饮用啤酒的量（毫升）', '8', 1, 209);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (211, '3', 25, '是否饮用黄酒', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (212, '3', 26, '饮用黄酒的量（两）', '8', 1, 211);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (213, '3', 27, '是否饮用白酒', '8', 2, null);
INSERT INTO question_information (id, questionnaire_id, question_sort, question_name, big_label_id, question_type, parent_id) VALUES (214, '3', 28, '饮用白酒的量（两）', '8', 1, 213);


insert into `user` (`member_id` , `password`, `user_name`) values ('seo', '$2a$10$hCznWplpeN.33/xIwr0ePOPmse04xalUOSLeB9I8WNZ02G2nHtcjG', 'name');
insert into `user_roles` (`user_member_id`, `roles`) values ('seo', 'USER');


-- Gym
insert into `gym` (`name`, `content`) values ('더클 마곡', '마곡점');


-- Post
insert into `post` (`content`, `difficulty`, `member_id`, `gym_id`) values ('내용1', '111','seo', "1");
insert into `post` (`content`, `difficulty`, `member_id`, `gym_id`) values ('내용2', '222','seo', "1");
insert into `post` (`content`, `difficulty`, `member_id`, `gym_id`) values ('내용3', '333','seo', "1");

insert into "ROLE" (ROLE, NAME, LEVEL) values ('ROLE_USER', 'USER', 1)
insert into "ROLE" (ROLE, NAME, LEVEL) values ('ROLE_ADMIN', 'ADMIN', 2)

insert into "TODAY_RATE" (RATE_NUM, RATE_STR) values (0, '별로')
insert into "TODAY_RATE" (RATE_NUM, RATE_STR) values (2, '보통')
insert into "TODAY_RATE" (RATE_NUM, RATE_STR) values (3, '좋음')
insert into "TODAY_RATE" (RATE_NUM, RATE_STR) values (4, '최고')

insert into "DRAW_STYLE" (STYLE_ENG, STYLE_KOR) values ('Watercolor style, ', '수채화')
insert into "DRAW_STYLE" (STYLE_ENG, STYLE_KOR) values ('Cartoon style, ', '카툰')
insert into "DRAW_STYLE" (STYLE_ENG, STYLE_KOR) values ('Drawing without color , ', '스케치')
insert into "DRAW_STYLE" (STYLE_ENG, STYLE_KOR) values ('Draw in pointillism, ', '점묘화')
insert into "DRAW_STYLE" (STYLE_ENG, STYLE_KOR) values ('Draw in Van Gogh style, ', '반 고흐')

insert into "CHALLENGES" (ACHIEVED_ICON_PATH, UNACHIEVED_ICON_PATH, NAME, SPECIFIC) values ('', '', '일기 쪼가리', '일기를 10일 연속으로 작성하셨습니다.')
insert into "CHALLENGES" (ACHIEVED_ICON_PATH, UNACHIEVED_ICON_PATH, NAME, SPECIFIC) values ('', '', '일기 책', '대단해요! 일기를 20일 연속으로 작성하셨습니다.')
insert into "CHALLENGES" (ACHIEVED_ICON_PATH, UNACHIEVED_ICON_PATH, NAME, SPECIFIC) values ('', '', '일기 대장경', '당신은 사람입니까? 일기를 30일 연속으로 작성하셨습니다.')
insert into "CHALLENGES" (ACHIEVED_ICON_PATH, UNACHIEVED_ICON_PATH, NAME, SPECIFIC) values ('', '', '편 - 안', '하고싶은 말이 많은 당신! 1000글자를 꽉 채우셨네요.')
insert into "CHALLENGES" (ACHIEVED_ICON_PATH, UNACHIEVED_ICON_PATH, NAME, SPECIFIC) values ('', '', '엔간히 해라', '시간이 많으신가요? 그림을 10번이나 수정했습니다.')

-- admin user
insert into "USERS" (NICKNAME, PASSWORD, USERNAME) VALUES ('admin', '$2a$10$FA89.0pAwJu9Hff/i1C82e.nKlEhCN.P/eHsth/UBDBL5/ajowxkm', 'admin')
insert into user_and_role (role_id, user_id) values (1, 1)
insert into user_and_role (role_id, user_id) values (2, 1)

-- user
insert into "USERS" (NICKNAME, PASSWORD, USERNAME) VALUES ('hayden', '$2a$10$FA89.0pAwJu9Hff/i1C82e.nKlEhCN.P/eHsth/UBDBL5/ajowxkm', '1234')
insert into user_and_role (role_id, user_id) values (1, 2)
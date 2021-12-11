-- 数据库 Data 脚本
DELETE
FROM user;

INSERT INTO user (id, name, age, email, role_id)
VALUES (1, 'Jone', 18, 'test1@moon.com', 1),
       (2, 'Jack', 20, 'test2@moon.com', 2),
       (3, 'Tom', 28, 'test3@moon.com', 2),
       (4, 'Sandy', 21, 'test4@moon.com', 3),
       (5, 'Billie', 24, 'test5@moon.com', 3);

DELETE
FROM role;

INSERT INTO role (id, role_name, role_describe)
VALUES (1, '管理员', 'boos 级别'),
       (2, '用户', '就是个普通人'),
       (3, '程序猿', '偶尔需要用来祭天');

DELETE
FROM goods;

INSERT INTO goods (id, name)
VALUES (1, '笔记本电脑'),
       (2, '手机'),
       (3, '机械键盘');
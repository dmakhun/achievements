INSERT INTO accessrole (id, LastModifiedDate, name)
VALUES (1, NOW(), 'ROLE_ADMIN');
INSERT INTO accessrole (id, LastModifiedDate, name)
VALUES (2, NOW(), 'ROLE_USER');
INSERT INTO accessrole (id, LastModifiedDate, name)
VALUES (3, NOW(), 'ROLE_MANAGER');
INSERT INTO accessrole (id, LastModifiedDate, name)
VALUES (4, NOW(), 'ROLE_ANONYMOUS');

INSERT INTO achievements.user (id, LastModifiedDate, email, name, password, picture, surname,
                               username, role_id)
VALUES (1, NOW(), 'admin@achievements.com', 'AdminName',
        '$2a$10$HVMmtifYuGlGFXdh3kU0WeF0N6GGR1H0fBP1bKKdkox.ilntJAuOy', null, 'AdminSurname',
        'admin', 1);
INSERT INTO achievements.user (id, LastModifiedDate, email, name, password, picture, surname,
                               username, role_id)
VALUES (2, NOW(), 'manager@achievements.com', 'ManagerName',
        '$2a$10$mz566BYGu6upK7u3i8rPwOqC3KG409D2jSo8NIOlxc2NHStyUPqmi', null, 'ManagerSurname',
        'manager', 2);
INSERT INTO achievements.user (id, LastModifiedDate, email, name, password, picture, surname,
                               username, role_id)
VALUES (3, NOW(), 'user@achievements.com', 'UserName',
        '$2a$10$db3OY6Z4QyR.AEnkmWO2x./6CLh6kAWd2W92ym4wTOGzqBSsqlVG2', null, 'UserSurname', 'user',
        3);
INSERT IGNORE INTO roles (id, last_modified_date, name)
VALUES (1, NOW(), 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, last_modified_date, name)
VALUES (2, NOW(), 'ROLE_MANAGER');
INSERT IGNORE INTO roles (id, last_modified_date, name)
VALUES (3, NOW(), 'ROLE_USER');
INSERT IGNORE INTO roles (id, last_modified_date, name)
VALUES (4, NOW(), 'ROLE_ANONYMOUS');

# admin/admin
INSERT IGNORE INTO achievements.users (id, last_modified_date, email, name, password, picture, surname,
                                       username, role_id)
VALUES (1, NOW(), 'admin@achievements.com', 'AdminName',
        '$2a$10$RDfUBX.xGmJ406MR0kSL2u.DmainGJJlSF86WBpkJNGHFI5iwiT0K', null, 'AdminSurname',
        'admin', 1);
# manager/manager
INSERT IGNORE INTO achievements.users (id, last_modified_date, email, name, password, picture, surname,
                                       username, role_id)
VALUES (2, NOW(), 'manager@achievements.com', 'ManagerName',
        '$2a$10$mz566BYGu6upK7u3i8rPwOqC3KG409D2jSo8NIOlxc2NHStyUPqmi', null, 'ManagerSurname',
        'manager', 2);
# user/user
INSERT IGNORE INTO achievements.users (id, last_modified_date, email, name, password, picture, surname,
                                       username, role_id)
VALUES (3, NOW(), 'user@achievements.com', 'UserName',
        '$2a$10$db3OY6Z4QyR.AEnkmWO2x./6CLh6kAWd2W92ym4wTOGzqBSsqlVG2', null, 'UserSurname', 'user',
        3);
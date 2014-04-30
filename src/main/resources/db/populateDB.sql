DELETE FROM [DB_Achievements].[dbo].[ach_role]
DELETE FROM [DB_Achievements].[dbo].[ach_competence]
DELETE FROM [DB_Achievements].[dbo].[ach_group]
DELETE FROM [DB_Achievements].[dbo].[ach_AchievementType]
DELETE FROM [DB_Achievements].[dbo].[ach_user]
DELETE FROM [DB_Achievements].[dbo].[ach_Achievement]
DELETE FROM [DB_Achievements].[dbo].[ach_UserToGroup]
DELETE FROM [DB_Achievements].[dbo].[ach_UserToCompetence]

SET IDENTITY_INSERT ach_role ON
INSERT INTO ach_role (id, lastmodifieddate, uuid, name)
VALUES (1, 2014-03-02, 'i1', 'user')
INSERT INTO ach_role (id, lastmodifieddate, uuid, name)
VALUES (2, 2014-04-01, 'i2', 'admin')
INSERT INTO ach_role (id, lastmodifieddate, uuid, name)
VALUES (3, 2014-04-05, 'i3', 'manager')
SET IDENTITY_INSERT ach_role OFF

SET IDENTITY_INSERT ach_user ON
INSERT INTO ach_user (id, lastmodifieddate, uuid, email, name, password, picture, surname, username, role_id)
VALUES (7, 2014-03-02, 'i1', 'vova@gmail.com', 'Vova',  'password1', null, 'Vova', 'Vova', 1)
INSERT INTO ach_user (id, lastmodifieddate, uuid, email, name, password, picture, surname, username, role_id)
VALUES (1, 2014-04-01, 'i2', 'ivan@gmail.com', 'Ivan',  'password2', null, 'Ivan', 'Ivan', 1)
INSERT INTO ach_user (id, lastmodifieddate, uuid, email, name, password, picture, surname, username, role_id)
VALUES (2, 2014-03-15, 'i3', 'max@gmail.com', 'Max',  'password3', null, 'Max', 'Max', 3)
SET IDENTITY_INSERT ach_user OFF

SET IDENTITY_INSERT ach_competence ON
INSERT INTO ach_competence (id, name, created, lastmodifieddate, uuid)
VALUES (1, 'Java', 2014-03-02, 2014-04-02, 'i1')
INSERT INTO ach_competence (id, name, created, lastmodifieddate, uuid)
VALUES (2, '.NET', 2014-03-02, 2014-04-02, 'i1')
SET IDENTITY_INSERT ach_competence OFF

SET IDENTITY_INSERT ach_AchievementType ON
INSERT INTO ach_AchievementType (id, lastmodifieddate, uuid, name, points, competence_id)
VALUES (1, 2014-03-02, 'i1', 'Gold', 100, 1)
INSERT INTO ach_AchievementType (id, lastmodifieddate, uuid, name, points, competence_id)
VALUES (2, 2014-03-02, 'i2', 'Silver', 90, 1)
INSERT INTO ach_AchievementType (id, lastmodifieddate, uuid, name, points, competence_id)
VALUES (3, 2014-03-02, 'i1', 'Bronze', 80, 2)
INSERT INTO ach_AchievementType (id, lastmodifieddate, uuid, name, points, competence_id)
VALUES (4, 2014-03-02, 'i3', 'SilverSuper', 85, 1)
SET IDENTITY_INSERT ach_AchievementType OFF

SET IDENTITY_INSERT ach_Achievement ON
INSERT INTO ach_Achievement (id, lastmodifieddate, uuid, comment, created, achievement_type_id, user_id)
VALUES (1, 2014-03-02, 'i1', 'comment1', 2014-03-07, 1, 7)
SET IDENTITY_INSERT ach_Achievement OFF

SET IDENTITY_INSERT ach_group ON
INSERT INTO ach_group (ID, CLOSED, NAME, OPENED, COMPETENCE_ID, LASTMODIFIEDDATE, UUID)
VALUES (1, 2014-04-02, 'Java-107', 2014-01-02, 1, 2014-03-02, 'i1')
INSERT INTO ach_group (ID, CLOSED, NAME, OPENED, COMPETENCE_ID, LASTMODIFIEDDATE, UUID)
VALUES (2, 2014-04-02, '.NET-107', 2014-01-02, 2, 2014-03-02, 'i2');
SET IDENTITY_INSERT ach_group OFF

INSERT INTO ach_UserToGroup (GROUP_ID, USER_ID)
VALUES (1,7)
INSERT INTO ach_UserToGroup (GROUP_ID, USER_ID)
VALUES (2,7)

INSERT INTO ach_UserToCompetence (COMPETENCE_ID, USER_ID)
VALUES (1,7)
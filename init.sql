INSERT INTO roles(id, name) VALUES(1, 'ROLE_USER');
INSERT INTO roles(id, name) VALUES(2, 'ROLE_ADMIN');
INSERT INTO roles(id, name) VALUES(3, 'ROLE_SCRIPTWRITER');
INSERT INTO roles(id, name) VALUES(4, 'ROLE_MANAGER');
INSERT INTO roles(id, name) VALUES(5, 'ROLE_ADVERTISER');
INSERT INTO roles(id, name) VALUES(6, 'ROLE_ACTOR');

INSERT INTO application_states(id, name) VALUES(1, 'STATE_NEW');
INSERT INTO application_states(id, name) VALUES(2, 'STATE_ACCEPTED');
INSERT INTO application_states(id, name) VALUES(3, 'STATE_CANCELLED');
INSERT INTO application_states(id, name) VALUES(4, 'STATE_DONE');

INSERT INTO application_types(id, name) VALUES(1, 'TYPE_AD');
INSERT INTO application_types(id, name) VALUES(2, 'TYPE_DECORATION');

INSERT INTO file_types(id, name) VALUES(1, 'TYPE_TASK');
INSERT INTO file_types(id, name) VALUES(2, 'TYPE_RESULT');
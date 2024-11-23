
INSERT INTO author (firstname, lastname) VALUES ('Jean', 'Dujardin');
INSERT INTO author (firstname, lastname) VALUES ('Jane', 'Doe');

INSERT INTO post (title, text, category, created_date, author_id) VALUES ('title_1', 'text_1', 'message', now(), 1);
INSERT INTO post (title, text, category, created_date, author_id) VALUES ('title_1', 'text_2', 'message', now(), 1);
INSERT INTO post (title, text, category, created_date, author_id) VALUES ('title_3', 'text_3', 'message', now(), 2);
INSERT INTO post (title, text, category, created_date, author_id) VALUES ('title_4', 'text_4', 'message', now(), 2);
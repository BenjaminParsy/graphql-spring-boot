
DELETE FROM review;
DELETE FROM book;
DELETE FROM author;

INSERT INTO author (id, firstname, lastname) VALUES (1, 'Jean', 'Dujardin');
INSERT INTO author (id, firstname, lastname) VALUES (2, 'Jane', 'Doe');

INSERT INTO book (id, title, text, category, created_date, author_id)
VALUES (1, 'title_1', 'text_1', 'novel', '2024-12-17', 1);

INSERT INTO book (id, title, text, category, created_date, author_id)
VALUES (2, 'title_2', 'text_2', 'novel', '2024-12-18', 1);

INSERT INTO book (id, title, text, category, created_date, author_id)
VALUES (3, 'title_3', 'text_3', 'novel', '2024-12-19', 2);

INSERT INTO book (id, title, text, category, created_date, author_id)
VALUES (4, 'title_4', 'text_4', 'novel', '2024-12-20', 2);

INSERT INTO review (id, text, created_by, book_id)
VALUES (1, 'Comment number 1', 'Benjamin', 1);
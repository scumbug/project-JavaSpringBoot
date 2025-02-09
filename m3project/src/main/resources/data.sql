insert into users (name, phone, email, password, admin_status) values
('Administrator', '+65 8123 4567', 'admin@mail.com', '$2a$10$Uh1wSkhDdD3dIPTY/15jJ.WOyfIHGrPECbnDKzt3bGSMdR0dc541C', true);
insert into users (name, email, password) values
('Phoebe Yong', 'phoebeykq@gmail.com', '$2a$10$fIUuek32p3f4nJJB0St37OsroKLvlul3WB/OCXPdhM.hckV/RkU7m'),
('Lye Yong Xin', 'yx@gmail.com', '$2a$10$Mosuxgwl.7EDMn466soSROkSaDnTtDXngDEFK0P4QmC6.HCRdw5iG'),
('Ong Xin Zhi', 'xong002@gmail.com', '$2a$10$6WyisGYRUslEB7EXdPlZUea2A045gcg3Wz8sDPm6GID.eofYoWjba'),
('Edison Zhuang', 'edison@skillsunion.com', 'edison'),
('Wong Chen Pang', 'cpwong@skillsunion.com', 'cpwong'),
('Terence Gaffud', 'terence.gaffud@skillsunion.com', 'terence'),
('Daniel Goh', 'daniel@mail.com', 'daniel'),
('Jennifer Du', 'jennifer.du@ntu.edu.sg', 'jennifer'),
('Jaedon Kwan', 'jaedon.kwan@ntu.edu.sg', 'jaedon');

insert into concerts (artist, concert_date, tickets_available, ticket_price) values
('WESTLIFE', '2023-02-16 20:00', 100, 288.99),
('BACKSTREET BOYS', '2023-02-22 20:00', 100, 288.99),
('PENTATONIX', '2023-03-06 20:00', 80, 188.99),
('BLACKPINK', '2023-05-13 19:30', 120, 388.99),
('NE-YO', '2023-05-22 20:00', 100, 288.99),
('SHILA AMZAH', '2023-06-03 20:00', 80, 188.99),
('THE 1975', '2023-07-18 20:00', 100, 288.99),
('G.E.M.', '2023-08-12 19:00', 90, 288.99),
('MAYDAY', '2023-12-03 19:30', 100, 188.99),
('JAY CHOU', '2023-12-17 20:00', 120, 388.99);

insert into tickets (concert_id, seat_id, user_id, submission_status) values
(3, 'C1', 2, true),
(3, 'B1', 7, true),
(3, 'B2', 8, true),
(4, 'A1', 2, true),
(4, 'A2', 2, true),
(7, 'B1', 4, true),
(7, 'B2', 4, true),
(8, 'A1', 3, true),
(8, 'A2', 3, true),
(9, 'B1', 9, true),
(9, 'A1', 3, true),
(9, 'A2', 3, true),
(10, 'B1', 5, true),
(10, 'B2', 6, true),
(10, 'B3', 10, true);

insert into seats (seat_id, seat_category, venue_hall, ticket_price, concert_type) values
('A1', 'A', 'Hall 1', 398, 'concert_type'),
('A2', 'A', 'Hall 1', 398, 'concert_type'),
('A3', 'A', 'Hall 1', 398, 'concert_type'),
('A4', 'A', 'Hall 1', 398, 'concert_type'),
('A5', 'A', 'Hall 1', 398, 'concert_type'),
('A6', 'A', 'Hall 1', 398, 'concert_type'),
('A7', 'A', 'Hall 1', 398, 'concert_type'),
('A8', 'A', 'Hall 1', 398, 'concert_type'),
('A9', 'A', 'Hall 1', 398, 'concert_type'),
('A10', 'A', 'Hall 1', 398, 'concert_type'),

('B1', 'B', 'Hall 1', 298, 'concert_type'),
('B2', 'B', 'Hall 1', 298, 'concert_type'),
('B3', 'B', 'Hall 1', 298, 'concert_type'),
('B4', 'B', 'Hall 1', 298, 'concert_type'),
('B5', 'B', 'Hall 1', 298, 'concert_type'),
('B6', 'B', 'Hall 1', 298, 'concert_type'),
('B7', 'B', 'Hall 1', 298, 'concert_type'),
('B8', 'B', 'Hall 1', 298, 'concert_type'),
('B9', 'B', 'Hall 1', 298, 'concert_type'),
('B10', 'B', 'Hall 1', 298, 'concert_type'),

('C1', 'C', 'Hall 1', 198, 'concert_type'),
('C2', 'C', 'Hall 1', 198, 'concert_type'),
('C3', 'C', 'Hall 1', 198, 'concert_type'),
('C4', 'C', 'Hall 1', 198, 'concert_type'),
('C5', 'C', 'Hall 1', 198, 'concert_type'),
('C6', 'C', 'Hall 1', 198, 'concert_type'),
('C7', 'C', 'Hall 1', 198, 'concert_type'),
('C8', 'C', 'Hall 1', 198, 'concert_type'),
('C9', 'C', 'Hall 1', 198, 'concert_type'),
('C10', 'C', 'Hall 1', 198, 'concert_type');
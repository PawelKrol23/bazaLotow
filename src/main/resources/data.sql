-- Miasta
INSERT INTO city (id, name, country) VALUES (1, 'Warszawa', 'Polska');
INSERT INTO city (id, name, country) VALUES (2, 'Berlin', 'Niemcy');
INSERT INTO city (id, name, country) VALUES (3, 'Paryż', 'Francja');

-- Samoloty
INSERT INTO airplane (id, model, registration_number, capacity)
VALUES (1, 'Boeing 737', 'SP-ABC', 180);
INSERT INTO airplane (id, model, registration_number, capacity)
VALUES (2, 'Airbus A320', 'SP-DEF', 160);

-- Loty
INSERT INTO flight (id, departure_city_id, arrival_city_id, departure_date_time, available_seats, airplane_id)
VALUES (1, 1, 2, '2025-06-01',  100, 1);
INSERT INTO flight (id, departure_city_id, arrival_city_id, departure_date_time, available_seats, airplane_id)
VALUES (2, 2, 3, '2025-06-02',  80, 2);

-- Użytkownicy
INSERT INTO users (id, first_Name, last_Name, email, password, authority)
VALUES (1, 'Jan','Kowalski', 'jan@example.com', 'password1', 'ADMIN');
INSERT INTO users (id, first_Name, last_Name, email, password, authority)
VALUES (2, 'Anna','Nowak', 'anna@example.com', 'password2', 'USER');

-- Bilety
INSERT INTO ticket (id, flight_id, user_id,  purchase_date, confirmed)
VALUES (1, 1, 1,    '2025-05-27T10:00:00', TRUE);
INSERT INTO ticket (id, flight_id, user_id,  purchase_date, confirmed)
VALUES (2, 2, 2,   '2025-05-27T11:00:00', FALSE);

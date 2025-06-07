-- Miasta
INSERT INTO city ( name, country) VALUES ( 'Warszawa', 'Polska');
INSERT INTO city ( name, country) VALUES ( 'Berlin', 'Niemcy');
INSERT INTO city ( name, country) VALUES ( 'Paryż', 'Francja');

-- Samoloty
INSERT INTO airplane ( model, registration_number, capacity)
VALUES ( 'Boeing 737', 'SP-ABC', 180);
INSERT INTO airplane ( model, registration_number, capacity)
VALUES ( 'Airbus A320', 'SP-DEF', 160);

-- Loty
INSERT INTO flight ( departure_city_id, arrival_city_id, departure_date_time, available_seats, airplane_id)
VALUES ( 1, 2, '2025-06-01',  100, 1);
INSERT INTO flight ( departure_city_id, arrival_city_id, departure_date_time, available_seats, airplane_id)
VALUES ( 2, 3, '2025-06-02',  80, 2);

-- Użytkownicy
INSERT INTO users ( first_Name, last_Name, email, password, authority)
VALUES ( 'Jan','Kowalski', 'jan@example.com', 'password1', 'ADMIN');
INSERT INTO users ( first_Name, last_Name, email, password, authority)
VALUES ( 'Anna','Nowak', 'anna@example.com', 'password2', 'USER');

-- Bilety
INSERT INTO ticket ( flight_id, user_id,  purchase_date, confirmed)
VALUES ( 1, 1,    '2025-05-27T10:00:00', TRUE);
INSERT INTO ticket ( flight_id, user_id,  purchase_date, confirmed)
VALUES ( 2, 2,   '2025-05-27T11:00:00', FALSE);

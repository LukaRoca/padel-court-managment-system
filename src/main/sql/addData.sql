
-- Inserir usuários
insert into users(token, name, email, password) values
('token1', 'Alice Silva', 'alice@email.com', 'senha123'),
('token2', 'Bruno Costa', 'bruno@email.com', 'senha456'),
('token3', 'Carla Dias', 'carla@email.com', 'senha789');

-- Inserir clubes
insert into club(name, owner) values
('Clube Central', 1),
('Clube Norte', 2);

-- Inserir courts
insert into court(name, club) values
('Court 1', 1),
('Court 2', 1),
('Court 3', 2);

-- Inserir rentals
insert into rental(date, initDuration, endDuration, usr, court) values
('2024-06-01', 10, 12, 1, 1),
('2024-06-02', 14, 16, 2, 2),
('2024-06-03', 9, 11, 3, 3);
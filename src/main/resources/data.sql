INSERT INTO CLIENTE(cliente_id, nome, conta, saldo, plano_exclusive, dt_nasc) VALUES (1001, 'RAFAEL TAKASHIMA', '1234567890', 1000, true, '1982-06-01');
INSERT INTO CLIENTE(cliente_id, nome, conta, saldo, plano_exclusive, dt_nasc) VALUES (1002, 'JOAO SANTOS', '1020304050', 2000, false, '1990-01-01');
INSERT INTO CLIENTE(cliente_id, nome, conta, saldo, plano_exclusive, dt_nasc) VALUES (1003, 'MARIA JOAQUINA', '999999999', 5000, false, '1988-06-21');
INSERT INTO CLIENTE(cliente_id, nome, conta, saldo, plano_exclusive, dt_nasc) VALUES (1004, 'PETER PARKER', '1000000000', 100, false, '1999-02-10');
INSERT INTO CLIENTE(cliente_id, nome, conta, saldo, plano_exclusive, dt_nasc) VALUES (1005, 'IRON MAN', '2000000000', 50000, true, '1970-10-25');

INSERT INTO TRANSACAO(transacao_id, data, valor, cliente_id) VALUES(5001, '2023-05-01', 1000, 1001);
INSERT INTO TRANSACAO(transacao_id, data, valor, cliente_id) VALUES(5002, '2023-05-02', 2000, 1002);
INSERT INTO TRANSACAO(transacao_id, data, valor, cliente_id) VALUES(5003, '2023-05-03', 5000, 1003);
INSERT INTO TRANSACAO(transacao_id, data, valor, cliente_id) VALUES(5004, '2023-05-04', 100, 1004);
INSERT INTO TRANSACAO(transacao_id, data, valor, cliente_id) VALUES(5005, '2023-05-05', 90000, 1005);
INSERT INTO TRANSACAO(transacao_id, data, valor, cliente_id) VALUES(5006, '2023-05-06', -20000, 1005);
INSERT INTO TRANSACAO(transacao_id, data, valor, cliente_id) VALUES(5007, '2023-05-07', -20000, 1005);
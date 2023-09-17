INSERT INTO public.departments (id, code, name, created_date, update_date) VALUES (1, 'MK', 'Marketing', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO public.departments (id, code, name, created_date, update_date) VALUES (2, 'SL', 'Ventas', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO public.departments (id, code, name, created_date, update_date) VALUES (3, 'HR', 'Recursos Humanos', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO public.departments (id, code, name, created_date, update_date) VALUES (4, 'TI', 'Telecomunicaciones', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO public.departments (id, code, name, created_date, update_date) VALUES (5, 'DL', 'Suministros', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO public.employees (id, document_type, document, first_name, last_name, city, address, email, phone_number, created_date, update_date, id_department) VALUES (1, 2, '123456789', 'Juan', 'Mora', 'Bucaramanga', 'Calle 123 #45-67', 'juan.mora@example.com', '3001234567', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
INSERT INTO public.employees (id, document_type, document, first_name, last_name, city, address, email, phone_number, created_date, update_date, id_department) VALUES (2, 1, '987654321', 'Martha', 'Molina', 'Medellin', 'Carrera 76 #54-32', 'maria.molina@example.com', '3109876543', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4);
INSERT INTO public.employees (id, document_type, document, first_name, last_name, city, address, email, phone_number, created_date, update_date, id_department) VALUES (3, 2, '456789123', 'Carlos', 'Solano', 'Cali', 'Avenida 5N #23-34', 'carlos.solano@example.com', '3204567891', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3);

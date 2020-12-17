INSERT into state(name, state_tax)
VALUES  ('Alabama', 0.1),
        ('Alaska', 0.12),
        ('Arizona', 0.08),
        ('Arkansas', 0.07),
        ('California', 0.05),
        ('Colorado', 0.1),
        ('Connecticut', 0.12),
        ('Delaware', 0.16),
        ('Florida', 0.11),
        ('Georgia', 0.1),
        ('Hawaii', 0.09),
        ('Idaho', 0.1),
        ('Illinois', 0.1),
        ('Indiana', 0.12),
        ('Iowa', 0.08),
        ('Kansas', 0.05),
        ('Kentucky', 0.03),
        ('Louisiana', 0.13),
        ('Maine', 0.1),
        ('Maryland', 0.14),
        ('Massachusetts', 0.16),
        ('Michigan', 0.12),
        ('Minnesota', 0.04),
        ('Mississippi', 0.1),
        ('Missouri', 0.02),
        ('Montana', 0.05),
        ('Nebraska', 0.08),
        ('Nevada', 0.11),
        ('New Hampshire', 0.1),
        ('New Jersey', 0.06),
        ('New Mexico', 0.07),
        ('New York', 0.12),
        ('North Carolina', 0.1),
        ('North Dakota', 0.14),
        ('Ohio', 0.04),
        ('Oklahoma', 0.12),
        ('Oregon', 0.14),
        ('Pennsylvania', 0.11),
        ('Rhode Island', 0.12),
        ('South Carolina', 0.09),
        ('South Dakota', 0.04),
        ('Tennessee', 0.12),
        ('Texas', 0.11),
        ('Utah', 0.12),
        ('Vermont', 0.13),
        ('Virginia', 0.09),
        ('Washington', 0.11),
        ('West Virginia', 0.12),
        ('Wisconsin', 0.13),
        ('Wyoming', 0.14);
INSERT INTO users(birthday, email, first_name, last_name, password, user_status, address_id, customer_id, location_id, login)
VALUES ('2000-01-01', 'systemadmin@gmail.com', 'John', 'Johnson',
         '$2a$10$Ze2x/3/q7FwFqebMFmnqoe6KYM8g2Cw0PgZznNWExstFFiqCL0.He', 'ACTIVE', null, null, null, 'systemadmin@gmail.com');
INSERT INTO user_role(user_id, user_role)
VALUES (1, 'SYSTEM_ADMIN');
















-- Création des tables
-- Drop tables if they exist
DROP TABLE financial_transactions CASCADE CONSTRAINTS;
DROP TABLE employees CASCADE CONSTRAINTS;

-- Create employees table
CREATE TABLE employees (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR2(50) NOT NULL,
    last_name VARCHAR2(50) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    phone VARCHAR2(20),
    position VARCHAR2(50) NOT NULL,
    salary NUMBER(10,2) NOT NULL,
    hire_date DATE NOT NULL
);

-- Create financial_transactions table
CREATE TABLE financial_transactions (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    type VARCHAR2(10) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE')),
    amount NUMBER(10,2) NOT NULL,
    description VARCHAR2(255),
    transaction_date TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    category VARCHAR2(50) NOT NULL
);

-- Add some indexes for better performance
CREATE INDEX idx_employee_email ON employees(email);
CREATE INDEX idx_transaction_type ON financial_transactions(type);
CREATE INDEX idx_transaction_date ON financial_transactions(transaction_date);

-- Insert some sample data for employees
INSERT INTO employees (first_name, last_name, email, phone, position, salary, hire_date)
VALUES ('John', 'Doe', 'john.doe@hotel.com', '123-456-7890', 'Manager', 50000.00, DATE '2023-01-01');

INSERT INTO employees (first_name, last_name, email, phone, position, salary, hire_date)
VALUES ('Jane', 'Smith', 'jane.smith@hotel.com', '123-456-7891', 'Receptionist', 35000.00, DATE '2023-02-01');

-- Inserting example employees
INSERT INTO employees (first_name, last_name, email, phone, position, salary, hire_date) 
VALUES 
('Charlie', 'Davis', 'charlie.davis@hotel.com', '123-456-7894', 'Waiter', 20000.00, DATE '2023-05-01'),
('Diana', 'Evans', 'diana.evans@hotel.com', '123-456-7895', 'Bartender', 22000.00, DATE '2023-06-01'),
('Ethan', 'Foster', 'ethan.foster@hotel.com', '123-456-7896', 'Security', 28000.00, DATE '2023-07-01'),
('Fiona', 'Green', 'fiona.green@hotel.com', '123-456-7897', 'Event Coordinator', 35000.00, DATE '2023-08-01'),
('George', 'Hall', 'george.hall@hotel.com', '123-456-7898', 'Maintenance', 32000.00, DATE '2023-09-01'),
('Hannah', 'Ingram', 'hannah.ingram@hotel.com', '123-456-7899', 'Marketing', 45000.00, DATE '2023-10-01'),
('Ian', 'Johnson', 'ian.johnson@hotel.com', '123-456-7800', 'Accountant', 50000.00, DATE '2023-11-01'),
('Julia', 'King', 'julia.king@hotel.com', '123-456-7801', 'Sales', 42000.00, DATE '2023-12-01'),
('Kevin', 'Lee', 'kevin.lee@hotel.com', '123-456-7802', 'IT Support', 48000.00, DATE '2024-01-01'),
('Laura', 'Martin', 'laura.martin@hotel.com', '123-456-7803', 'HR', 55000.00, DATE '2024-02-01'),
('Mike', 'Nelson', 'mike.nelson@hotel.com', '123-456-7804', 'Cleaner', 24000.00, DATE '2024-03-01');
('Alice', 'Johnson', 'alice.johnson@hotel.com', '123-456-7892', 'Housekeeper', 25000.00, DATE '2023-03-01'),
('Bob', 'Brown', 'bob.brown@hotel.com', '123-456-7893', 'Chef', 40000.00, DATE '2023-04-01'),
-- Insert some sample financial transactions
INSERT INTO financial_transactions (type, amount, description, category)
VALUES ('INCOME', 1500.00, 'Room 101 booking', 'Accommodation');

INSERT INTO financial_transactions (type, amount, description, category)
VALUES ('EXPENSE', 500.00, 'Cleaning supplies', 'Maintenance');

INSERT INTO financial_transactions (type, amount, description, category)
VALUES ('INCOME', 2000.00, 'Conference room rental', 'Events');

INSERT INTO financial_transactions (type, amount, description, category)
VALUES ('EXPENSE', 1000.00, 'Utilities payment', 'Utilities');

-- Inserting example financial transactions
INSERT INTO financial_transactions (type, amount, description, category) 
VALUES
( 'INCOME', 1500.00, 'Room 102 booking', 'Accommodation'),
('EXPENSE', 800.00, 'Food supplies', 'Food'),
('INCOME', 600.00, 'Room 103 booking', 'Accommodation'),
('EXPENSE', 1200.00, 'Equipment maintenance', 'Maintenance'),
('INCOME', 400.00, 'Room 104 booking', 'Accommodation'),
('EXPENSE', 500.00, 'Marketing expenses', 'Marketing'),
('INCOME', 700.00, 'Room 105 booking', 'Accommodation'),
('EXPENSE', 1100.00, 'Staff training', 'Training'),
('INCOME', 900.00, 'Room 106 booking', 'Accommodation'),
('EXPENSE', 1300.00, 'Utilities payment', 'Utilities'),
('INCOME', 1400.00, 'Room 107 booking', 'Accommodation'),
('EXPENSE', 950.00, 'Office supplies', 'Office'),
('INCOME', 750.00, 'Room 108 booking', 'Accommodation'),
('EXPENSE', 850.00, 'Travel expenses', 'Travel'),
('INCOME', 300.00, 'Room 109 booking', 'Accommodation'),
('EXPENSE', 950.00, 'Office supplies', 'Office'),
('INCOME', 750.00, 'Room 108 booking', 'Accommodation'),
('EXPENSE', 850.00, 'Travel expenses', 'Travel'),
('INCOME', 300.00, 'Room 109 booking', 'Accommodation');

-- Création d'un utilisateur pour l'application (à exécuter en tant que SYSTEM)
-- CREATE USER hotel_admin IDENTIFIED BY your_password;
-- GRANT CONNECT, RESOURCE TO hotel_admin;
-- GRANT CREATE SESSION TO hotel_admin;
-- GRANT ALL PRIVILEGES ON employees TO hotel_admin;
-- GRANT ALL PRIVILEGES ON financial_transactions TO hotel_admin;

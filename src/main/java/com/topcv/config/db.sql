USE master;
-- Xóa cơ sở dữ liệu nếu tồn tại
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'TopCV')
    DROP DATABASE TopCV;
GO
-- Tạo cơ sở dữ liệu
IF NOT EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'TopCV')
    CREATE DATABASE TopCV;
GO
-- Sử dụng cơ sở dữ liệu mới
USE TopCV;
GO
-- Tạo bảng role
CREATE TABLE role (
    id INT IDENTITY(1,1) PRIMARY KEY,
    role_name VARCHAR(100),
    created_at DATETIME,
    updated_at DATETIME
);
GO
-- Tạo bảng account
CREATE TABLE account (
    id INT IDENTITY(1,1) PRIMARY KEY,
    address VARCHAR(255),
    description TEXT,
    email VARCHAR(255),
    full_name VARCHAR(100),
    image VARCHAR(255),
    password VARCHAR(128),
    phone_number VARCHAR(20),
    status SMALLINT,
    role_id INT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (role_id) REFERENCES role(id)
);
GO
-- Tạo bảng company
CREATE TABLE company (
    id INT IDENTITY(1,1) PRIMARY KEY,
    address VARCHAR(255),
    description TEXT,
    email VARCHAR(255),
    logo VARCHAR(255),
    name_company VARCHAR(255),
    phone_number VARCHAR(20),
    status SMALLINT,
    account_id INT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (account_id) REFERENCES account(id)
);
GO
-- Tạo bảng category
CREATE TABLE category (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255),
    created_at DATETIME,
    updated_at DATETIME
);
GO
-- Tạo bảng recruitment
CREATE TABLE recruitment (
    id INT IDENTITY(1,1) PRIMARY KEY,
    address VARCHAR(255),
    created_at DATETIME,
    description TEXT,
    experience VARCHAR(255),
    quantity INT,
    rank VARCHAR(255),
    salary VARCHAR(255),
    status SMALLINT,
    title VARCHAR(255),
    type VARCHAR(255),
    view_count INT,
    category_id INT,
    company_id INT,
    deadline DATETIME,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (company_id) REFERENCES company(id)
);
GO
-- Tạo bảng cv
CREATE TABLE cv (
    id INT IDENTITY(1,1) PRIMARY KEY,
    file_path VARCHAR(255),
    account_id INT,
    status SMALLINT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (account_id) REFERENCES account(id)
);
GO
-- Tạo bảng save_job
CREATE TABLE save_job (
    id INT IDENTITY(1,1) PRIMARY KEY,
    recruitment_id INT,
    account_id INT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (recruitment_id) REFERENCES recruitment(id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);
GO
-- Tạo bảng apply_job
CREATE TABLE apply_job (
    id INT IDENTITY(1,1) PRIMARY KEY,
    recruitment_id INT,
    account_id INT,
    status SMALLINT,
    text VARCHAR(255),
    cv_id INT,
    note TEXT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (recruitment_id) REFERENCES recruitment(id),
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (cv_id) REFERENCES cv(id)
);
GO
-- Tạo bảng follow_company
CREATE TABLE follow_company (
    id INT IDENTITY(1,1) PRIMARY KEY,
    company_id INT,
    account_id INT,
    created_at DATETIME,
    updated_at DATETIME,
    FOREIGN KEY (company_id) REFERENCES company(id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

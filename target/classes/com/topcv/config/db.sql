USE
master;
-- Xóa cơ sở dữ liệu nếu tồn tại
IF
EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'TopCV')
    DROP
DATABASE TopCV;
GO
-- Tạo cơ sở dữ liệu
IF NOT EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'TopCV')
    CREATE
DATABASE TopCV;
GO
-- Sử dụng cơ sở dữ liệu mới
USE TopCV;
GO
-- Tạo bảng role
CREATE TABLE role
(
    id        INT IDENTITY(1,1) PRIMARY KEY,
    name      VARCHAR(100),
    createdAt DATETIME,
    updatedAt DATETIME
);
GO
-- Tạo bảng account
CREATE TABLE account
(
    id          INT IDENTITY(1,1) PRIMARY KEY,
    address     VARCHAR(255),
    description TEXT,
    email       VARCHAR(255),
    fullName    VARCHAR(100),
    image       VARCHAR(255),
    password    VARCHAR(128),
    phoneNumber VARCHAR(20),
    status      SMALLINT,
    roleId      INT,
    createdAt   DATETIME,
    updatedAt   DATETIME,
    FOREIGN KEY (roleId) REFERENCES role (id)
);
GO
-- Tạo bảng company
CREATE TABLE company
(
    id          INT IDENTITY(1,1) PRIMARY KEY,
    address     VARCHAR(255),
    description TEXT,
    email       VARCHAR(255),
    logo        VARCHAR(255),
    name        VARCHAR(255),
    phoneNumber VARCHAR(20),
    status      SMALLINT,
    accountId   INT,
    createdAt   DATETIME,
    updatedAt   DATETIME,
    FOREIGN KEY (accountId) REFERENCES account (id)
);
GO
-- Tạo bảng category
CREATE TABLE category
(
    id        INT IDENTITY(1,1) PRIMARY KEY,
    name      VARCHAR(255),
    createdAt DATETIME,
    updatedAt DATETIME
);
GO
-- Tạo bảng recruitment
CREATE TABLE recruitment
(
    id          INT IDENTITY(1,1) PRIMARY KEY,
    address     VARCHAR(255),
    createdAt   DATETIME,
    description TEXT,
    experience  VARCHAR(255),
    quantity    INT,
    rank        VARCHAR(255),
    salary      VARCHAR(255),
    status      SMALLINT,
    title       VARCHAR(255),
    type        VARCHAR(255),
    viewCount   INT,
    categoryId  INT,
    companyId   INT,
    deadline    DATETIME,
    FOREIGN KEY (categoryId) REFERENCES category (id),
    FOREIGN KEY (companyId) REFERENCES company (id)
);
GO
-- Tạo bảng cv
CREATE TABLE cv
(
    id        INT IDENTITY(1,1) PRIMARY KEY,
    filePath  VARCHAR(255),
    accountId INT,
    status    SMALLINT,
    createdAt DATETIME,
    updatedAt DATETIME,
    FOREIGN KEY (accountId) REFERENCES account (id)
);
GO
-- Tạo bảng save_job
CREATE TABLE save_job
(
    id            INT IDENTITY(1,1) PRIMARY KEY,
    recruitmentId INT,
    accountId     INT,
    createdAt     DATETIME,
    updatedAt     DATETIME,
    FOREIGN KEY (recruitmentId) REFERENCES recruitment (id),
    FOREIGN KEY (accountId) REFERENCES account (id)
);
GO
-- Tạo bảng apply_job
CREATE TABLE apply_job
(
    id            INT IDENTITY(1,1) PRIMARY KEY,
    recruitmentId INT,
    accountId     INT,
    status        SMALLINT,
    text          VARCHAR(255),
    cvId          INT,
    note          TEXT,
    createdAt     DATETIME,
    updatedAt     DATETIME,
    FOREIGN KEY (recruitmentId) REFERENCES recruitment (id),
    FOREIGN KEY (accountId) REFERENCES account (id),
    FOREIGN KEY (cvId) REFERENCES cv (id)
);
GO
-- Tạo bảng follow_company
CREATE TABLE follow_company
(
    id        INT IDENTITY(1,1) PRIMARY KEY,
    companyId INT,
    accountId INT,
    createdAt DATETIME,
    updatedAt DATETIME,
    FOREIGN KEY (companyId) REFERENCES company (id),
    FOREIGN KEY (accountId) REFERENCES account (id)
);

-- Tạo dữ liệu mẫu
GO
SET IDENTITY_INSERT role OFF;
GO
INSERT INTO role (id, name, createdAt, updatedAt)
VALUES (1,'CUSTOMER', GETDATE(), GETDATE()),
(2,'COMPANY', GETDATE(), GETDATE());
GO
SET IDENTITY_INSERT role ON;
GO
SET IDENTITY_INSERT account OFF;
INSERT INTO account (email, fullName, password, status, roleId)
VALUES ('customer1433@gmail.com', 'Nguyen Van A', '123123', 1, 1);
SET
IDENTITY_INSERT account ON;
select *
FROM role r



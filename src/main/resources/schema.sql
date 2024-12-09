PRAGMA foreign_keys = ON;
CREATE TABLE IF NOT EXISTS Books (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL,
    availableCopies INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS Users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id INTEGER NOT NULL,
    email VARCHAR(255) NOT NULL,
    FOREIGN KEY (role_id) REFERENCES Roles(role_id)
);
CREATE TABLE IF NOT EXISTS Roles (
    role_id INTEGER,
    role_name VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS Transactions (
    transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    book_id INTEGER NOT NULL,
    action VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (book_id) REFERENCES Books(id)
);
# Library Management System

## Project Overview

This application is a comprehensive library management system designed to streamline book inventory and borrowing processes. It features two main user roles:

1. **User**: Can search for and borrow books.
2. **Administrator**: Can manage the book inventory, including adding, updating, and removing books.

The primary goal of this system is to offer a user-friendly interface for efficient library operations.

![img.png](img.png)

---

## Technology Stack

- **Frontend**: JavaFX for an interactive and visually appealing user interface.
- **Backend**: Spring Boot to manage business logic and RESTful APIs.
- **Database**: SQLite for lightweight and efficient data storage.
- **APIs**: RESTful APIs ensure smooth communication between the client and server.

![img_1.png](img_1.png)

---

## Classes and Functions

### Key Classes:
1. **`Book`**: Represents a single book in the system, containing properties such as `title`, `author`, `isbn`, and `availableCopies`.
    - `Book.java`: [View Source Code](src/main/java/com/librer/Librer/book/BookRepository.java)

2. **`BookRepository`**: Handles all database operations related to books.
    - Key Methods:
        - `findAll()`: Retrieves all books from the database.
        - `findById(int id)`: Fetches a book by its ID.
        - `create(Book book)`: Adds a new book to the database.
        - `update(Book book, int id)`: Updates details of an existing book.
        - `delete(int id)`: Deletes a book by ID.
    - [View Source Code](src/main/java/com/librer/Librer/book/BookRepository.java)

3. **`Books`**: A wrapper class for lists of books.
    - [View Source Code](src/main/java/com/librer/Librer/book/Books.java)

![img_4.png](img_4.png)

---

## RESTful API

### Endpoints:
- **CREATE** `/books`: Adds a new book.
- **READ** `/books`: Fetches all books.
- **UPDATE** `/books/{id}`: Updates an existing book.
- **DELETE** `/books/{id}`: Deletes a book.

These endpoints allow CRUD operations on the book inventory.

![img_3.png](img_3.png)

---

## Database Design

The database consists of four main tables:

1. **Users**: Stores user information and roles.
2. **Books**: Stores details about books, such as title, author, and availability.
3. **Roles**: Defines user permissions.
4. **Transactions**: Records all book transactions, including borrowing and returning.

![img_5.png](img_5.png)

---

## User Interface

### Key Screens:
1. **User Dashboard**: Allows users to browse and borrow books.
2. **Admin Panel**: Provides administrators with tools to manage the inventory.

Login and register screen

![obrázok](https://github.com/user-attachments/assets/fa2c0d71-bb2a-4435-b282-c1dd84711654)

If an administrator logs in, the main administrator dashboard is displayed.

![obrázok](https://github.com/user-attachments/assets/839854cb-b42c-4635-bda6-6d2bbc50d42a)

![obrázok](https://github.com/user-attachments/assets/b163a165-e8ba-4631-92f1-95c6393c044e)

![obrázok](https://github.com/user-attachments/assets/d5cebc0c-7af1-4344-b5a4-490777322d1f)

The user's transactions can be displayed by double-clicking on the user's name.

![obrázok](https://github.com/user-attachments/assets/02adeee1-b55b-4437-832a-2158ab024761)

If a user logs in, the main user dashboard is displayed.

![obrázok](https://github.com/user-attachments/assets/5a264b3c-5f99-4117-b99b-2c593a974e36)

A user can also borrow two or more copies of the same book.

![obrázok](https://github.com/user-attachments/assets/e6c7a5c3-eee5-4acc-aacb-60477ed35725)

![obrázok](https://github.com/user-attachments/assets/c84292db-7e55-45c1-9c9a-9c332b73d0a0)

---

## Challenges and Approach

### Challenges:
1. **Secure Communication**: Preventing unauthorized access and data leaks.
2. **SQL Injection**: Ensuring all database operations are secure.

### Solutions:
- Implemented parameterized queries to avoid SQL injection.
- Used Spring Security for robust access control.

---


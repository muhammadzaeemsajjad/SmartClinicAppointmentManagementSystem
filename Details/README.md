# Smart Clinic Management System (SCMS)

A robust **Java-based desktop application** designed to streamline clinic operations.  
The Smart Clinic Management System replaces manual paperwork with an automated solution using **Java Swing GUI**, **Object-Oriented Programming**, and **file-based persistence**.

---

##  Project Overview

The **Smart Clinic Management System (SCMS)** manages interactions between:

- **Admin**
- **Doctor**
- **Patient**
- **Receptionist**

The system provides secure login, efficient appointment scheduling, and persistent data storage using Java Serialization.

---

##  Key Features

###  User Authentication
- Secure login system for all roles
- Role-based dashboards

---

###  Admin Module
- Central **Admin Dashboard**
- Add, update, or delete **Doctors**
- Create or remove **Receptionist accounts**
- View statistics:
  - Total patients
  - Total appointments

---

###  Doctor Module
- View upcoming appointments in tabular format
- Set **working days** (e.g., Mon, Wed, Fri)
- Prevent appointment conflicts
- View patient details linked to appointments

---

###  Patient Module
- Book appointments with doctors
- Select **valid future date & time**
- Automatic validation:
  - No past bookings
  - No double booking
- View appointment history and status

---

###  Receptionist Module
- Register new patients
- Update patient records
- Book or reschedule appointments on behalf of patients

---

##  Tech Stack & Concepts

| Category | Details |
|-------|--------|
| Language | Java (JDK 8+) |
| GUI Framework | Java Swing (JFrame, JPanel, JTable, JOptionPane) |
| Storage | File Handling using Serialization (`.dat` files) |
| Architecture | Object-Oriented Programming (OOP) |

---

##  Object-Oriented Concepts Used

- **Inheritance**  
  `Doctor`, `Patient`, and `Receptionist` extend the common `User` class.

- **Polymorphism**  
  Method overriding (e.g., `toString()` and role-specific behaviors).

- **Encapsulation**  
  Private fields with public getters and setters.

- **Composition**  
  - `Doctor` has-a list of `Appointment`
  - Manager classes have-a `FileManager`

- **Abstraction**  
  - Abstract base classes (where applicable)
  - Interface implementation (`Serializable`)

---

##  Advanced Implementation Details

###  Generics
- `ArrayList<?>` used in `FileManager`
- One generic method handles serialization for all entity lists

---

###  Exception Handling
- **Custom Exception**:
  - `UserAlreadyExistsException` prevents duplicate user IDs
- **Built-in Exceptions**:
  - `IllegalArgumentException` for invalid input or past dates

---

###  Try-With-Resources
- Ensures file streams close automatically
- Prevents memory leaks during serialization

---

###  Java Collections
- `ArrayList` → Stores users and appointments
- `HashSet` → Stores doctor working days (no duplicates, fast lookup)

---

##  Project Structure

```bash
SmartClinicManagementSystem/
├── src/
│   ├── main/
│   │   └── Main.java                # Entry Point (Login & Data Linking)
│   ├── models/
│   │   ├── User.java                # Parent Class
│   │   ├── Doctor.java
│   │   ├── Patient.java
│   │   ├── Appointment.java
│   │   └── Receptionist.java
│   ├── managerClasses/
│   │   ├── FileManager.java         # Generic File Handler
│   │   ├── DoctorManager.java
│   │   ├── PatientManager.java
│   │   └── AppointmentManager.java
│   ├── gui/
│   │   ├── LoginFrame.java
│   │   ├── AdminDashboard.java
│   │   ├── DoctorDashboard.java
│   │   └── PatientDashboard.java
│   └── CustomExceptions/
│       └── UserAlreadyExistsException.java
└── README.md

##  Author

Developed by **M. Zaeem Sajjad And Zaryab Zahid And Hafiz Abdul Rehman **  
As a **Semester Project** for Object-Oriented Programming (Java)

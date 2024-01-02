# Empleados Manager

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Description
The "empleados" project is a Java application for managing employees. It provides CRUD (Create, Read, Update, Delete) operations for the employee entity and includes methods for sorting employees by name and position.

## Features
- Create new employees with the ability to specify their name, position, and other relevant details.
- Retrieve employee information by their unique identifier.
- Update employee information, such as their name, position, or other attributes.
- Delete employees from the system.
- Sort employees by name or position.

## Usage
The application interacted through console inputs. It will run until the user introduces the corresponding exit commands.
   
   ```java
   // Create new employees in DDBB:
   Employee employee = new Employee(String name, String surname, String position, Double salary, LocalDate join_date);
    EmployeeJpaController.create(employee)

   // Search an employee by ID
   EmployeeJpaController.create(employee);
   int employeeId = 1; // Example employee ID
   Employee emp = EmployeeJpaController.findEmployee(employeeId);

   // Update entries
   int employeeId = 1;
   Employee employee = EmployeeJpaController.findEmployee(employeeId);
   if (employee != null) {
   EmployeeJpaController.update(employee);

   // Delete
   int employeeId = 1;
 EmployeeJpaController.delete(employeeId);
   
   ```
   
Most of the CRUD operations throw exceptions that are handled by try/catch, avoiding runtime crashes caused by invalid inputs, non existent or duplicated entities...
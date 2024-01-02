# Empleados Manager

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Description
The "empleados" project is a Java application for managing employees. It provides CRUD (Create, Read, Update, Delete) operations for the employee entities of an SQL databse, and includes methods for sorting employees by name and position. The interaction witb the DDBB relies in a JPA controller associated to its corresponding persistence unit. 

## Features
- Create new employees with the ability to specify their name, position, and other relevant details.
- Retrieve employee information by their unique identifier.
- Update employee information, such as their name, position, or other attributes.
- Delete employees from the system.
- Sort employees by name or position.

![scrnshoot](https://media.discordapp.net/attachments/1054858184814248046/1191828612849086464/image.png?ex=65a6dbe4&is=659466e4&hm=584111a5592f964c4ab578cb3b0c41d185dd0f5d3606c413801462d9afae14e7&=&format=webp&quality=lossless&width=1114&height=889)

## Usage
The application interacted through console inputs. It will run until the user introduces the corresponding exit commands.
   
   ```java
   // Create new employees in DDBB:
   Employee employee = new Employee(String name, String surname, String position, double salary, LocalDate join_date);
   EmployeeJpaController.create(employee)

   // Search an employee by ID
   EmployeeJpaController.create(employee);
   int employeeId = 1; // Example employee ID
   Employee emp = EmployeeJpaController.findEmployee(employeeId);

   // Update entries
   int employeeId = 1;
   Employee employee = EmployeeJpaController.findEmployee(employeeId);
   EmployeeJpaController.update(employee);

   // Delete
   int employeeId = 1;
   EmployeeJpaController.delete(employeeId);
   
   ```
   
Most of the CRUD operations throw exceptions that are handled by try/catch, avoiding runtime crashes caused by invalid inputs, non existent or duplicated entities...


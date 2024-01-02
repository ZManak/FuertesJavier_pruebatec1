# empleados

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
1. Create new employees:
   ```java
   Employee employee = new Employee();
   employee.setName("John Doe");
   employee.setPosition("Manager");

   // Set other attributes as needed
   EmployeeJpaController.create(employee);
   int employeeId = 1; // Example employee ID
   Employee employee = EmployeeJpaController.findEmployee(employeeId);
    if (employee != null) {

   // Access employee attributes
   String name = employee.getName();
   String position = employee.getPosition();

   // Update entries
   int employeeId = 1;
   Employee employee = EmployeeJpaController.findEmployee(employeeId);
   if (employee != null) {
   EmployeeJpaController.update(employee);

   // Delete
   int employeeId = 1;
   try {
   EmployeeJpaController.delete(employeeId);
   } catch (NonexistentEntityException ex) {

   // Handle exception if employee does not exist
   Logger.getLogger(PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
   }
   ```
   

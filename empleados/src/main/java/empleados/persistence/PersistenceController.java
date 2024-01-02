/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package empleados.persistence;

import controlsys.EmployeeJpaController;
import controlsys.exceptions.InvalidDataException;
import controlsys.exceptions.NonexistentEntityException;
import controlsys.exceptions.PreexistingEntityException;
import empleados.models.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is in charge of the CRUD operations of the employee entity
 * It has the methods to create, delete, update and read the employees
 * It also has the methods to sort the employees by name and position
 * 
 * @author Manak
 */
public class PersistenceController {

    EmployeeJpaController empJPA = new EmployeeJpaController();

    public void createEmployee(Employee emp)
            throws InvalidDataException, PreexistingEntityException, NonexistentEntityException {
        try {
            empJPA.create(emp);
        } catch (InvalidDataException ex) {
            System.out.println(ex.getMessage());
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(empleados.persistence.PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteEmployee(int id) {
        try {
            empJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(empleados.persistence.PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Employee> findEmployee() throws Exception {
        return empJPA.findEmployeeEntities();
    }

    public Employee findSurname(String surname) throws Exception {

        List<Employee> empleados = empJPA.findEmployeeEntities();
        for (Employee emp : empleados) {
            if (emp.getSurname().equals(surname)) {
                return emp;
            }
        }
        return null;
    }

    public Employee findId(Integer id) throws Exception {
        if (id < 0 || id == null || !(id instanceof Integer)) {
            throw new InvalidDataException();
        }
        try {
            List<Employee> employees = empJPA.findEmployeeEntities();
            for (Employee emp : employees) {
                if (emp.getId() == id) {
                    return emp;
                }
            }
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(empleados.persistence.PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidDataException ex) {
            Logger.getLogger(empleados.persistence.PersistenceController.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    public List<Employee> findPosition(String position) throws Exception {

        List<Employee> employees = empJPA.findEmployeeEntities();
        List<Employee> sortedByPosition = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getPosition().equals(position)) {
                sortedByPosition.add(emp);
            }
        }
        return sortedByPosition;
    }

    public void updateEmployee(Employee emp) throws NonexistentEntityException, InvalidDataException {
        try {
            empJPA.edit(emp);
        } catch (InvalidDataException ex) {
            Logger.getLogger(empleados.persistence.PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(empleados.persistence.PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(empleados.persistence.PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

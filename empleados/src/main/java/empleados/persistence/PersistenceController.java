/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package empleados.persistence;

import controlsys.empleados.EmpleadoJpaController;
import controlsys.empleados.exceptions.NonexistentEntityException;
import empleados.models.Empleado;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manak
 */
public class PersistenceController {
    
    EmpleadoJpaController empJPA = new EmpleadoJpaController();
    
    public void crearEmpleado(Empleado emp) {
      empJPA.create(emp);
  }
  
  public void borrarPersona(int id) {
      try {
          empJPA.destroy(id);
      } catch (NonexistentEntityException ex) {
          Logger.getLogger(empleados.persistence.PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  
  public List<Empleado> sacarEmpleados () {
      return empJPA.findEmpleadoEntities();
  }

    public Empleado sacarNombre (String name) {
     
        List<Empleado> empleados = empJPA.findEmpleadoEntities();
        for (Empleado emp : empleados) {
            if (emp.getName().equals(name)) {
                return emp;
            }
        }
        return null;
    }

    public Empleado sacarId (int id) {

        List<Empleado> empleados = empJPA.findEmpleadoEntities();
        for (Empleado emp : empleados) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        return null;
    }

    public List<Empleado> sacarPosition (String position) {

        List<Empleado> empleados = empJPA.findEmpleadoEntities();
        List<Empleado> sortedByPosition = new ArrayList<>();
        for (Empleado emp : empleados) {
            if (emp.getPosition().equals(position)) {
                sortedByPosition.add(emp);
            }
        }
        return sortedByPosition;
    }
  
  public void modificarEmpleado (Empleado emp) {
  
      try {
          empJPA.edit(emp);
      } catch (Exception ex) {
          Logger.getLogger(empleados.persistence.PersistenceController.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
}

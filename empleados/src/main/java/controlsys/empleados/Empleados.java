/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package controlsys.empleados;

import empleados.models.Empleado;
import empleados.persistence.PersistenceController;
import java.util.List;


/**
 *
 * @author Manak
 */
public class Empleados {

    public static void main(String[] args) {
        PersistenceController controller = new PersistenceController();
        //Empleado emp1 = new Empleado("Javier", "Fuertes", "Intern", 18000.0, LocalDate.of(2023,1,1));
        //controller.crearEmpleado(emp1);     
        List<Empleado> empleados = controller.sacarEmpleados();
        
        for (Empleado empleado : empleados){
            System.out.println(empleado.getName());
        }
        
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package controlsys.empleados;

import empleados.models.Empleado;
import empleados.persistence.PersistenceController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static empleados.constants.Colors.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manak
 */
public class Empleados {


    public static void main(String[] args) {
        
        PersistenceController controller = new PersistenceController();
        Scanner sc = new Scanner(System.in); 

        printBanner();
        do
        {
            printMenu();
            System.out.println("Choose an option: ");
            int option = Integer.parseInt(sc.nextLine());

            try {
                switch (option) {
                case 1 -> {
                    createEmployee(controller, sc);
                }
                case 2 -> {
                    searchEmployee(controller, sc);
                }
                case 3 -> {
                    modifyEmployee(controller, sc);
                }
                case 4 -> {
                    System.out.println("Id: ");
                    int id1 = Integer.parseInt(sc.nextLine());
                    controller.borrarPersona(id1);
                    System.out.println("Employee id." + id1 + " deleted");
                }
                case 5 -> {
                    List<Empleado> employees = controller.sacarEmpleados();
                    printEmpleados(employees);
                }
                case 6 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            }
            } catch (Exception ex) {
                Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (!"exit".equals(sc.nextLine()));
        System.out.println("Exiting...");
        System.exit(0);
    }

    public static void printMenu() {
        System.out.println(PURPLE_UNDERLINED + "┌──────────────────────────────┐┐" + RESET);
        System.out.println(CYAN_BOLD + "│         EMPLOYEE MANAGER     |│");
        System.out.println(PURPLE_UNDERLINED + "├──────────────────────────────|┤" + RESET);
        System.out.println(GREEN_BOLD + "│ " + BLUE_BOLD + "1. Add                      " + GREEN_BOLD + " ││" + RESET);
        System.out.println(GREEN_BOLD + "│ " + BLUE_BOLD + "2. Search                   " + GREEN_BOLD + " ││" + RESET);
        System.out.println(GREEN_BOLD + "│ " + BLUE_BOLD + "3. Modify                   " + GREEN_BOLD + " ││" + RESET);
        System.out.println(GREEN_BOLD + "│ " + BLUE_BOLD + "4. Delete                   " + GREEN_BOLD + " ││" + RESET);
        System.out.println(GREEN_BOLD + "│ " + BLUE_BOLD + "5. Print All                " + GREEN_BOLD + " ││" + RESET);
        System.out.println(GREEN_BOLD + "│ " + BLUE_BOLD + "6. EXIT                     " + GREEN_BOLD + " ││" + RESET);
        System.out.println(PURPLE_UNDERLINED + "└──────────────────────────────┘┘" + RESET);
    }

    public static void createEmployee(PersistenceController controller, Scanner sc) {
        System.out.println("Name: ");
        String name = sc.nextLine();
        System.out.println("Surname: ");
        String surname = sc.nextLine();
        System.out.println("Position: ");
        String position = sc.nextLine();
        System.out.println("Salary: ");
        double salary = Double.parseDouble(sc.nextLine());
        System.out.println("Join date (dd-MM-yyyy): ");
        String joinDate = sc.nextLine();
        LocalDate date = LocalDate.parse(joinDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Empleado emp = new Empleado(name, surname, position, salary, date);
        controller.crearEmpleado(emp);
        System.out.println("Employee created");
    }

    public static void searchEmployee(PersistenceController controller, Scanner sc) {
        System.out.println("Search by: ");
        System.out.println("1. Name");
        System.out.println("2. Position");
        int option = Integer.parseInt(sc.nextLine());
        switch (option) {
            case 1 -> {
                System.out.println("Name: ");
                String name = sc.nextLine();
                Empleado employee = controller.sacarNombre(name);
                printEmpleado(employee);
            }
            case 2 -> {
                System.out.println("Position: ");
                String position = sc.nextLine();
                List<Empleado> employeesByPosition = controller.sacarPosition(position);
                printEmpleados(employeesByPosition);
            }
        }
    }

    public static void modifyEmployee(PersistenceController controller, Scanner sc) {
        System.out.println("Id: ");
        int id = Integer.parseInt(sc.nextLine());
        Empleado employee = controller.sacarId(id);
        System.out.println("Updating: " + employee.getName() + " " + employee.getSurname());
        System.out.println("New name: ");
        String name = sc.nextLine();
        System.out.println("New surname: ");
        String surname = sc.nextLine();
        System.out.println("New position: ");
        String position = sc.nextLine();
        System.out.println("New salary: ");
        double salary = Double.parseDouble(sc.nextLine());
        System.out.println("New join date (dd-MM-yyyy): ");
        String date = sc.nextLine();
        LocalDate joinDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        employee.setName(name);
        employee.setSurname(surname);
        employee.setPosition(position);
        employee.setSalary(salary);
        employee.setJoin_Date(joinDate);
        controller.modificarEmpleado(employee);
        System.out.println("Employee updated");
    }

        public static void printEmpleado(Empleado emp) {
        System.out.println("__________________________");
        System.out.println("Id: " + emp.getId());
        System.out.println("Name: " + emp.getName());
        System.out.println("Surname: " + emp.getSurname());
        System.out.println("Position: " + emp.getPosition());
        System.out.println("Salary: " + emp.getSalary());
        System.out.println("Join date: " + emp.getJoin_Date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
    public static void printEmpleados(List<Empleado> employeeList) {
        
        for (Empleado emp : employeeList) {
            System.out.println("__________________________");
            System.out.println("Id: " + emp.getId());
            System.out.println("Name: " + emp.getName());
            System.out.println("Surname: " + emp.getSurname());
            System.out.println("Position: " + emp.getPosition());
            System.out.println("Salary: " + emp.getSalary());
            System.out.println("Join date: " + emp.getJoin_Date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }

    public static void printBanner() {
        String texto = "                                               ,----,         ,----,                        \n" +
                               "                 ,----..                     ,/   .`|       ,/   .`|                   ,--. \n" +
                               "  .--.--.       /   /   \\       ,---,.     ,`   .'  :     ,`   .'  :     ,---,.    ,--/  /| \n" +
                               " /  /    '.    /   .     :    ,'  .' |   ;    ;     /   ;    ;     /   ,'  .' | ,---,': / ' \n" +
                               "|  :  /`. /   .   /   ;.  \\ ,---.'   | .'___,/    ,'  .'___,/    ,'  ,---.'   | :   : '/ /  \n" +
                               ";  |  |--`   .   ;   /  ` ; |   |   .' |    :     |   |    :     |   |   |   .' |   '   ,   \n" +
                               "|  :  ;_     ;   |  ; \\ ; | :   :  :   ;    |.';  ;   ;    |.';  ;   :   :  |-, '   |  /    \n" +
                               " \\  \\    `.  |   :  | ; | ' :   |  |-, `----'  |  |   `----'  |  |   :   |  ;/| |   ;  ;    \n" +
                               "  `----.   \\ .   |  ' ' ' : |   :  ;/|     '   :  ;       '   :  ;   |   :   .' :   '   \\   \n" +
                               "  __ \\  \\  | '   ;  \\; /  | |   |   .'     |   |  '       |   |  '   |   |  |-, '   |    '  \n" +
                               " /  /`--'  /  \\   \\  ',  /  '   :  '       '   :  |       '   :  |   '   :  ;/| |   : |.  \\ \n" +
                               "'--'.     /    ;   :    /   |   |  |       ;   |.'        ;   |.'    |   |    \\ |   | '_\\.' \n" +
                               "  `--'---'      \\   \\ .'    |   :  \\       '---'          '---'      |   :   .' '   : |     \n" +
                               "                 `---`      |   | ,'                                 |   | ,'   ;   |,'     \n" +
                               "                            `----'                                   `----'     '---'       ";

        System.out.println(texto);
    }
}

package controlsys;

import empleados.models.Employee;
import empleados.persistence.PersistenceController;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static empleados.constants.Colors.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import controlsys.exceptions.InvalidDataException;
import controlsys.exceptions.NonexistentEntityException;
import controlsys.exceptions.PreexistingEntityException;

/**
 * The Empleados class is the main class that manages the employee system.
 * It provides options to add, search, modify, and delete employees, as well as
 * print all employees.
 * The class uses a PersistenceController to interact with the employee data,
 * stored in a SQL database.
 * 
 * To run the program, execute the main method in this class.
 * 
 * @author Manak
 */
public class Empleados {

    public static void main(String[] args) throws Exception {

        PersistenceController controller = new PersistenceController();
        Scanner sc = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("#,###.##");

        printBanner();
        do {
            printMenu();
            System.out.println("Choose an option: ");

            int option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1 -> {
                    createEmployee(controller, sc, df);
                }
                case 2 -> {
                    searchEmployee(controller, sc, df);
                }
                case 3 -> {
                    modifyEmployee(controller, sc, df);
                }
                case 4 -> {
                    System.out.println("Id: ");
                    int id1 = Integer.parseInt(sc.nextLine());
                    controller.deleteEmployee(id1);
                    System.out.println("Employee id." + id1 + " deleted");
                }
                case 5 -> {
                    List<Employee> employees = controller.findEmployee();
                    printEmpleados(employees, df);
                    System.out.println("\nEnter to continue...");
                }
                case 6 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option");
            }
        } while (!"exit".equals(sc.nextLine()));
        System.out.println("Exiting...");
        System.exit(1);
    }

    /**
     * Prints the menu options for the employee system.
     */
    public static void printMenu() {
        System.out.println(PURPLE_UNDERLINED + "┌──────────────────────────────┐┐" + RESET);
        System.out.println(CYAN_BOLD + "│         EMPLOYEE MANAGER     |│");
        System.out.println(PURPLE_UNDERLINED + "├──────────────────────────────|┤" + RESET);
        System.out.println(GREEN_BOLD + "│ " + BLUE_BOLD + "1. Add                      " + GREEN_BOLD + " ││" + RESET);
        System.out.println(GREEN_BOLD + "│ " + BLUE_BOLD + "2. Search                   " + GREEN_BOLD + " ││" + RESET);
        System.out.println(GREEN_BOLD + "│ " + BLUE_BOLD + "3. Modify                   " + GREEN_BOLD + " ││" + RESET);
        System.out.println(GREEN_BOLD + "│ " + BLUE_BOLD + "4. Delete                   " + GREEN_BOLD + " ││" + RESET);
        System.out.println(GREEN_BOLD + "│ " + BLUE_BOLD + "5. Print All                " + GREEN_BOLD + " ││" + RESET);
        System.out
                .println(GREEN_BOLD + "│ " + PURPLE_BOLD + "6. EXIT                     " + GREEN_BOLD + " ││" + RESET);
        System.out.println(PURPLE_UNDERLINED + "└──────────────────────────────┘┘" + RESET);
    }

    /**
     * Creates a new employee based on user input and adds it to the system.
     * 
     * @param controller    the PersistenceController used to interact with the
     *                      employee data
     * @param sc            the Scanner object used to read user input
     * @param decimalFormat the DecimalFormat object used to format the salary
     */
    public static void createEmployee(PersistenceController controller, Scanner sc, DecimalFormat decimalFormat)
            throws Exception {
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
        try {
            Employee emp = new Employee(name, surname, position, salary, date);
            controller.createEmployee(emp);
            System.out.println("Employee created");
            System.out.println("Id: " + emp.getId() + " Name: " + emp.getName() + " Surname: " + emp.getSurname()
                    + " Position: " + emp.getPosition() + " Salary: " + decimalFormat.format(emp.getSalary())
                    + " Join date: "
                    + emp.getJoin_Date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            System.out.println("\nEnter to continue...");
        } catch (InvalidDataException ex) {
            System.out.println(ex.getMessage());
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Searches for an employee based on user input and prints the details.
     * 
     * @param controller    the PersistenceController used to interact with the
     *                      employee data
     * @param sc            the Scanner object used to read user input
     * @param decimalFormat the DecimalFormat object used to format the salary
     */
    public static void searchEmployee(PersistenceController controller, Scanner sc, DecimalFormat decimalFormat)
            throws Exception {
        System.out.println("Search by: ");
        System.out.println("1. Surname");
        System.out.println("2. Position");
        int option = Integer.parseInt(sc.nextLine());
        switch (option) {
            case 1 -> {
                System.out.println("Surname: ");
                String surname = sc.nextLine();
                Employee employee = controller.findSurname(surname);
                printEmpleado(employee, decimalFormat);
            }
            case 2 -> {
                System.out.println("Position: ");
                String position = sc.nextLine();
                List<Employee> employeesByPosition = controller.findPosition(position);
                printEmpleados(employeesByPosition, decimalFormat);
            }

        }
    }

    /**
     * Modifies an employee based on user input and updates the system.
     * 
     * @param controller    the PersistenceController used to interact with the
     *                      employee data
     * @param sc            the Scanner object used to read user input
     * @param decimalFormat the DecimalFormat object used to format the salary
     * @throws Exception
     */
    public static void modifyEmployee(PersistenceController controller, Scanner sc, DecimalFormat decimalFormat)
            throws Exception {
        try {
            System.out.println("Id: ");
            int id = Integer.parseInt(sc.nextLine());
            Employee employee = controller.findId(id);
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
            controller.updateEmployee(employee);
            System.out.println("Employee updated");
            printEmpleado(employee, decimalFormat);
            System.out.println("\nEnter to continue...");
        } catch (InvalidDataException ex) {
            System.out.println(ex.getMessage());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Empleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prints the details of an employee.
     * 
     * @param emp           the employee to print
     * @param decimalFormat the DecimalFormat object used to format the salary
     */
    public static void printEmpleado(Employee emp, DecimalFormat decimalFormat) {
        System.out.println("__________________________");
        System.out.println("Id: " + emp.getId());
        System.out.println("Name: " + emp.getName());
        System.out.println("Surname: " + emp.getSurname());
        System.out.println("Position: " + emp.getPosition());
        System.out.println("Salary: " + decimalFormat.format(emp.getSalary()));
        System.out.println("Join date: " + emp.getJoin_Date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    /**
     * Prints the details all employees.
     * 
     * @param employeeList  the list of employees to print
     * @param decimalFormat the DecimalFormat object used to format the salary
     */
    public static void printEmpleados(List<Employee> employeeList, DecimalFormat decimalFormat) {

        for (Employee emp : employeeList) {
            System.out.println("__________________________");
            System.out.println("Id: " + emp.getId());
            System.out.println("Name: " + emp.getName());
            System.out.println("Surname: " + emp.getSurname());
            System.out.println("Position: " + emp.getPosition());
            System.out.println("Salary: " + decimalFormat.format(emp.getSalary()));
            System.out.println("Join date: " + emp.getJoin_Date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }

    /**
     * Prints the banner for the employee system :)
     */
    public static void printBanner() {
        String text = "                                               ,----,         ,----,                        \n"
                +
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

        System.out.println(text);
    }
}

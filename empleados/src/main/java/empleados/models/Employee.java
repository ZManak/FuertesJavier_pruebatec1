package empleados.models;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import controlsys.exceptions.InvalidDataException;

/**
 * This class represents the employee entity, it has the attributes of the
 * employee.
 * It also has the getters and setters of the attributes, as well the
 * constructors.
 * It also has the annotations to be used by the JPA
 * 
 * @author Manak
 */

@Entity
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String position;
    private double salary;
    private LocalDate join_date;

    public Employee() {

    }

    public Employee(String name, String surname, String position, double salary, LocalDate join_date)
            throws InvalidDataException {
        if (name.equals("") || surname.equals("") || position.equals("")) {
            throw new InvalidDataException(

            );
        }
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.salary = salary;
        this.join_date = join_date;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public LocalDate getJoin_Date() {
        return join_date;
    }

    public void setJoin_Date(LocalDate join_date) {
        this.join_date = join_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

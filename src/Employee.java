import java.time.LocalDate;
import java.util.Objects;

public class Employee {
    private int id;
    private String name;
    private String surname;
    private int age;
    private double salary;
    private Department department;
    private boolean isEmployer;
    private LocalDate startDate;
    private String email;
    private String phoneNumber;
    private Position position;
    private String address;

    @Override
    public String toString() {
        return  id +","+ name + "," + surname + "," + age + "," + salary + "," + department + "," + isEmployer + "," + startDate + "," + email +
                "," + phoneNumber +
                "," + position +
                "," + address  ;
    }

    public Employee(int id, String name, String surname, int age, double salary, Department department, boolean isEmployer, LocalDate startDate, String email, String phoneNumber, Position position, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.isEmployer = isEmployer;
        this.startDate = startDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean isEmployer() {
        return isEmployer;
    }

    public void setEmployer(boolean employer) {
        isEmployer = employer;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id || Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
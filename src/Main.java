import EmployeeInformation.Department;
import EmployeeInformation.Employee;
import EmployeeInformation.Position;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {//I change 1. and 4. employees EmployeeInformation.Position(INTERN) and 2. and 3. employees startDate.ofYear(2023)
        List<Employee> processed_employees = readFileToList().stream()//2. Filter, Sort, and Modify EmployeeInformation.Employee Data
                .filter(employee -> Position.INTERN.equals(employee.getPosition()))
                .sorted(Comparator.comparing(Employee::getStartDate))
                .filter(employee -> Department.IT.equals(employee.getDepartment()))
                .filter(employee -> employee.getStartDate().isBefore(LocalDate.now().minusYears(5)))
                .toList();
        writeFileFromList(processed_employees, "src/Texts/processed_employees.txt");


        Map<Department, List<Employee>> department_summary = readFileToList().stream()//Generate a department summary report in department_summary.txt.
                .collect(Collectors.groupingBy(Employee::getDepartment));
        writeFileFromMapForDepartment(department_summary, "src/Texts/department_summary.txt");


        List<Employee> new_employees = readFileToList().stream()//List employees who started in the last 2 years in new_employees.txt.
                .filter(employee -> employee.getStartDate().isAfter(LocalDate.now().minusYears(2)))
                .toList();
        writeFileFromList(new_employees, "src/Texts/new_employees.txt");


        Set<Employee> unique_employees = new HashSet<>(readFileToList());//5.  Identify Duplicate EmployeeInformation.Employee  I add employee equals for solution
        writeFileFromList(unique_employees.stream().toList(), "src/Texts/unique_employees.txt");

        averageOfEachDepartment(department_summary, "src/Texts/average_salary_by_department.txt");


        Optional<Employee> min = readFileToList().stream()
                .min(Comparator.comparing(Employee::getStartDate));
        writeFileFromOptional(min, "src/Texts/longest_serving_employee.txt");//7. Find Longest-Serving EmployeeInformation.Employee


        Map<Position, List<Employee>> employees_by_position = readFileToList().stream()//8. Count Employees by EmployeeInformation.Position
                .collect(Collectors.groupingBy(Employee::getPosition));
        writeFileFromMapKeyPosition(employees_by_position, "src/Texts/employees_by_position.txt");


        writeFileFromMapKeyDepartmentThenPosition(department_summary, "src/Texts/employees_by_department_and_position.txt");
    }

    private static void writeFileFromMapKeyDepartmentThenPosition(Map<Department, List<Employee>> department_summary, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            Set<Department> keySetDepartment = department_summary.keySet();
            for (Department department : keySetDepartment) {
                writer.write(department + ":");
                writer.newLine();
                Map<Position, List<Employee>> positionListMap = department_summary.get(department).stream()
                        .collect(Collectors.groupingBy(Employee::getPosition));
                Set<Position> keySetPosition = positionListMap.keySet();
                for (Position position : keySetPosition) {
                    writer.write("  " + position + ":");
                    writer.newLine();
                    for (Employee employee : positionListMap.get(position)) {
                        writer.write("      " + employee.toString());
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeFileFromMapKeyPosition(Map<Position, List<Employee>> employees_by_position, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            Set<Position> keySet = employees_by_position.keySet();
            for (Position position : keySet) {
                writer.write(position + " count:");
                int counter = employees_by_position.get(position).size();
                writer.write(counter + "");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeFileFromOptional(Optional<Employee> optionalEmployee, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            writer.write(String.valueOf(optionalEmployee.orElse(null)));
            writer.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<Employee> readFileToList() {//1. Read EmployeeInformation.Employee Data from a Text File
        List<Employee> employeeList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/Texts/employees.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                String[] localDate = split[7].split("-");
                employeeList.add(
                        new Employee(
                                Integer.parseInt(split[0]),
                                split[1],
                                split[2],
                                Integer.parseInt(split[3]),
                                BigDecimal.valueOf(Double.parseDouble(split[4])),
                                switch (split[5].toUpperCase()) {
                                    case "IT" -> Department.IT;
                                    case "HR" -> Department.HR;
                                    case "FINANCE" -> Department.FINANCE;
                                    case "MARKETING" -> Department.MARKETING;
                                    case "SALES" -> Department.SALES;
                                    case "EXECUTIVE" -> Department.EXECUTIVE;
                                    default -> {
                                        System.out.println("EmployeeInformation.Department");
                                        yield null;
                                    }
                                },
                                !split[6].equalsIgnoreCase("False"),
                                LocalDate.of(
                                        Integer.parseInt(localDate[0]),
                                        Integer.parseInt(localDate[1]),
                                        Integer.parseInt(localDate[2])),
                                split[8],
                                split[9],
                                switch (split[10].toUpperCase()) {
                                    case "DIRECTOR" -> Position.DIRECTOR;
                                    case "EXECUTIVE" -> Position.EXECUTIVE;
                                    case "INTERN" -> Position.INTERN;
                                    case "JUNIOR" -> Position.JUNIOR;
                                    case "MANAGER" -> Position.MANAGER;
                                    case "SENIOR" -> Position.SENIOR;
                                    default -> {
                                        System.out.println("EmployeeInformation.Position");
                                        yield null;
                                    }
                                },
                                split[11]
                        )
                );
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return employeeList;
    }

    private static void writeFileFromList(List<Employee> processed_employees, String name) {//3. Write Processed EmployeeInformation.Employee Data to a New Text File
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            for (Employee employee : processed_employees) {
                writer.write(employee.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void writeFileFromMapForDepartment(Map<Department, List<Employee>> department_summary, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            Set<Department> keySetDepartment = department_summary.keySet();
            for (Department department : keySetDepartment) {
                writer.write(department + ":");
                writer.newLine();
                writer.write("employees count:"+department_summary.get(department).size());
                writer.newLine();
                double sum=0;
                for (Employee employee:department_summary.get(department)){
                    sum+=Double.parseDouble(employee.getSalary().toString());
                }
                writer.write("employees total money:"+sum);
                writer.newLine();

                Map<Position, List<Employee>> positionListMap = department_summary.get(department).stream()
                        .collect(Collectors.groupingBy(Employee::getPosition));
                Set<Position> keySetPosition = positionListMap.keySet();
                for (Position position : keySetPosition) {
                    writer.write("  " + position + ":");
                    writer.newLine();
                    writer.write("      employees count:"+positionListMap.get(position).size());
                    writer.newLine();
                    double sum1=0;
                    for (Employee employee:positionListMap.get(position)){
                        sum1+=Double.parseDouble(employee.getSalary().toString());
                    }
                    writer.write("      employees total money:"+sum1);
                    writer.newLine();

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void averageOfEachDepartment(Map<Department, List<Employee>> department_summary, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(name))) {
            Set<Department> keySet = department_summary.keySet();
            for (Department department : keySet) {
                writer.write(department + " average:");
                int counter = 0;
                BigDecimal average ;
                double sum=0;
                for (Employee employee : department_summary.get(department)) {
                    counter++;
                    sum+=Double.parseDouble(employee.getSalary().toString());
                }
                average=new BigDecimal(sum);
                BigDecimal result = average.divide(new BigDecimal(counter), 2, RoundingMode.HALF_UP);
                writer.write(result + "");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
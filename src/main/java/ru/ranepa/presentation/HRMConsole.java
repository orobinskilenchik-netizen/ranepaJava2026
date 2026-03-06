package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.service.HRMService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class HRMConsole {

    private final HRMService service;
    private final Scanner scanner = new Scanner(System.in);

    public HRMConsole(HRMService service) {
        this.service = service;
    }

    public void start() {
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");

            if (choice == 1) {
                showAll();
            } else if (choice == 2) {
                addEmployee();
            } else if (choice == 3) {
                deleteEmployee();
            } else if (choice == 4) {
                findById();
            } else if (choice == 5) {
                showStatistics();
            } else if (choice == 6) {
                filterByPosition();
            } else if (choice == 0) {
                System.out.println("Exit.");
                break;
            } else {
                System.out.println("Error.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== HRM System Menu ===");
        System.out.println("1. Show all employees");
        System.out.println("2. Add employee");
        System.out.println("3. Delete employee");
        System.out.println("4. Find employee by ID");
        System.out.println("5. Show statistics");
        System.out.println("6. Filter by position");
        System.out.println("0. Exit");
    }

    private void showAll() {
        List<Employee> employees = service.findAll();
        if (employees.isEmpty()) {
            System.out.println("There are no employees yet.");
            return;
        }
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    private void addEmployee() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter position: ");
        String position = scanner.nextLine();

        BigDecimal salary = readBigDecimal("Enter salary: ");
        LocalDate hireDate = readDate("Enter hire date (YYYY-MM-DD): ");

        Employee employee = new Employee(name, position, salary, hireDate);
        String message = service.addEmployee(employee);
        System.out.println(message);
    }

    private void deleteEmployee() {
        long id = readLong("Enter employee ID: ");
        String message = service.deleteEmployee(id);
        System.out.println(message);
    }

    private void findById() {
        long id = readLong("Enter employee ID: ");
        Optional<Employee> employee = service.findById(id);

        if (employee.isPresent()) {
            System.out.println(employee.get());
        } else {
            System.out.println("The employee was not found.");
        }
    }

    private void showStatistics() {
        System.out.println("Average salary: " + service.calculateAverageSalary());

        Optional<Employee> top = service.findTopEarner();
        if (top.isPresent()) {
            System.out.println("Maximum salary: " + top.get().getSalary());
            System.out.println("Who gets the maximum: " + top.get().getName() + " (" + top.get().getPosition() + ")");
        } else {
            System.out.println("There are no employees, statistics are not available.");
        }
    }

    private void filterByPosition() {
        System.out.print("Enter position: ");
        String position = scanner.nextLine();

        List<Employee> list = service.filterByPosition(position);
        if (list.isEmpty()) {
            System.out.println("Employees with the position " + position + " no.");
            return;
        }
        for (Employee e : list) {
            System.out.println(e);
        }
    }


    private int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Error: enter a number");
            }
        }
    }

    private long readLong(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Long.parseLong(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Error: Enter a valid ID (number)");
            }
        }
    }

    private BigDecimal readBigDecimal(String message) {
        while (true) {
            try {
                System.out.print(message);
                return new BigDecimal(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Error: Enter a number (for example, 1500.0).");
            }
        }
    }

    private LocalDate readDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Error: The date must be in the format YYYY-MM-DD.");
            }
        }
    }
}
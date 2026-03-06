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
            int choice = readInt();

            if (choice == 1) showAll();
            else if (choice == 2) addEmployee();
            else if (choice == 3) deleteEmployee();
            else if (choice == 4) findById();
            else if (choice == 5) showStatistics();
            else if (choice == 6) filterByPosition();
            else if (choice == 0) {
                System.out.println("Exit.");
                break;
            } else System.out.println("Error.");
        }
    }

    private void printMenu() {
        System.out.println("\n=== HRM System Menu ===");
        System.out.println("1. Show all");
        System.out.println("2. Add");
        System.out.println("3. Delete");
        System.out.println("4. Find by ID");
        System.out.println("5. Statistics");
        System.out.println("6. Filter by position");
        System.out.println("0. Exit");
    }

    private void showAll() {
        List<Employee> employees = service.findAll();
        if (employees.isEmpty()) {
            System.out.println("No employees.");
            return;
        }
        for (Employee e : employees) System.out.println(e);
    }

    private void addEmployee() {
        String name = readName();
        String position = readPosition();
        BigDecimal salary = readBigDecimal();
        LocalDate hireDate = readDate();

        Employee employee = new Employee(name, position, salary, hireDate);
        System.out.println(service.addEmployee(employee));
    }

    private void deleteEmployee() {
        long id = readLong();
        System.out.println(service.deleteEmployee(id));
    }

    private void findById() {
        long id = readLong();
        Optional<Employee> employee = service.findById(id);
        if (employee.isPresent()) System.out.println(employee.get());
        else System.out.println("Not found.");
    }

    private void showStatistics() {
        System.out.println("Average salary: " + service.calculateAverageSalary());
        Optional<Employee> top = service.findTopEarner();
        if (top.isPresent()) {
            System.out.println("Max salary: " + top.get().getSalary());
            System.out.println("Who gets max: " + top.get().getName());
        } else System.out.println("No employees.");
    }

    private void filterByPosition() {
        System.out.print("Enter position: ");
        String position = scanner.nextLine();
        List<Employee> list = service.filterByPosition(position);
        if (list.isEmpty()) System.out.println("No employees with position: " + position);
        else for (Employee e : list) System.out.println(e);
    }

    private int readInt() {
        while (true) {
            try {
                System.out.print("Choose: ");
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Error: enter number");
            }
        }
    }

    private long readLong() {
        while (true) {
            try {
                System.out.print("Enter ID: ");
                return Long.parseLong(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Error: enter number");
            }
        }
    }

    private BigDecimal readBigDecimal() {
        while (true) {
            try {
                System.out.print("Enter salary: ");
                return new BigDecimal(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Error: enter number");
            }
        }
    }

    private LocalDate readDate() {
        while (true) {
            try {
                System.out.print("Enter date (YYYY-MM-DD): ");
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Error: use format YYYY-MM-DD");
            }
        }
    }
    //
    private String readName() {
        while (true) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            if (name.matches("[a-zA-Zа-яА-ЯёЁ\\s]+")) {
                return name;
            } else {
                System.out.println("Error: Use ONLY letters for name!");
            }
        }
    }
    //
    private String readPosition() {
        while (true) {
            System.out.print("Enter position: ");
            String position = scanner.nextLine();

            if (position.matches("[a-zA-Zа-яА-ЯёЁ\\s]+")) {
                return position;
            } else {
                System.out.println("Error: Use ONLY letters for position!");
            }
        }
    }
        }

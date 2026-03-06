package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HRMService {

    private final EmployeeRepository repository;

    public HRMService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public String addEmployee(Employee employee) {
        return repository.save(employee);
    }

    public String deleteEmployee(long id) {
        return repository.delite(id);
    }

    public Optional<Employee> findById(long id) {
        return repository.findById(id);
    }

    public List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        for (Employee e : repository.findAll()) {
            list.add(e);
        }
        return list;
    }

    public BigDecimal calculateAverageSalary() {
        List<Employee> employees = findAll();
        if (employees.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (Employee e : employees) {
            sum = sum.add(e.getSalary());
        }

        return sum.divide(BigDecimal.valueOf(employees.size()), 2, RoundingMode.HALF_UP);
    }

    public Optional<Employee> findTopEarner() {
        List<Employee> employees = findAll();
        if (employees.isEmpty()) {
            return Optional.empty();
        }

        Employee top = employees.get(0);
        for (Employee e : employees) {
            if (e.getSalary().compareTo(top.getSalary()) > 0) {
                top = e;
            }
        }
        return Optional.of(top);
    }

    public List<Employee> filterByPosition(String position) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : findAll()) {
            if (e.getPosition() != null && e.getPosition().equalsIgnoreCase(position)) {
                result.add(e);
            }
        }
        return result;
    }
}
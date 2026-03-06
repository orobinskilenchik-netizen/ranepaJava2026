package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private Map<Long, Employee> employees = new HashMap<>();
    private static long nextId = 1;

    @Override
    public String save(Employee employee) {
        if (employee != null){
            employee.setId(nextId++);
            employees.put(employee.getId(), employee);
        }
        return "Employee" + employee.getId() +
                "" + employee.getName() + "add";
    }

    @Override
    public Optional<Employee> findById(long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public Iterable<Employee> findAll() {
        return employees.values();
    }

    @Override
    public String delite(long id) {
        Employee removed = employees.remove(id);

        if (removed == null) {
            return "Employee with ID " + id + " not found";
        }
        return "Employee with ID " + id + " delited";
    }
}

package ru.ranepa;

import ru.ranepa.presentation.HRMConsole;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.HRMService;

public class hrmapplication {
    public static void main(String[] args) {

        EmployeeRepository repository = new EmployeeRepositoryImpl();
        HRMService service = new HRMService(repository);
        HRMConsole console = new HRMConsole(service);

        console.start();
    }
}

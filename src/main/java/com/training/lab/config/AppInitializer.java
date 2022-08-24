package com.training.lab.config;

import com.training.lab.entity.Employee;
import com.training.lab.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppInitializer implements CommandLineRunner {

  @Autowired private EmployeeRepository empRepo;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("initializing...and...loading data in H2 DB table");

    this.empRepo.saveAll(this.loadEmployeeData());
  }

  private List<Employee> loadEmployeeData() {

    List<Employee> employees = new ArrayList<>();

    Employee emp1 = new Employee();
    emp1.setName("Peter Adams");
    emp1.setLocation("Singapore");
    emp1.setDept("CCIB");

    employees.add(emp1);

    emp1 = new Employee();
    emp1.setName("Ram Kaushik");
    emp1.setLocation("Bengaluru");
    emp1.setDept("FUNCTIONS");

    employees.add(emp1);

    emp1 = new Employee();
    emp1.setName("Venkat Raman");
    emp1.setLocation("Chennai");
    emp1.setDept("CCIB");

    employees.add(emp1);

    emp1 = new Employee();
    emp1.setName("Lawrence T");
    emp1.setLocation("China");
    emp1.setDept("TTO");

    employees.add(emp1);

    emp1 = new Employee();
    emp1.setName("Shashidhar K");
    emp1.setLocation("Bengaluru");
    emp1.setDept("TTO");

    employees.add(emp1);

    return employees;
  }
}

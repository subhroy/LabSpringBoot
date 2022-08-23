package com.training.lab.service;

import com.training.lab.entity.Employee;

import java.util.List;


public interface EmployeeService {

    Employee createEmployee(Employee newEmployee);

    List<Employee> getAllEmployees();

    Employee getById(long id);

    Employee updateEmployee(Employee modifiedEmployee);

    String deleteEmployee(long id);

    List<Employee> getByLocation(String location);

    List<Employee> getByDepartment(String dept);

}

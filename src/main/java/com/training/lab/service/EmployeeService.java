package com.training.lab.service;

import com.training.lab.entity.Employee;
import com.training.lab.exception.RecordNotCreatedException;
import com.training.lab.exception.RecordNotDeletedException;
import com.training.lab.exception.RecordNotFoundException;
import com.training.lab.exception.RecordNotUpdatedException;

import java.util.List;

public interface EmployeeService {

  Employee createEmployee(Employee newEmployee) throws RecordNotCreatedException;

  List<Employee> getAllEmployees();

  Employee getById(long id) throws RecordNotFoundException;

  Employee updateEmployee(Employee modifiedEmployee) throws RecordNotUpdatedException;

  String deleteEmployee(long id) throws RecordNotDeletedException;

  List<Employee> getByLocation(String location) throws RecordNotFoundException;

  List<Employee> getByDepartment(String dept);
}

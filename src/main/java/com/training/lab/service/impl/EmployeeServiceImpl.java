package com.training.lab.service.impl;

import com.training.lab.entity.Employee;
import com.training.lab.exception.RecordNotCreatedException;
import com.training.lab.exception.RecordNotDeletedException;
import com.training.lab.exception.RecordNotFoundException;
import com.training.lab.exception.RecordNotUpdatedException;
import com.training.lab.repository.EmployeeRepository;
import com.training.lab.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired private EmployeeRepository empRepo;

  @Override
  public Employee createEmployee(Employee newEmployee) throws RecordNotCreatedException {
    try {
      Employee emp = this.empRepo.save(newEmployee);
      return emp;
    } catch (DataAccessException e) {
      String msg = "Exception: Employee not created. Something went wrong..!!";
      throw new RecordNotCreatedException(msg, e);
    }
  }

  @Override
  public List<Employee> getAllEmployees() {
    return this.empRepo.findAll();
  }

  @Override
  public Employee getById(long id) throws RecordNotFoundException {
    Optional<Employee> emp = this.empRepo.findById(id);
    if (emp.isPresent()) {
      return emp.get();
    } else {
      String message = "Employee not found with id - " + id;
      throw new RecordNotFoundException(message);
    }
  }

  @Override
  public Employee updateEmployee(Employee modifiedEmployee) throws RecordNotUpdatedException {
    if (this.empRepo.existsById(modifiedEmployee.getId())) {
      try {
        Employee emp = this.empRepo.save(modifiedEmployee);
        return emp;
      } catch (DataAccessException e) {
        String message = "Employee not updated. Check data.";
        throw new RecordNotUpdatedException(message, e);
      }
    } else {
      String message = "Update failed. Employee not found with id - " + modifiedEmployee.getId();
      throw new RecordNotUpdatedException(message);
    }
  }

  @Override
  public String deleteEmployee(long id) throws RecordNotDeletedException {
    String message = "";
    try {
      this.empRepo.deleteById(id);
      message = "Successfully deleted";
    } catch (EmptyResultDataAccessException e) {
      message = "Failed to delete. Invalid employee id";
      throw new RecordNotDeletedException(message, e);
    }
    return message;
  }

  @Override
  public List<Employee> getByLocation(String location) throws RecordNotFoundException {
    List<Employee> employees = this.empRepo.findByLocation(location);
    if (employees.size() > 0) {
      return employees;
    } else {
      throw new RecordNotFoundException("No employee found in the location - " + location);
    }
  }

  @Override
  public List<Employee> getByDepartment(String dept) {
    List<Employee> employees = this.empRepo.findEmployeesByDepartment(dept);
    return employees;
  }
}

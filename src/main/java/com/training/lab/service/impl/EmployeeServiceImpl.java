package com.training.lab.service.impl;

import com.training.lab.entity.Employee;
import com.training.lab.repository.EmployeeRepository;
import com.training.lab.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired private EmployeeRepository empRepo;

  @Override
  public Employee createEmployee(Employee newEmployee) {
    Employee emp = this.empRepo.save(newEmployee);
    return emp;
  }

  @Override
  public List<Employee> getAllEmployees() {
    return this.empRepo.findAll();
  }

  @Override
  public Employee getById(long id) {

    log.info("this.empRepo.findById(id) :: "+this.empRepo.findById(id));
   /* Employee emp = this.empRepo.findById(id).get();
    return emp;*/
    Optional<Employee> emp = this.empRepo.findById(id);
    if(emp.isPresent()) {
      return emp.get();
    }
    return null;
  }

  @Override
  public Employee updateEmployee(Employee modifiedEmployee) {
    if (this.empRepo.existsById(modifiedEmployee.getId())) {
      Employee emp = this.empRepo.save(modifiedEmployee);
      return emp;
    }
    return null;
  }

  @Override
  public String deleteEmployee(long id) {
    String message = "";
    this.empRepo.deleteById(id);
    message = "Successfully deleted";
    return message;
  }

  @Override
  public List<Employee> getByLocation(String location) {
    List<Employee> employees = this.empRepo.findByLocation(location);
    return employees;
  }

  @Override
  public List<Employee> getByDepartment(String dept) {
    List<Employee> employees = this.empRepo.findEmployeesByDepartment(dept);
    return employees;
  }
}

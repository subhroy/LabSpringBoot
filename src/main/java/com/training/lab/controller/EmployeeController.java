package com.training.lab.controller;

import com.training.lab.entity.Employee;
import com.training.lab.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

  @Autowired private EmployeeService employeeService;

  @PostMapping("/")
  public String saveEmployee(@RequestBody Employee newEmp) {
    Employee emp = this.employeeService.createEmployee(newEmp);
    return "Emp created Id  " + emp.getId() + " , name " + emp.getName();
  }

  @GetMapping("/")
  public List<Employee> getAll() {
    List<Employee> empLst = this.employeeService.getAllEmployees();
    return empLst;
  }

  @GetMapping("/{id}")
  public Employee getById(@PathVariable long id) {
    Employee emp = this.employeeService.getById(id);
    return emp;
  }

  @PutMapping("/")
  public Employee updateEmployee(@RequestBody Employee employee) {
    Employee emp = this.employeeService.updateEmployee(employee);
    return emp;
  }

  @DeleteMapping("/{id}")
  public String deleteEmployee(@PathVariable long id) {
    String message = this.employeeService.deleteEmployee(id);
    return message;
  }

  @GetMapping("/location/{loc}")
  public List<Employee> getEmployeeByLoc(@PathVariable String loc){
    List<Employee> employees = this.employeeService.getByLocation(loc);
    return employees;
  }

  @GetMapping("/department/{dept}")
  public List<Employee> getEmployeeByDept(@PathVariable String dept){
    List<Employee> employees = this.employeeService.getByDepartment(dept);
    return employees;
  }

}

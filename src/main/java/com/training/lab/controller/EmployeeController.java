package com.training.lab.controller;

import com.training.lab.config.APIErrorResponse;
import com.training.lab.config.APISuccessResponse;
import com.training.lab.entity.Employee;
import com.training.lab.exception.RecordNotCreatedException;
import com.training.lab.exception.RecordNotDeletedException;
import com.training.lab.exception.RecordNotFoundException;
import com.training.lab.exception.RecordNotUpdatedException;
import com.training.lab.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
@Tag(name = "Employee", description = "Employee Service APIs")
@Slf4j
public class EmployeeController {

  @Autowired private EmployeeService employeeService;

  @Autowired private JobLauncher jobLauncher;

  @Autowired
  @Qualifier("emp_job")
  private Job job;

  @PostMapping("/")
  @Operation(summary = "create-employee", description = "Create new employee")
  public ResponseEntity<?> saveEmployee(@RequestBody Employee newEmployee) {

    try {

      APISuccessResponse response = new APISuccessResponse();
      Employee emp = this.employeeService.createEmployee(newEmployee);

      response.setMessage("Employee created");
      response.setResponseBody(emp);
      response.setStatusCode(HttpStatus.CREATED);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (RecordNotCreatedException e) {

      APIErrorResponse response = new APIErrorResponse();

      response.setMessage("Something went wrong");
      response.setCause(e.getLocalizedMessage());
      response.setHttpStatusCode(HttpStatus.BAD_REQUEST);

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
  }

  @GetMapping("/")
  @Operation(summary = "get-all-employees", description = "Get all employees")
  public ResponseEntity<?> getAll() {

    APISuccessResponse response = new APISuccessResponse();
    List<Employee> emps = this.employeeService.getAllEmployees();
    log.info("List of all employees :: " + emps);
    response.setMessage("Employee found - " + emps.size());
    response.setResponseBody(emps);
    response.setStatusCode(HttpStatus.FOUND);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "fetch-employee-by-empId",
      description = "Fetch an existing employee by Employee Id.")
  public ResponseEntity<?> getById(@PathVariable long id) {

    try {

      APISuccessResponse response = new APISuccessResponse();
      Employee emp = this.employeeService.getById(id);

      response.setMessage("Employee found");
      response.setResponseBody(emp);
      response.setStatusCode(HttpStatus.FOUND);

      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (RecordNotFoundException e) {

      APIErrorResponse response = new APIErrorResponse();
      log.error("Exception Occurred while fetching employee by ID..!!! ");
      response.setMessage("Something went wrong");
      response.setCause(e.getLocalizedMessage());
      response.setHttpStatusCode(HttpStatus.NOT_FOUND);

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PutMapping("/")
  @Operation(summary = "update-employee", description = "Update Existing Employee")
  public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {

    try {
      APISuccessResponse response = new APISuccessResponse();
      Employee emp = this.employeeService.updateEmployee(employee);

      response.setMessage("Employee found");
      response.setResponseBody(emp);
      response.setStatusCode(HttpStatus.ACCEPTED);

      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (RecordNotUpdatedException e) {

      APIErrorResponse response = new APIErrorResponse();

      response.setMessage("Something went wrong");
      response.setCause(e.getLocalizedMessage());
      response.setHttpStatusCode(HttpStatus.BAD_REQUEST);

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "delete-employee", description = "Delete and existing employee record")
  public ResponseEntity<?> deleteEmployee(@PathVariable long id) {

    try {
      APISuccessResponse response = new APISuccessResponse();
      String message = this.employeeService.deleteEmployee(id);
      response.setMessage("Employee deleted");
      response.setResponseBody(message);
      response.setStatusCode(HttpStatus.ACCEPTED);

      return ResponseEntity.status(HttpStatus.OK).body(response);

    } catch (RecordNotDeletedException e) {
      APIErrorResponse response = new APIErrorResponse();

      response.setMessage("Something went wrong");
      response.setCause(e.getLocalizedMessage());
      response.setHttpStatusCode(HttpStatus.BAD_REQUEST);

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
  }

  @GetMapping("/location/{location}")
  @Operation(
      summary = "fetch-employee-by-location",
      description = "Fetch an existing employee by location")
  public ResponseEntity<?> getEmployeesByLocation(@PathVariable String location) {

    try {
      APISuccessResponse response = new APISuccessResponse();
      List<Employee> employees = this.employeeService.getByLocation(location);

      response.setMessage("Employee found - " + employees.size());
      response.setResponseBody(employees);
      response.setStatusCode(HttpStatus.FOUND);

      return ResponseEntity.status(HttpStatus.OK).body(response);

    } catch (RecordNotFoundException e) {
      APIErrorResponse response = new APIErrorResponse();

      response.setMessage("Something went wrong");
      response.setCause(e.getLocalizedMessage());
      response.setHttpStatusCode(HttpStatus.BAD_REQUEST);

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
  }

  @GetMapping("/department/{dept}")
  @Operation(
      summary = "fetch-employee-by-dept",
      description = "Fetch an existing employee by dept.")
  public ResponseEntity<?> getEmployeesByDept(@PathVariable String dept) {

    APISuccessResponse response = new APISuccessResponse();
    List<Employee> employees = this.employeeService.getByDepartment(dept);

    response.setMessage("Employee found - " + employees.size());
    response.setResponseBody(employees);
    response.setStatusCode(HttpStatus.FOUND);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/create/batch")
  @Operation(summary = "Bulk creation of employees in batch")
  public ResponseEntity<?> saveEmployeesInBatch() {

    try {
      APISuccessResponse response = new APISuccessResponse();
      Map<String, JobParameter> maps = new HashMap<>();
      maps.put("TimeStamp", new JobParameter(System.currentTimeMillis()));
      JobParameters jobParameters = new JobParameters(maps);
      JobExecution execution = jobLauncher.run(job, jobParameters);
      response.setMessage("Status - " + execution.getStatus());
      response.setResponseBody(
          "START TIME: " + execution.getStartTime() + "END TIME: " + execution.getEndTime());
      response.setStatusCode(HttpStatus.CREATED);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (JobExecutionAlreadyRunningException
        | JobRestartException
        | JobInstanceAlreadyCompleteException
        | JobParametersInvalidException e) {

      APIErrorResponse response = new APIErrorResponse();
      response.setMessage("Something went wrong. Batch processing failed.");
      response.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }
}

package com.training.lab.repository;

import com.training.lab.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  List<Employee> findByLocation(String location);

  List<Employee> findByDept(String department);

  @Query(value = "select * from employee where location = ?1", nativeQuery = true)
  List<Employee> findEmployeesByLocation(String location);

  @Query(value = "select * from employee where dept = ?1", nativeQuery = true)
  List<Employee> findEmployeesByDepartment(String dept);
}

package com.training.lab.batch.writer;

import com.training.lab.entity.Employee;
import com.training.lab.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class EmployeeJavaWriter implements ItemWriter<Employee> {

    @Autowired
    private EmployeeRepository empRepo;

    @Override
    public void write(List<? extends Employee> items) throws Exception {
        this.empRepo.saveAll(items);
        log.info("Employee saved successfully in batch");
    }
}

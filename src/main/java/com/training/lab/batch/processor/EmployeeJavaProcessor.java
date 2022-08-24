package com.training.lab.batch.processor;

import com.training.lab.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmployeeJavaProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee item) throws Exception {

        /**
         * process logic goes here below
         */
        item.setName(item.getName().toUpperCase());
        log.info("Employee processing from file completed");
        return item;
    }
}

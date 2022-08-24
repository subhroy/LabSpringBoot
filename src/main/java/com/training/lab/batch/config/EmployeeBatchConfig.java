package com.training.lab.batch.config;

import com.training.lab.entity.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class EmployeeBatchConfig {

  String HEADERS[] = {"name", "location", "dept"};

  /**
   *  1. Create Job
   *  2. Create Step
   **/
  @Bean(name = "emp_job")
  public Job employeeJob(
      JobBuilderFactory jobBuilderFactory,
      StepBuilderFactory stepBuilderFactory,
      ItemReader<Employee> itemReader,
      ItemProcessor<Employee, Employee> itemProcessor,
      ItemWriter<Employee> itemWriter) {

    Step step =
        stepBuilderFactory
            .get("EMPLOYEE_ETL_STEP")
            .<Employee, Employee>chunk(100)
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWriter)
            .build();

    Job job =
        jobBuilderFactory
            .get("EMPLOYEE_JOB")
            .incrementer(new RunIdIncrementer())
            .start(step)
            .build();

    return job;
  }

  @Bean
  public FlatFileItemReader<Employee> flatFileItemReader(
      @Value("${app.batch.file.employee-data}") Resource resource) {

    FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>();

    reader.setName("EMPLOYEE_CSV_READER");
    reader.setResource(resource);
    reader.setLinesToSkip(1);
    reader.setLineMapper(employeeLineMapper());

    return reader;
  }

  @Bean
  public LineMapper<Employee> employeeLineMapper() {

    DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<Employee>();

    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setNames(HEADERS);

    lineMapper.setLineTokenizer(tokenizer);

    BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<Employee>();
    fieldSetMapper.setTargetType(Employee.class);

    lineMapper.setFieldSetMapper(fieldSetMapper);

    return lineMapper;
  }
}

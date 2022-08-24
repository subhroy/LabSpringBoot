###### Hands-on
1. Swagger :http://localhost:7070/swagger-ui/index.html
2. H2 Console: http://localhost:7070/h2-console
3. http://localhost:7070/actuator
    - "management.endpoints.web.base-path=/monitor" This will change the URL to http://localhost:7070/monitor


###### Note:
@Component is parent of everything like @Service, @Repository...i.e. every one extends @Component. That's why they can be Autowired.

###### difference between @Bean / @Component 
@Component is a class-level annotation, but @Bean is at the method level, so @Component is only an option when a class's source code is editable. @Bean can always be used, but it's more verbose. @Component is compatible with Spring's auto-detection, but @Bean requires manual class instantiation.
@Bean can only be used inside @Component.


###### OpenAPI-doc we are using. We can use Springfox API also for swagger right. Do we have any advantage of open-ui api?
Spring-fx doesn't work above Spring 2.5.0 onwards. It has compatibility issue. So use open-api-ui

######  Reference Log related config
1. https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-logging.html
2. https://howtodoinjava.com/spring-boot2/logging/logging-application-properties/
3. https://www.baeldung.com/spring-boot-logging
4. https://www.codingame.com/playgrounds/4497/configuring-logback-with-spring-boot

###### Spring Batch : Read employee.csv file, 
###### then process the data(here we are just making the emp names capital) 
###### and in H2 DB using Spring Batch
* Check the com.training.lab.batch packages. 
* Created employee.csv file
* Added "app.batch.file.employee-data=classpath:employee.csv" in the application.properties
* Added "spring.batch.job.enabled=false;" in the application.properties to stop spring batch trigger automatically
1. Create a Job
2. Create Step

After run check.
H2 DB
----------------------------
SELECT * FROM BATCH_JOB_EXECUTION;
SELECT * FROM BATCH_JOB_EXECUTION_CONTEXT;
SELECT * FROM BATCH_JOB_EXECUTION_PARAMS;
SELECT * FROM BATCH_JOB_INSTANCE;
SELECT * FROM BATCH_STEP_EXECUTION;
SELECT * FROM BATCH_STEP_EXECUTION_CONTEXT;
SELECT * FROM EMPLOYEE;


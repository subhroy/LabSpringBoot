###### Hands-on
1. Swagger :http://localhost:7070/swagger-ui/index.html
2. H2 Console: http://localhost:7070/h2-console
3. http://localhost:7070/actuator
    - "management.endpoints.web.base-path=/monitor" This will change the URL to http://localhost:7070/monitor


###### Note:
@Component is parent of everything like @Service, @Repository...i.e. every one extends @Component. That's why they can be Autowired.

###### difference between @Bean / @Component 
@Component is a class-level annotation, but @Bean is at the method level, so @Component is only an option when a class's source code is editable. @Bean can always be used, but it's more verbose. @Component is compatible with Spring's auto-detection, but @Bean requires manual class instantiation.


###### OpenAPI-doc we are using. We can use Springfox API also for swagger right. Do we have any advantage of open-ui api?
Spring-fx doesn't work above Spring 2.5.0 onwards. It has compatibility issue. So use open-api-ui

######  Reference Log related config
1. https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/boot-features-logging.html
2. https://howtodoinjava.com/spring-boot2/logging/logging-application-properties/
3. https://www.baeldung.com/spring-boot-logging



package com.training.lab.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

  /*@Value("${app.swagger.title}")
  private String title;
  @Value("${app.swagger.version}")
  private String version;
  @Value("${app.swagger.description}")
  private String description;*/

  @Autowired
  private SwaggerProperties swaggerProperties;

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(
            new Info()
                .title(this.swaggerProperties.getTitle())
                .version(this.swaggerProperties.getVersion())
                .description(this.swaggerProperties.getDescription()));
  }

}

package com.training.lab.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.swagger")
@Component
public class SwaggerProperties {

    private String title;
    private String version;
    private String description;
}

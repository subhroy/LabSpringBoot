package com.training.lab.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APISuccessResponse {

    private String message;
    private Object responseBody;
    private Object statusCode;
    private boolean success = true;

}

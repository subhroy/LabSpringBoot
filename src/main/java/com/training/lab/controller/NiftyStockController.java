package com.training.lab.controller;

import com.training.lab.config.APISuccessResponse;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/nifty")
public class NiftyStockController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("nifty_stock_batch")
    private Job job;

    @GetMapping("/batch")
    public ResponseEntity<?> loadNiftyStockData() throws RecordNotAddedException {

        APISuccessResponse response = new APISuccessResponse();

        Map<String, JobParameter> maps = new HashMap<>();

        maps.put("timestamp", new JobParameter(System.currentTimeMillis()));

        JobParameters parameters = new JobParameters(maps);


        JobExecution execution;
        try {
            execution = jobLauncher.run(job, parameters);
            response.setMessage("Status- "+execution.getStatus());
            response.setResponseBody(
                    " Start Time: " + execution.getStartTime()+
                            " End Time: "+ execution.getEndTime());
            response.setStatusCode(HttpStatus.OK);
            response.setSuccess(true);


            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header("status", String.valueOf(HttpStatus.ACCEPTED))
                    .body(response);




        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
            // TODO Auto-generated catch block
            throw new RecordNotAddedException("Batch Processing of nifty failed", e);
        }
    }

}
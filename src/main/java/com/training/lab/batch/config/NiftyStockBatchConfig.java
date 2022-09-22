package com.training.lab.batch.config;

import com.training.lab.entity.NiftyStock;
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
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Configuration
@EnableBatchProcessing
public class NiftyStockBatchConfig {

    /**
     * 1. create job
     * 2. create step
     */

    private String STOCK_DATASET_COLUMNS [] = {"tradingdate",
            "symbol",
            "securityType",
            "prevClose",
            "open",
            "high",
            "low",
            "lastPrice",
            "close",
            "vwap",
            "volume",
            "turnover",
            "tradesTotal",
            "deliverablesVolume",
            "deliverablePercent"

    };

    @Bean(name="nifty_stock_batch")
    public Job nifthStockJob(JobBuilderFactory jobBuilderFactory,
                             StepBuilderFactory stepBuilderFactory,
                             ItemReader<NiftyStock> itemReader,
                             ItemProcessor<NiftyStock, NiftyStock> itemProcessor,
                             ItemWriter<NiftyStock> itemWriter) {

        Step step = stepBuilderFactory.get("NIFTY-Stock-Step")
                .<NiftyStock,NiftyStock>chunk(100)
				.reader(itemReader)
//                .reader(multiResourceItemReader()) //Used for multiple file read
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        Job job = jobBuilderFactory.get("NIFTY-Stock-Job")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();

        return job;

    }

    /**
     * Used for multiple CSV file read under resource/stock-data
     */
    public MultiResourceItemReader<NiftyStock> multiResourceItemReader() {

        Resource[] resources = null;
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        MultiResourceItemReader<NiftyStock> resourceItemReader = new MultiResourceItemReader<NiftyStock>();
        try {
            resources = patternResolver.getResources("stock-data/*.csv");
            resourceItemReader.setResources(resources);
            resourceItemReader.setDelegate(flatItemReader());
            return resourceItemReader;
        } catch(IOException e) {
            e.printStackTrace();
            return resourceItemReader;
        }
    }

    @Bean
	//public FlatFileItemReader<NiftyStock> flatItemReader(@Value("${app.batch.file.nifty-data}") Resource resource) {
    public FlatFileItemReader<NiftyStock> flatItemReader() { // For multiple file read under resource/stock-data

        FlatFileItemReader<NiftyStock> reader = new FlatFileItemReader<NiftyStock>();

		//reader.setResource(resource);
        reader.setLinesToSkip(1);
        reader.setLineMapper(niftyStockLineMapper());
        reader.setName("NIFTY-Stock-Reader");

        return reader;

    }

    @Bean
    public LineMapper<NiftyStock> niftyStockLineMapper() {

        DefaultLineMapper<NiftyStock> lineMapper = new DefaultLineMapper<NiftyStock>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(STOCK_DATASET_COLUMNS);
        lineMapper.setLineTokenizer(tokenizer);

        BeanWrapperFieldSetMapper<NiftyStock> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(NiftyStock.class);
        fieldSetMapper.setConversionService(createConversionService());
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }


    public ConversionService createConversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        DefaultConversionService.addDefaultConverters(conversionService);
        conversionService.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String text) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(text, formatter);
            }
        });
        return conversionService;
    }

}

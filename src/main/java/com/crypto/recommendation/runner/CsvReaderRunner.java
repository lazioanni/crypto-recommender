package com.crypto.recommendation.runner;

import com.crypto.recommendation.service.CsvReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CsvReaderRunner is a component that runs at application startup.
 *
 * @author lioannidis
 * @version 0.1
 */
@Component
public class CsvReaderRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CsvReaderRunner.class);

    private final CsvReaderService csvReaderService;

    public CsvReaderRunner(CsvReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

    /**
     * This method is executed when the Spring Boot application starts.
     * It reads all CSV files from the specified directory.
     *
     * @param args command-line arguments passed to the application
     */
    @Override
    public void run(String... args) {
        logger.info("Starting to read CSV files from directory: data");

        try {
            csvReaderService.readAllCsvFilesFromDirectory("data");
            logger.info("Successfully processed CSV files from directory: data");
        } catch (Exception e) {
            logger.error("Error occurred while reading CSV files from the directory 'data'", e);
        }
    }
}
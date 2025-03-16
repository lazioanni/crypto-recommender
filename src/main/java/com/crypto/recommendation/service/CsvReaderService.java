package com.crypto.recommendation.service;

import com.crypto.recommendation.model.TradingRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.crypto.recommendation.mapper.TradingRecordMapper.createTradingRecord;

/**
 *
 *
 * <h1>CsvReaderService</h1>
 *
 * Service to read and process CSV files containing trading data.
 * This service reads all CSV files from a specified directory, processes them line by line,
 * and creates TradingRecord objects from the CSV data.
 *
 * @author lioannidis
 * @version 0.1
 */
@Service
public class CsvReaderService {
    private static final Logger logger = LoggerFactory.getLogger(CsvReaderService.class);

    protected final List<TradingRecord> records = new ArrayList<>();

    /**
     * This method uses a resource pattern to identify CSV
     * files in the classpath and processes each one.
     *
     * @param folderPath the folder path where the CSV files are stored
     */
    public void readAllCsvFilesFromDirectory(String folderPath) {
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources("classpath:" + folderPath + "/*.csv");

            if (resources.length == 0) {
                logger.warn("No CSV files found in directory: {}", folderPath);
                return;
            }

            for (Resource resource : resources) {
                logger.info("Reading file: {}", resource.getFilename());
                processCsvFile(resource);
            }
        } catch (Exception e) {
            logger.error("Error reading CSV files from folder: {}", folderPath, e);
        }
    }

    /**
     * Processes a single CSV file and adds the TradingRecord objects to the records list.
     * Each line of the CSV file is converted to a TradingRecord.
     *
     * @param resource the CSV file resource to be processed
     * @throws Exception if an error occurs while reading the CSV file
     */
    private void processCsvFile(Resource resource) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            reader.readLine(); // Skip the header line

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                TradingRecord tradingRecord = createTradingRecord(values);
                records.add(tradingRecord);
            }
        }
    }
}

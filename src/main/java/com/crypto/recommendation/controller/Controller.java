package com.crypto.recommendation.controller;

import com.crypto.recommendation.dto.CryptoNormalizedRangeDTO;
import com.crypto.recommendation.dto.StatisticsDTO;
import com.crypto.recommendation.service.TradingStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Controller class that handles HTTP requests related to cryptocurrency trading.
 * This class also provides exception handling for invalid inputs.
 *
 * @author lioannidis
 * @version 0.1
 */
@RestController
@RequestMapping("/api")
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final TradingStatisticsService tradingStatisticsService;

    public Controller(TradingStatisticsService tradingStatisticsService) {
        this.tradingStatisticsService = tradingStatisticsService;
    }

    /**
     * Endpoint to get trading statistics for a given cryptocurrency symbol.
     *
     * @param symbol the cryptocurrency symbol
     * @return a {@link ResponseEntity} containing the statistics DTO, or a 404 if not found
     */
    @GetMapping("/{symbol}")
    public ResponseEntity<StatisticsDTO> getTradingStats(@PathVariable String symbol) {
        logger.info("Fetching trading statistics for symbol: {}", symbol);

        StatisticsDTO statisticsDTO = tradingStatisticsService.calculateStatistics(symbol);
        return (statisticsDTO != null) ? ResponseEntity.ok(statisticsDTO) : ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to get a list of all cryptocurrencies sorted by their normalized range desc.
     *
     * @return a list of {@link CryptoNormalizedRangeDTO} objects
     */
    @GetMapping("/normalized-range")
    public List<CryptoNormalizedRangeDTO> getNormalizedRangeDesc() {
        logger.info("Fetching sorted list of cryptocurrencies by normalized range.");
        return tradingStatisticsService.getNormalizedRangeDesc();
    }

    /**
     * Endpoint to get the cryptocurrency with the highest normalized range for a specific date.
     *
     * @param date the date in 'yyyy-MM-dd' format
     * @return a {@link ResponseEntity} containing the {@link CryptoNormalizedRangeDTO} for the
     * highest normalized range on that date, or a 404 if no data is found
     */
    @GetMapping("/normalized-by-date/{date}")
    public ResponseEntity<CryptoNormalizedRangeDTO> filterByDate(@PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            logger.info("Fetching crypto with highest normalized range for date: {}", parsedDate);

            return tradingStatisticsService.getCryptoWithHighestNormalizedRange(parsedDate)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format provided: {}", date, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CryptoNormalizedRangeDTO());
        }
    }
}

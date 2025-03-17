package com.crypto.recommendation.service;

import com.crypto.recommendation.dto.CryptoNormalizedRangeDTO;
import com.crypto.recommendation.dto.StatisticsDTO;
import com.crypto.recommendation.enums.SupportedCryptos;
import com.crypto.recommendation.model.TradingRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.crypto.recommendation.mapper.TradingRecordMapper.buildCryptoNormalizedRangeDTO;
import static com.crypto.recommendation.mapper.TradingRecordMapper.buildSymbolStatsDTO;
import static com.crypto.recommendation.util.TradingRecordUtil.filterBySymbol;

/**
 * Service to calculate trading statistics and normalized ranges for cryptocurrencies.
 *
 * @author lioannidis
 * @version 0.1
 */
@Service
public class TradingStatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(TradingStatisticsService.class);
    private final CsvReaderService csvReaderService;

    public TradingStatisticsService(CsvReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

    /**
     * Calculates statistics (min, max, oldest, newest prices) for a specific cryptocurrency.
     *
     * @param symbol the symbol of the cryptocurrency
     * @return the StatisticsDTO for the given symbol
     */
    public StatisticsDTO calculateStatistics(String symbol) {
        List<TradingRecord> symbolRecords = filterBySymbol(symbol, this.csvReaderService.records);

        if (symbolRecords.isEmpty()) {
            logger.warn("No records found for symbol: {}", symbol);
            return null;
        }

        return buildSymbolStatsDTO(symbol, symbolRecords);
    }

    /**
     * Retrieves the cryptocurrency with the highest normalized range for a specific day.
     *
     * @param date the date for which to calculate the highest normalized range
     * @return an Optional containing the CryptoNormalizedRangeDTO
     */
    public Optional<CryptoNormalizedRangeDTO> getCryptoWithHighestNormalizedRange(LocalDate date) {
        Map<String, List<TradingRecord>> groupedByCrypto = this.csvReaderService.records.stream()
                .filter(record -> record.getTimestamp().equals(date))
                .collect(Collectors.groupingBy(TradingRecord::getSymbol));

        return groupedByCrypto.entrySet().stream()
                .map(entry -> buildCryptoNormalizedRangeDTO(entry.getKey(), entry.getValue()))
                .filter(dto -> dto.getNormalizedRange().compareTo(BigDecimal.ZERO) > 0)
                .max(Comparator.comparing(CryptoNormalizedRangeDTO::getNormalizedRange));
    }

    /**
     * Retrieves a list of cryptocurrencies sorted by their normalized range
     * in descending order.
     *
     * @return a list of CryptoNormalizedRangeDTOs
     */
    public List<CryptoNormalizedRangeDTO> getNormalizedRangeDesc() {
        return Arrays.stream(SupportedCryptos.values())
                .map(coin -> {
                    List<TradingRecord> coinRecords = this.csvReaderService.records.stream()
                            .filter(record -> record.getSymbol().equals(coin.name()))
                            .collect(Collectors.toList());
                    return buildCryptoNormalizedRangeDTO(coin.name(), coinRecords);
                })
                .sorted(Comparator.comparing(CryptoNormalizedRangeDTO::getNormalizedRange).reversed())
                .collect(Collectors.toList());
    }

}

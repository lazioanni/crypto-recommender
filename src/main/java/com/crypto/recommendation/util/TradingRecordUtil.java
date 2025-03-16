package com.crypto.recommendation.util;

import com.crypto.recommendation.model.TradingRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class providing helper methods.
 *
 * @author lioannidis
 * @version 0.1
 */
public class TradingRecordUtil {

    /**
     * Finds the record with the minimum price.
     *
     * @param records the list of {@link TradingRecord} to search
     * @return the {@link TradingRecord} with the minimum price, or null
     */
    public static TradingRecord findMinPriceRecord(List<TradingRecord> records) {
        return records.stream()
                .min(Comparator.comparing(TradingRecord::getPrice))
                .orElse(null);
    }

    /**
     * Finds the record with the maximum price.
     *
     * @param records the list of {@link TradingRecord} to search
     * @return the {@link TradingRecord} with the maximum price, or null
     */
    public static TradingRecord findMaxPriceRecord(List<TradingRecord> records) {
        return records.stream()
                .max(Comparator.comparing(TradingRecord::getPrice))
                .orElse(null);
    }

    /**
     * Finds the oldest trading record.
     *
     * @param records the list of {@link TradingRecord} to search
     * @return the {@link TradingRecord} representing the oldest record, or null
     */
    public static TradingRecord findOldestRecord(List<TradingRecord> records) {
        return records.stream()
                .min(Comparator.comparing(TradingRecord::getTimestamp))
                .orElse(null);
    }

    /**
     * Finds the newest trading record.
     *
     * @param records the list of {@link TradingRecord} to search
     * @return the {@link TradingRecord} representing the newest record,  or null
     */
    public static TradingRecord findNewestRecord(List<TradingRecord> records) {
        return records.stream()
                .max(Comparator.comparing(TradingRecord::getTimestamp))
                .orElse(null);
    }

    /**
     * Calculates the normalized range of prices.
     *
     * @param records the list of {@link TradingRecord} to process
     * @return the calculated normalized range, or BigDecimal ZERO
     */
    public static BigDecimal findNormalizedRange(List<TradingRecord> records) {
        if (records == null || records.isEmpty()) {
            return BigDecimal.ZERO;
        }

        TradingRecord minRecord = findMinPriceRecord(records);
        TradingRecord maxRecord = findMaxPriceRecord(records);

        if (minRecord == null || maxRecord == null || minRecord.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal minPrice = minRecord.getPrice();
        BigDecimal maxPrice = maxRecord.getPrice();

        return maxPrice.subtract(minPrice).divide(minPrice, 10, RoundingMode.HALF_UP);
    }

    /**
     * Filters a list of trading records based on the provided symbol.
     *
     * @param symbol the symbol to filter
     * @param records the list of {@link TradingRecord} to filter
     * @return a list of {@link TradingRecord}
     */
    public static List<TradingRecord> filterBySymbol(String symbol, List<TradingRecord> records) {
        return records.stream()
                .filter(record -> record.getSymbol().equalsIgnoreCase(symbol))
                .collect(Collectors.toList());
    }
}

package com.crypto.recommendation.mapper;

import com.crypto.recommendation.dto.CryptoNormalizedRangeDTO;
import com.crypto.recommendation.dto.StatisticsDTO;
import com.crypto.recommendation.model.TradingRecord;

import java.math.BigDecimal;
import java.util.List;

import static com.crypto.recommendation.util.DateUtil.convertToLocalDate;
import static com.crypto.recommendation.util.TradingRecordUtil.findMaxPriceRecord;
import static com.crypto.recommendation.util.TradingRecordUtil.findMinPriceRecord;
import static com.crypto.recommendation.util.TradingRecordUtil.findNewestRecord;
import static com.crypto.recommendation.util.TradingRecordUtil.findNormalizedRange;
import static com.crypto.recommendation.util.TradingRecordUtil.findOldestRecord;

/**
 * Utility class for mapping TradingRecord objects.
 *
 * @author lioannidis
 * @version 0.1
 */
public class TradingRecordMapper {

    /**
     * Builds a {@link StatisticsDTO} from the given symbol and list of {@link TradingRecord} objects.
     * *
     * @param symbol the cryptocurrency symbol
     * @param records the list of {@link TradingRecord} objects
     * @return a {@link StatisticsDTO}
     */
    public static StatisticsDTO buildSymbolStatsDTO(String symbol, List<TradingRecord> records) {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setSymbol(symbol);
        statisticsDTO.setMinPrice(findMinPriceRecord(records).getPrice());
        statisticsDTO.setMaxPrice(findMaxPriceRecord(records).getPrice());
        statisticsDTO.setNewest(findNewestRecord(records).getTimestamp());
        statisticsDTO.setOldest(findOldestRecord(records).getTimestamp());
        return statisticsDTO;
    }

    /**
     * Builds a {@link CryptoNormalizedRangeDTO} from the given symbol and list of {@link TradingRecord} objects.
     *
     * @param symbol the cryptocurrency symbol
     * @param records the list of {@link TradingRecord} objects
     * @return a {@link CryptoNormalizedRangeDTO}
     */
    public static CryptoNormalizedRangeDTO buildCryptoNormalizedRangeDTO(String symbol, List<TradingRecord> records) {
        CryptoNormalizedRangeDTO cryptoNormalizedRangeDTO = new CryptoNormalizedRangeDTO();
        cryptoNormalizedRangeDTO.setSymbol(symbol);
        cryptoNormalizedRangeDTO.setNormalizedRange(findNormalizedRange(records));
        return cryptoNormalizedRangeDTO;
    }

    /**
     * Creates a {@link TradingRecord} object from a CSV row.
     *
     * @param values an array of string values representing a CSV row (timestamp, symbol, price)
     * @return a {@link TradingRecord}
     */
    public static TradingRecord createTradingRecord(String[] values) {
        String timestamp = values[0].trim();
        String symbol = values[1].trim();
        BigDecimal price = new BigDecimal(values[2].trim());

        TradingRecord tradingRecord = new TradingRecord();
        tradingRecord.setTimestamp(convertToLocalDate(timestamp));
        tradingRecord.setSymbol(symbol);
        tradingRecord.setPrice(price);

        return tradingRecord;
    }
}

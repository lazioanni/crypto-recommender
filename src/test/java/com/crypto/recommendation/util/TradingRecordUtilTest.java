package com.crypto.recommendation.util;

import com.crypto.recommendation.model.TradingRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TradingRecordUtilTest {
    private static List<TradingRecord> tradingRecords;

    @BeforeAll
    public static void init() {
        tradingRecords = new ArrayList<>();
        TradingRecord tradingRecordBTC = new TradingRecord();
        tradingRecordBTC.setTimestamp(LocalDate.of(2025,3,1));
        tradingRecordBTC.setSymbol("BTC");
        tradingRecordBTC.setPrice(new BigDecimal(100));
        tradingRecords.add(tradingRecordBTC);
        TradingRecord tradingRecordETH = new TradingRecord();
        tradingRecordETH.setTimestamp(LocalDate.of(2025,2,1));
        tradingRecordETH.setSymbol("ETH");
        tradingRecordETH.setPrice(new BigDecimal(200));
        tradingRecords.add(tradingRecordETH);
        TradingRecord tradingRecordXRP = new TradingRecord();
        tradingRecordXRP.setTimestamp(LocalDate.of(2025,1,1));
        tradingRecordXRP.setSymbol("XRP");
        tradingRecordXRP.setPrice(new BigDecimal(300));
        tradingRecords.add(tradingRecordXRP);
    }
    @Test
    void findMinPriceRecord() {
        final var expectedRecord = "BTC";

        final var result  = TradingRecordUtil.findMinPriceRecord(tradingRecords);

        assertThat(result).isNotNull()
                .extracting(TradingRecord::getSymbol)
                .isEqualTo(expectedRecord);
    }

    @Test
    void findMaxPriceRecord() {
        final var expectedRecord = "XRP";

        final var result  = TradingRecordUtil.findMaxPriceRecord(tradingRecords);

        assertThat(result).isNotNull()
                .extracting(TradingRecord::getSymbol)
                .isEqualTo(expectedRecord);
    }

    @Test
    void findOldestRecord() {
        final var expectedRecord = "XRP";

        final var result  = TradingRecordUtil.findOldestRecord(tradingRecords);

        assertThat(result).isNotNull()
                .extracting(TradingRecord::getSymbol)
                .isEqualTo(expectedRecord);
    }

    @Test
    void findNewestRecord() {
        final var expectedRecord = "BTC";

        final var result  = TradingRecordUtil.findNewestRecord(tradingRecords);

        assertThat(result).isNotNull()
                .extracting(TradingRecord::getSymbol)
                .isEqualTo(expectedRecord);
    }

    @Test
    void findNormalizedRange() {
        final var expectedResult = new BigDecimal("2.0000000000");

        final var result  = TradingRecordUtil.findNormalizedRange(tradingRecords);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void filterBySymbol() {
        final var filter = "BTC";

        final var result  = TradingRecordUtil.filterBySymbol(filter,tradingRecords);

        assertThat(result).isNotNull().first()
                .extracting(TradingRecord::getSymbol)
                .isEqualTo(filter);
    }
}
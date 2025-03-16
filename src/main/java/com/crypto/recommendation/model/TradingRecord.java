package com.crypto.recommendation.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * TradingRecord represents a record of a cryptocurrency trade.
 *
 * @author lioannidis
 * @version 0.1
 */
@Data
public class TradingRecord {
    private LocalDate timestamp;
    private String symbol;
    private BigDecimal price;
}

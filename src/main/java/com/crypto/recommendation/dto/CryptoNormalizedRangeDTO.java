package com.crypto.recommendation.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * CryptoNormalizedRangeDTO is a DTO that represents
 * the normalized range of a cryptocurrency.
 *
 * @author lioannidis
 * @version 0.1
 */
@Data
public class CryptoNormalizedRangeDTO {
    private String symbol;
    private BigDecimal normalizedRange;
}
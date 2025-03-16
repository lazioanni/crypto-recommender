package com.crypto.recommendation.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * StatisticsDTO is a DTO that holds statistical data for a cryptocurrency.
 *
 * @author lioannidis
 * @version 0.1
 */

@Data
public class StatisticsDTO {
    private String symbol;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private LocalDate oldest;
    private LocalDate newest;
}
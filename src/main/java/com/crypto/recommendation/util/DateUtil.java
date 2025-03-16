package com.crypto.recommendation.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;


/**
 * Utility class for date operations.
 *
 * @author lioannidis
 * @version 0.1
 */
public class DateUtil {

    /**
     * Converts a timestamp to a {@link LocalDate} object.

     * @param timestamp the timestamp in milliseconds
     * @return the corresponding {@link LocalDate} object
     * @throws NumberFormatException if the provided timestamp is not a valid number
     */
    public static LocalDate convertToLocalDate(String timestamp) {
        long timestampInMillis = Long.parseLong(timestamp);
        return Instant.ofEpochMilli(timestampInMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}

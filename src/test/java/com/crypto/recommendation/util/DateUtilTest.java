package com.crypto.recommendation.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateUtilTest {

    @Test
    void convertToLocalDate() {
        final var timestamp = "1641031200000";
        final var expectedDate = "2022-01-01";

        final var result  = DateUtil.convertToLocalDate(timestamp);

        assertThat(result).isEqualTo(expectedDate);
    }

    @Test
    void convertToLocalDateWrongFormat_throws_NumberFormatException() {
        final var timestamp = "164103120000a";

        assertThatThrownBy(() -> DateUtil.convertToLocalDate(timestamp))
                .isInstanceOf(NumberFormatException.class);
    }
}
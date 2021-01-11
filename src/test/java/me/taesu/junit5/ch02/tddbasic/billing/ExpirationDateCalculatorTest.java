package me.taesu.junit5.ch02.tddbasic.billing;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by itaesu on 2021/01/11.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
class ExpirationDateCalculatorTest {
    private ExpirationDateCalculator expirationDateCalculator = new ExpirationDateCalculator();

    @Test
    public void shouldMatchExpirationDate_whenPay10000() throws Exception {
        //given
        final int payAmount = 10000;
        LocalDate payDate = LocalDate.of(2020, 1, 1);
        LocalDate expectedExpirationDate = LocalDate.of(2020, 2, 1);

        //when
        LocalDate result = expirationDateCalculator.calculate(PayData.builder()
                                                                     .payDate(payDate)
                                                                     .payAmount(payAmount)
                                                                     .build());

        //then
        assertEquals(expectedExpirationDate, result);

        //given
        payDate = LocalDate.of(2020, 1, 31);
        expectedExpirationDate = LocalDate.of(2020, 2, 29);

        //when
        result = expirationDateCalculator.calculate(PayData.builder()
                                                           .payDate(payDate)
                                                           .payAmount(payAmount)
                                                           .build());

        //then
        assertEquals(expectedExpirationDate, result);
    }

    @Test
    public void shouldMatchExpirationDate_whenPay30000() throws Exception {
        //given
        final int payAmount = 30000;
        LocalDate payDate = LocalDate.of(2020, 1, 1);
        LocalDate expectedExpirationDate = LocalDate.of(2020, 4, 1);

        //when
        LocalDate result = expirationDateCalculator.calculate(PayData.builder()
                                                                     .payDate(payDate)
                                                                     .payAmount(payAmount)
                                                                     .build());

        //then
        assertEquals(expectedExpirationDate, result);

        //given
        payDate = LocalDate.of(2020, 1, 31);
        expectedExpirationDate = LocalDate.of(2020, 4, 30);

        //when
        result = expirationDateCalculator.calculate(PayData.builder()
                                                           .payDate(payDate)
                                                           .payAmount(payAmount)
                                                           .build());

        //then
        assertEquals(expectedExpirationDate, result);
    }

    @Test
    public void shouldMatchExpirationDate_whenPayBetweenFirstPayDateAndExpirationDate() throws Exception {
        //given
        LocalDate lastPayDate = LocalDate.of(2020, 1, 1);
        LocalDate payDate = LocalDate.of(2020, 1, 29);
        final int payAmount = 30000;
        final PayData payData = PayData.builder()
                                       .lastPayDate(lastPayDate)
                                       .payDate(payDate)
                                       .payAmount(payAmount)
                                       .build();

        LocalDate expectedExpirationDate = LocalDate.of(2020, 5, 1);

        //when
        final var result = expirationDateCalculator.calculate(payData);

        //then
        assertEquals(expectedExpirationDate, result);
    }

    @Test
    public void shouldMatchExpirationDate_whenPayAtExpirationDate() throws Exception {
        //given
        LocalDate lastPayDate = null;
        LocalDate payDate = LocalDate.of(2020, 2, 1);
        final int payAmount = 30000;
        final PayData payData = PayData.builder()
                                       .lastPayDate(lastPayDate)
                                       .payDate(payDate)
                                       .payAmount(payAmount)
                                       .build();

        LocalDate expectedExpirationDate = LocalDate.of(2020, 5, 1);

        //when
        final var result = expirationDateCalculator.calculate(payData);

        //then
        assertEquals(expectedExpirationDate, result);
    }

    @Test
    public void shouldMatchExpirationDate_whenPayAt10Months() throws Exception {
        //given
        LocalDate lastPayDate = LocalDate.of(2020, 1, 30);
        LocalDate payDate = LocalDate.of(2020, 2, 1);
        final int payAmount = 100000;
        final PayData payData = PayData.builder()
                                       .lastPayDate(lastPayDate)
                                       .payDate(payDate)
                                       .payAmount(payAmount)
                                       .build();

        LocalDate expectedExpirationDate = LocalDate.of(2021, 2, 28);

        //when
        final var result = expirationDateCalculator.calculate(payData);

        //then
        assertEquals(expectedExpirationDate, result);
    }

    @Test
    public void shouldMatchPayMonth() throws Exception {
        assertEquals(expirationDateCalculator.getPayMonth(10000 * 8), 8);
        assertEquals(expirationDateCalculator.getPayMonth(10000 * 10), 12);
        assertEquals(expirationDateCalculator.getPayMonth(10000 * 14), 12 + 4);
        assertEquals(expirationDateCalculator.getPayMonth(10000 * 22), 24 + 2);
    }
}
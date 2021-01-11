package me.taesu.junit5.billing;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

/**
 * Created by itaesu on 2021/01/11.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
public class ExpirationDateCalculator {

    public LocalDate calculate(PayData payData) {
        final var baseDate = Optional.ofNullable(payData.getLastPayDate()).orElse(payData.getPayDate());
        final var months = Period.between(baseDate, payData.getPayDate()).getMonths()
                + (!baseDate.equals(payData.getPayDate()) ? 1 : 0);

        int monthDiff = months + getPayMonth(payData.getPayAmount());
        return baseDate.plusMonths(monthDiff);
    }

    int getPayMonth(int payAmount) {
        final var payMonth = payAmount / 10000;
        return (payMonth / 10) * 12 + (payMonth % 10);
    }
}

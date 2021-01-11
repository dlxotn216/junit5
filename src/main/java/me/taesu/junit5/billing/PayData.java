package me.taesu.junit5.billing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by itaesu on 2021/01/11.
 *
 * @author Lee Tae Su
 * @version 1.2.0
 * @since 1.2.0
 */
@Getter @NoArgsConstructor @Builder @AllArgsConstructor
public class PayData {
    private LocalDate lastPayDate;
    private LocalDate payDate;
    private int payAmount;
}

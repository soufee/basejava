package ru.shoma.webapp.util;

import java.time.LocalDate;
import java.time.Month;

/**
 * Created by Shoma on 11.03.2018.
 */
public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1,1);
    public static LocalDate of (int year, Month month){
        return LocalDate.of(year, month, 1);
    }
}

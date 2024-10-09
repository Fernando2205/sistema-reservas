package com.usbcali.reservas.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class DateUtil {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static final List<LocalDate> FECHAS_CONCIERTO = Arrays.asList(
            LocalDate.of(2024,10,4),
            LocalDate.of(2024,10,11),
            LocalDate.of(2024,10,18),
            LocalDate.of(2024,10,25)
    );
}

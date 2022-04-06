package com.gmail.puhovashablinskaya.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Service
public class DateTimeServiceImpl implements DateTimeService {
    @Value("${format.date.time}")
    private String formatDateTime;

    @Value("${time.zone}")
    private String timeZone;

    @Override
    public LocalDateTime currentTimeDate() {
        return dataTimeWithZone(timeZone);
    }

    @Override
    public String currentTimeDateFormat() {
        LocalDateTime localDateTime = dataTimeWithZone(timeZone);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(formatDateTime);
        return localDateTime.format(timeFormat);
    }

    @Override
    public LocalDate currentDate() {
        LocalDateTime dateTime = dataTimeWithZone(timeZone);
        return dateTime.toLocalDate();
    }

    private LocalDateTime dataTimeWithZone(String timeZone) {
        Instant instant = Instant.now();
        ZoneId minskEurope = ZoneId.of(timeZone);
        ZonedDateTime nowZonedDateTime = ZonedDateTime.ofInstant(instant, minskEurope);
        return nowZonedDateTime.toLocalDateTime();
    }
}

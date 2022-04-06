package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.service.DataTimeService;
import com.gmail.puhovashablinskaya.service.config.ServiceConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class DataTimeServiceImpl implements DataTimeService {
    private final ServiceConfig serviceConfig;

    @Override
    public LocalDateTime currentTimeDate() {
        return dataTimeWithZone(serviceConfig.getTimeZone());
    }

    @Override
    public String currentTimeDateFormat() {
        LocalDateTime localDateTime = currentTimeDate();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(serviceConfig.getFormatDate());
        return localDateTime.format(timeFormat);
    }

    private LocalDateTime dataTimeWithZone(String timeZone) {
        Instant instant = Instant.now();
        ZoneId minskEurope = ZoneId.of(timeZone);
        ZonedDateTime nowZonedDateTime = ZonedDateTime.ofInstant(instant, minskEurope);
        return nowZonedDateTime.toLocalDateTime();
    }
}

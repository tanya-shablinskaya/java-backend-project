package com.gmail.puhovashablinskaya.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface DateTimeService {
    LocalDateTime currentTimeDate();

    String currentTimeDateFormat();

    LocalDate currentDate();
}

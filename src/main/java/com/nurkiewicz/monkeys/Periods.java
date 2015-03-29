package com.nurkiewicz.monkeys;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;

public class Periods {

    /**
     * Warning: only estimate.
     */
    public static Duration toDuration(Period period) {
        final ZonedDateTime now = ZonedDateTime.now();
        return Duration.between(now, now.plus(period));
    }

}

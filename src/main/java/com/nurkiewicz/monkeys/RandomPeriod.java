package com.nurkiewicz.monkeys;

import com.nurkiewicz.monkeys.Periods;
import lombok.Value;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Value
public class RandomPeriod {

    private static final Random RANDOM = new Random();

    Period expected;
    Period stdDev;

    public Duration make() {
        final long shift = Periods.toDuration(expected).toMillis();
        final long stdDev = Periods.toDuration(this.stdDev).toMillis();
        final double gaussian = RANDOM.nextGaussian() * stdDev;
        double randomMillis = shift + gaussian;
        return Duration.ofMillis((long) randomMillis);
    }

}

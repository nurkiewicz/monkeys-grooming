package com.nurkiewicz.monkeys.simulation;

import java.time.*;
import java.time.temporal.TemporalAmount;

public class SimulationClock extends Clock {

    private Instant simulationNow = Instant.now();
    public ZoneId getZone() {
        return ZoneId.systemDefault();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return this;
    }

    @Override
    public Instant instant() {
        return simulationNow;
    }

    public void advance(TemporalAmount amount) {
        simulationNow = simulationNow.plus(amount);
    }

    public void advanceTo(Instant instant) {
        simulationNow = instant;
    }

}

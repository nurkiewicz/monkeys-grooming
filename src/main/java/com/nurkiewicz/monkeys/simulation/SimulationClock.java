package com.nurkiewicz.monkeys.simulation;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

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

    public void advanceTo(Instant instant) {
        simulationNow = instant;
    }

}

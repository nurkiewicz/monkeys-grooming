package com.nurkiewicz.monkeys.actions;

import com.google.common.collect.ImmutableSet;
import com.nurkiewicz.monkeys.simulation.Planner;

import java.time.Clock;
import java.time.Duration;
import java.util.Set;

public class Stop extends Action {

    private final Planner planner;

    public Stop(Clock simulationTime, Duration delay, Planner planner) {
        super(simulationTime, delay);
        this.planner = planner;
    }

    @Override
    public void run() {
        planner.stop();
    }
}

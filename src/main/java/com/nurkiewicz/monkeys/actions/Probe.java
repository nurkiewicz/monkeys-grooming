package com.nurkiewicz.monkeys.actions;

import com.google.common.collect.ImmutableSet;
import com.nurkiewicz.monkeys.simulation.Population;
import com.nurkiewicz.monkeys.simulation.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Duration;
import java.util.Set;

public class Probe extends Action {

    private static final Logger log = LoggerFactory.getLogger(Probe.class);
    private final Population population;

    public Probe(Clock simulationTime, Duration delay, Population population) {
        super(simulationTime, delay);
        this.population = population;
    }

    @Override
    public void run() {
        final Statistics stats = population.statistics();
        log.info("At {} population: {}", getSchedule(), stats);
    }
}

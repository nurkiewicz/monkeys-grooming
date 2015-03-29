package com.nurkiewicz.monkeys.simulation;

import com.nurkiewicz.monkeys.Periods;
import com.nurkiewicz.monkeys.actions.Probe;
import com.nurkiewicz.monkeys.actions.Stop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Duration;
import java.util.stream.IntStream;

public class Simulation implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Simulation.class);

    private final Planner planner;

    public Simulation(Planner planner, Clock clock, Environment environment, Population population) {
        this.planner = planner;
        final Duration simulationLength = Periods.toDuration(environment.getSimulationLength());
        final Stop action = new Stop(clock, simulationLength, this.planner);
        this.planner.schedule(action);
        scheduleProbes(clock, population, simulationLength, 5_000);
    }

    private void scheduleProbes(Clock clock, Population population, Duration simulationLength, int howMany) {
        IntStream
                .range(0, howMany)
                .mapToObj(idx -> simulationLength.multipliedBy(idx).dividedBy(howMany))
                .forEach(delay -> this.planner.schedule(new Probe(clock, delay, population)));
    }

    @Override
    public void run() {
        log.info("Starting simulation");
        planner.run();
        log.info("Simulation done");
    }
}

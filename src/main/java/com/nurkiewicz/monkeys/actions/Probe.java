package com.nurkiewicz.monkeys.actions;

import com.nurkiewicz.monkeys.behaviours.Cheater;
import com.nurkiewicz.monkeys.behaviours.Grudger;
import com.nurkiewicz.monkeys.behaviours.Sucker;
import com.nurkiewicz.monkeys.simulation.Population;
import com.nurkiewicz.monkeys.simulation.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.Duration;

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
        final Integer grudgers = stats.getPerType().getOrDefault(Grudger.class.getSimpleName(), 0);
        final Integer suckers = stats.getPerType().getOrDefault(Sucker.class.getSimpleName(), 0);
        final Integer cheaters = stats.getPerType().getOrDefault(Cheater.class.getSimpleName(), 0);
        System.out.println(grudgers + "\t" + suckers + "\t" + cheaters);
    }
}

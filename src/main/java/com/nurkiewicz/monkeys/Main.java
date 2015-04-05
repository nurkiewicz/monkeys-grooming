package com.nurkiewicz.monkeys;

import com.google.common.collect.ImmutableMap;
import com.nurkiewicz.monkeys.behaviours.*;
import com.nurkiewicz.monkeys.simulation.*;

import java.time.Period;

public class Main {

    public static void main(String[] args) {
        System.out.println("Cheaters\tGrudgers\tSuckers");
        final SimulationClock clock = new SimulationClock();
        final Environment environment = environment();
        final MonkeyFactory monkeyFactory = new MonkeyFactory(clock, environment);
        final Planner planner = new Planner(clock, environment);
        final Population population = new Population(planner, monkeyFactory, environment);
        final Simulation simulation = new Simulation(planner, clock, environment, population);
        simulation.run();
    }

    private static Environment environment() {
        return Environment.builder().
                initialCount(initialCount()).
                breeding(new RandomPeriod(Period.ofYears(4), Period.ofMonths(6))).
                lifetime(new RandomPeriod(Period.ofYears(10), Period.ofMonths(12))).
                cheaterAcceptProbability(0.5).
                deathByParasite(new RandomPeriod(Period.ofWeeks(4), Period.ofDays(2))).
                dieDueToGroomingProbability(0.001).
                maxChildren(4).
                maxPopulationSize(1_000).
                mutationProbability(0.05).
                parasiteInfection(new RandomPeriod(Period.ofDays(7), Period.ofDays(1))).
                simulationLength(Period.ofYears(10_000)).
                build();
    }

    private static ImmutableMap<Class<? extends Monkey>, Integer> initialCount() {
        return ImmutableMap.of(
                Cheater.class, 0,
                Grudger.class, 5,
                Sucker.class, 5
        );
    }

}

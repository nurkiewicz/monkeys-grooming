package com.nurkiewicz.monkeys;

import com.google.common.collect.ImmutableMap;
import com.nurkiewicz.monkeys.behaviours.*;
import com.nurkiewicz.monkeys.simulation.*;

import java.time.Period;

public class Main {

    public static void main(String[] args) {
        final SimulationClock clock = new SimulationClock();
        final Environment environment = environment();
        final MonkeyFactory monkeyFactory = new MonkeyFactory(clock);
        final Planner planner = new Planner(clock);
        final Population population = new Population(planner, monkeyFactory, environment);
        final Simulation simulation = new Simulation(planner, clock, environment, population);
        simulation.run();
    }

    private static Environment environment() {
        final ImmutableMap<Class<? extends Monkey>, Integer> populationCount = ImmutableMap.of(
                Sucker.class, 100,
                Cheat.class, 0,
                Grudger.class, 100
        );
        final RandomPeriod lifetime = new RandomPeriod(Period.ofYears(10), Period.ofMonths(12));
        final RandomPeriod breeding = new RandomPeriod(Period.ofYears(4), Period.ofMonths(6));
        final RandomPeriod parasiteDeath = new RandomPeriod(Period.ofWeeks(4), Period.ofDays(2));
        final RandomPeriod parasiteInfection = new RandomPeriod(Period.ofDays(7), Period.ofDays(1));
        return new Environment(
                populationCount, 0.01, lifetime, breeding, parasiteDeath, parasiteInfection, Period.ofYears(10_000), 10_000, 4);
    }

}

package com.nurkiewicz.monkeys.actions;

import com.google.common.collect.ImmutableSet;
import com.nurkiewicz.monkeys.behaviours.Monkey;
import com.nurkiewicz.monkeys.simulation.Population;

import java.time.Duration;

public class AskForGrooming extends MonkeyAction {
    private final Population population;

    public AskForGrooming(Monkey monkey, Duration delay, Population population) {
        super(monkey, delay);
        this.population = population;
    }

    @Override
    public void run(Monkey monkey) {
        monkey.infect();
        population.askForGrooming(monkey);
    }
}

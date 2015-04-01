package com.nurkiewicz.monkeys.actions;

import com.nurkiewicz.monkeys.behaviours.Monkey;
import com.nurkiewicz.monkeys.simulation.Population;

import java.time.Duration;

public class Kill extends MonkeyAction {
    private final Population population;

    public Kill(Monkey monkey, Duration lifetime, Population population) {
        super(monkey, lifetime);
        this.population = population;
    }

    @Override
    public void run(Monkey monkey) {
        population.kill(monkey);
    }
}

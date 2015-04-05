package com.nurkiewicz.monkeys.actions;

import com.nurkiewicz.monkeys.behaviours.Monkey;
import com.nurkiewicz.monkeys.simulation.Population;

import java.time.Duration;

public class Breed extends MonkeyAction {
    private final Population population;

    public Breed(Monkey monkey, Duration delay, Population population) {
        super(monkey, delay);
        this.population = population;
    }

    @Override
    public void run(Monkey monkey) {
        population.breed(monkey);
    }
}

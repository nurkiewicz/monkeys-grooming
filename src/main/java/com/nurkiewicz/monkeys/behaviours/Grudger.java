package com.nurkiewicz.monkeys.behaviours;

import java.time.Clock;
import java.util.HashSet;
import java.util.Set;

public class Grudger extends Monkey {

    private final Set<Monkey> cheaters = new HashSet<>();

    public Grudger(Clock simulationTime) {
        super(simulationTime);
    }

    @Override
    public boolean acceptsToGroom(Monkey monkey) {
        return !cheaters.contains(monkey);
    }

    @Override
    public void monkeyRejectedToGroomMe(Monkey monkey) {
        cheaters.add(monkey);
    }

}

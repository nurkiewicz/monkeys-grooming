package com.nurkiewicz.monkeys.behaviours;

import com.nurkiewicz.monkeys.behaviours.Monkey;

import java.time.Clock;
import java.util.HashSet;
import java.util.Set;

public class Grudger extends Monkey {

    private final Set<Monkey> cheats = new HashSet<>();

    public Grudger(Clock simulationTime) {
        super(simulationTime);
    }

    @Override
    public boolean acceptsToGroom(Monkey monkey) {
        return !cheats.contains(monkey);
    }

    @Override
    public void monkeyRejectedToGroomMe(Monkey monkey) {
        cheats.add(monkey);
    }

}

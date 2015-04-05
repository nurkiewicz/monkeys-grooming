package com.nurkiewicz.monkeys.behaviours;

import java.time.Clock;

public class Cheater extends Monkey {

    private final double acceptProbability;

    public Cheater(Clock simulationTime, double acceptProbability) {
        super(simulationTime);
        this.acceptProbability = acceptProbability;
    }

    @Override
    public boolean acceptsToGroom(Monkey monkey) {
        return Math.random() < acceptProbability;
    }
}

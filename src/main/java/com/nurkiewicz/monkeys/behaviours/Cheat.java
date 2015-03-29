package com.nurkiewicz.monkeys.behaviours;

import java.time.Clock;

public class Cheat extends Monkey {

    public Cheat(Clock simulationTime) {
        super(simulationTime);
    }

    @Override
    public boolean acceptsToGroom(Monkey monkey) {
        return false;
    }
}

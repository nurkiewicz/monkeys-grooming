package com.nurkiewicz.monkeys.behaviours;

import java.time.Clock;

public class Sucker extends Monkey {
    public Sucker(Clock simulationTime) {
        super(simulationTime);
    }

    @Override
    public boolean acceptsToGroom(Monkey monkey) {
        return true;
    }

}

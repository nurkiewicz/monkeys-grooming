package com.nurkiewicz.monkeys.behaviours;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public abstract class Monkey {

    private final Instant birth;
    private boolean dead;
    private boolean hasParasite;
    private Clock simulationTime;

    public Monkey(Clock simulationTime) {
        this.simulationTime = simulationTime;
        birth = simulationTime.instant();
    }

    public void kill() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public Duration ageDifferenceBetween(Monkey monkey) {
        return Duration.between(this.birth, monkey.birth).abs();
    }

    public Clock getSimulationTime() {
        return simulationTime;
    }

    public void infect() {
        this.hasParasite = true;
    }

    public boolean hasParasite() {
        return hasParasite;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + this.hashCode() + ")";
    }

    public boolean groomedBy(Monkey groomMe) {
        if (groomMe.acceptsToGroom(this)) {
            hasParasite = false;
            return true;
        } else {
            monkeyRejectedToGroomMe(groomMe);
            return false;
        }
    }

    public abstract boolean acceptsToGroom(Monkey monkey);

    public void monkeyRejectedToGroomMe(Monkey monkey) {
        //no-op
    }
}

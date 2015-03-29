package com.nurkiewicz.monkeys.actions;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.Set;

public abstract class Action implements Comparable<Action> {

    private final Instant schedule;

    public Action(Clock simulationTime, Duration delay) {
        this.schedule = simulationTime.instant().plus(delay);
    }

    @Override
    public int compareTo(Action other) {
        return this.schedule.compareTo(other.schedule);
    }

    public abstract void run();

    public Instant getSchedule() {
        return schedule;
    }
}

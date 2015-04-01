package com.nurkiewicz.monkeys.actions;

import com.google.common.base.MoreObjects;
import com.nurkiewicz.monkeys.behaviours.Monkey;

import java.time.Duration;

public abstract class MonkeyAction extends Action {
    private final Monkey monkey;

    public MonkeyAction(Monkey monkey, Duration delay) {
        super(monkey.getSimulationTime(), delay);
        this.monkey = monkey;
    }

    public Monkey getMonkey() {
        return monkey;
    }

    @Override
    public final void run() {
        if (!monkey.isDead()) {
            run(monkey);
        }
    }

    public abstract void run(Monkey monkey);

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("monkey", monkey)
                .add("schedule", getSchedule())
                .toString();
    }
}

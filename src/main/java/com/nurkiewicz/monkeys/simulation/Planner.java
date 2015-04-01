package com.nurkiewicz.monkeys.simulation;

import com.nurkiewicz.monkeys.actions.*;
import com.nurkiewicz.monkeys.behaviours.Monkey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.PriorityQueue;
import java.util.Queue;

public class Planner implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Planner.class);

    private final Queue<Action> pending = new PriorityQueue<>();
    private final SimulationClock simulationClock;
    private long totalExecuted = 0;

    public Planner(SimulationClock simulationClock) {
        this.simulationClock = simulationClock;
    }

    public void schedule(Action action) {
        pending.add(action);
    }

    @Override
    public void run() {
        while (!pending.isEmpty()) {
            Action nearestAction = pending.poll();
            log.debug("Running action {} at {}", nearestAction, nearestAction.getSchedule());
            simulationClock.advanceTo(nearestAction.getSchedule());
            nearestAction.run();
            ++totalExecuted;
        }
    }

    public int queueSize() {
        return pending.size();
    }

    public long totalExecuted() {
        return totalExecuted;
    }

    public void stop() {
        log.info("Stopping simulation");
        pending.clear();
    }

    public Duration cancelKill(Monkey monkey) {
        final MonkeyAction killAction = pending
                .stream()
                .filter(action -> action instanceof Kill)
                .map(action -> (MonkeyAction) action)
                .filter(action -> action.getMonkey().equals(monkey))
                .findAny()
                .get();
        pending.remove(killAction);
        return Duration.between(simulationClock.instant(), killAction.getSchedule());
    }

    void kill(Monkey child, Duration lifetime, Population population) {
        schedule(new Kill(child, lifetime, population));
    }

    void breed(Monkey child, Duration breeding, Population population) {
        schedule(new Breed(child, breeding, population));
    }

    void askForGrooming(Monkey child, Duration parasiteInfection, Population population) {
        schedule(new AskForGrooming(child, parasiteInfection, population));
    }
}

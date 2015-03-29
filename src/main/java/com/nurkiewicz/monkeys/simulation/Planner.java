package com.nurkiewicz.monkeys.simulation;

import com.nurkiewicz.monkeys.actions.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

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
}

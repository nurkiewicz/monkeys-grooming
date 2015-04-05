package com.nurkiewicz.monkeys.simulation;

import com.nurkiewicz.monkeys.actions.*;
import com.nurkiewicz.monkeys.behaviours.Monkey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.stream.IntStream;

public class Planner implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Planner.class);
    private static final Random RANDOM = new Random();

    private final Queue<Action> pending = new PriorityQueue<>();
    private final SimulationClock simulationClock;
    private final Environment environment;
    private long totalExecuted = 0;

    public Planner(SimulationClock simulationClock, Environment environment) {
        this.simulationClock = simulationClock;
        this.environment = environment;
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

    void scheduleMonkeyLifecycle(Monkey child, Population population) {
        askForGrooming(child, environment.getParasiteInfection().make(), population);
        scheduleBreedings(child, population);
        kill(child, environment.getLifetime().make(), population);
    }

    void askForGrooming(Monkey child, Duration parasiteInfection, Population population) {
        schedule(new AskForGrooming(child, parasiteInfection, population));
    }

    private void scheduleBreedings(Monkey child, Population population) {
        final int childrenCount = RANDOM.nextInt(environment.getMaxChildren() + 1);
        IntStream.
                rangeClosed(1, childrenCount)
                .forEach(x -> breed(child, environment.getBreeding().make(), population));
    }

    void kill(Monkey child, Duration lifetime, Population population) {
        schedule(new Kill(child, lifetime, population));
    }

    private void breed(Monkey child, Duration breeding, Population population) {
        schedule(new Breed(child, breeding, population));
    }

    void killByParasite(Monkey monkey, Population population) {
        final Duration parasiteDeathDelay = environment.getDeathByParasite().make();
        schedule(new KillByParasite(monkey, parasiteDeathDelay, population));
    }
}

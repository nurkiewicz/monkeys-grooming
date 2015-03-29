package com.nurkiewicz.monkeys.simulation;

import com.google.common.collect.ImmutableMap;
import com.nurkiewicz.monkeys.actions.AskForGrooming;
import com.nurkiewicz.monkeys.actions.Breed;
import com.nurkiewicz.monkeys.actions.Kill;
import com.nurkiewicz.monkeys.actions.KillByParasite;
import com.nurkiewicz.monkeys.behaviours.Monkey;
import com.nurkiewicz.monkeys.behaviours.MonkeyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class Population {

    private static final Logger log = LoggerFactory.getLogger(Population.class);
    private static final Random RANDOM = new Random();

    private final Set<Monkey> monkeys = new HashSet<>();
    private final Planner planner;
    private final MonkeyFactory monkeyFactory;
    private final Environment environment;

    public Population(Planner planner, MonkeyFactory monkeyFactory, Environment environment) {
        this.planner = planner;
        this.monkeyFactory = monkeyFactory;
        this.environment = environment;
        createInitialPopulation();
    }

    private void createInitialPopulation() {
        environment.getBehaviourCount()
                .entrySet()
                .stream()
                .flatMap(entry -> manyMonkeysOfType(entry.getKey(), (int) entry.getValue()))
                .forEach(this::addMonkey);
    }

    private Stream<? extends Monkey> manyMonkeysOfType(Class<? extends Monkey> type, int count) {
        return IntStream
                .range(0, count)
                .mapToObj(x -> monkeyFactory.monkeyOfType(type));
    }

    public void wantsToBread(Monkey monkey) {
        findSuitablePartner(monkey)
                .ifPresent(partner -> breed(partner, monkey));
    }

    private void breed(Monkey m1, Monkey m2) {
        Monkey child = monkeyFactory.randomChild(m1, m2, environment.getMutationProbability());
        addMonkey(child);
    }

    private Population addMonkey(Monkey child) {
        if (full()) {
            log.debug("No space for new monkey: {}", monkeys.size());
        } else {
            newMonkey(child);
        }
        return this;
    }

    private boolean full() {
        return monkeys.size() >= environment.getMaxPopulationSize();
    }

    private void newMonkey(Monkey child) {
        monkeys.add(child);
        scheduleBreedings(child);
        scheduleDeath(child);
        scheduleNextParasiteInfection(child);
        log.debug("New monkey in population {}total {}", child, monkeys.size());
    }

    private void scheduleNextParasiteInfection(Monkey child) {
        final Duration parasiteInfection = environment.getParasiteInfection().make();
        planner.schedule(new AskForGrooming(child, parasiteInfection, this));
    }

    private void scheduleDeath(Monkey child) {
        Duration lifetime = environment.getLifetime().make();
        planner.schedule(new Kill(child, lifetime, this));
    }

    private void scheduleBreedings(Monkey child) {
        final int childrenCount = RANDOM.nextInt(environment.getMaxChildren() + 1);
        IntStream.
                rangeClosed(1, childrenCount)
                .forEach(x -> scheduleBreeding(child));
    }

    private void scheduleBreeding(Monkey child) {
        final Duration breeding = environment.getBreeding().make();
        planner.schedule(new Breed(child, breeding, this));
    }

    private Optional<Monkey> findSuitablePartner(Monkey monkey) {
        return monkeys
                .stream()
                .limit(100)
                .filter(m -> m != monkey)
                .min(comparing(monkey::ageDifferenceBetween));
    }

    public Statistics statistics() {
        final Map<? extends Class<? extends Monkey>, Integer> perType = monkeys
                .stream()
                .collect(groupingBy(Monkey::getClass))
                .entrySet()
                .stream()
                .collect(toMap(entry -> entry.getKey(), entry -> entry.getValue().size()));
        return new Statistics(ImmutableMap.copyOf(perType), planner.queueSize(), planner.totalExecuted());
    }

    public void kill(Monkey monkey) {
        monkeys.remove(monkey);
        monkey.kill();
        log.debug("Monkey {} died, left {}", monkey, monkeys.size());
    }

    public void askForGrooming(Monkey monkey) {
        killByParasite(monkey);
        findMonkeyToGroomMe(monkey).ifPresent(m -> monkey.groomedBy(m));
        scheduleNextParasiteInfection(monkey);
    }

    private void killByParasite(Monkey monkey) {
        final Duration parasiteDeathDelay = environment.getDeathByParasite().make();
        planner.schedule(new KillByParasite(monkey, parasiteDeathDelay, this));
    }

    private Optional<Monkey> findMonkeyToGroomMe(Monkey monkey) {
        return monkeys
                .stream()
                .filter(m -> m != monkey)
                .skip(randomMonkeyIdx())
                .findAny();
    }

    private int randomMonkeyIdx() {
        if (monkeys.size() > 1) {
            return RANDOM.nextInt(monkeys.size() - 1);
        } else {
            return 0;
        }
    }

}

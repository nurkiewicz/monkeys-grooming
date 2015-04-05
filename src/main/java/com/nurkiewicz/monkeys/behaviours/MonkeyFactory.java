package com.nurkiewicz.monkeys.behaviours;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.nurkiewicz.monkeys.simulation.Environment;

import java.time.Clock;
import java.util.Random;
import java.util.function.Supplier;

public class MonkeyFactory {

    private final Random random = new Random();

    private final Clock simulationTime;
    private final Environment environment;

    public MonkeyFactory(Clock simulationTime, Environment environment) {
        this.simulationTime = simulationTime;
        this.environment = environment;
    }

    public Monkey randomChild(Monkey m1, Monkey m2, double mutationProbability) {
        if (random.nextDouble() < mutationProbability) {
            return randomTypeOfMonkey();
        }
        Monkey dominatingParent = random.nextBoolean()? m1 : m2;
        return monkeyOfType(dominatingParent.getClass());
    }

    private Monkey randomTypeOfMonkey() {
        return monkeyOfType(randomType());
    }

    private Class<? extends Monkey> randomType() {
        ImmutableList<Class<? extends Monkey>> types = factories().keySet().asList();
        return types.get(random.nextInt(types.size()));
    }

    @SuppressWarnings("unchecked")
    public <T extends Monkey> T monkeyOfType(Class<T> type) {
        return (T) factories().get(type).get();
    }

    private ImmutableMap<Class<? extends Monkey>, Supplier<Monkey>> factories() {
        return ImmutableMap.of(
                Sucker.class, () -> new Sucker(simulationTime),
                Cheater.class, () -> new Cheater(simulationTime, environment.getCheaterAcceptProbability()),
                Grudger.class, () -> new Grudger(simulationTime)
        );
    }


}

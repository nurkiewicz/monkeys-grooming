package com.nurkiewicz.monkeys.simulation;

import com.google.common.collect.ImmutableMap;
import com.nurkiewicz.monkeys.RandomPeriod;
import com.nurkiewicz.monkeys.behaviours.Monkey;
import lombok.Value;

import java.time.Period;

@Value
public class Environment {

    ImmutableMap<Class<? extends Monkey>, Integer> behaviourCount;
    double mutationProbability;
    RandomPeriod lifetime;
    RandomPeriod breeding;
    RandomPeriod deathByParasite;
    RandomPeriod parasiteInfection;
    RandomPeriod dieEarlierDueToGrooming;
    Period simulationLength;
    int maxPopulationSize;
    int maxChildren;

}

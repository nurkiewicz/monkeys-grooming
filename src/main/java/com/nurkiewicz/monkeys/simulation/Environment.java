package com.nurkiewicz.monkeys.simulation;

import com.google.common.collect.ImmutableMap;
import com.nurkiewicz.monkeys.RandomPeriod;
import com.nurkiewicz.monkeys.behaviours.Monkey;
import lombok.Builder;
import lombok.Value;

import java.time.Period;

@Value
@Builder
public class Environment {

    /**
     * How many instances of each behaviour shoudl we start with
     */
    ImmutableMap<Class<? extends Monkey>, Integer> initialCount;
    /**
     * The probability that a child will have random gene rather than 50/50 chances of having parents' gene
     */
    double mutationProbability;
    /**
     * The probability that cheater will accept grooming despite being... cheater.
     */
    double cheaterAcceptProbability;
    /**
     * The probability that monkey doing grooming will die immediately after
     */
    double dieDueToGroomingProbability;
    /**
     * Random variable characterizing life time.
     */
    RandomPeriod lifetime;
    /**
     * When breeding takes places during life time.
     */
    RandomPeriod breeding;
    /**
     * How long it takes after infection to die.
     * Monkey must be groomed beforehand
     */
    RandomPeriod deathByParasite;
    /**
     * How often monkey is infected by parasite.
     */
    RandomPeriod parasiteInfection;
    /**
     * How long should the simulation be taking place
     */
    Period simulationLength;
    /**
     * Breeding is not possible above this limit.
     */
    int maxPopulationSize;
    /**
     * Each monkey will have between 0 and this number of children.
     */
    int maxChildren;

}

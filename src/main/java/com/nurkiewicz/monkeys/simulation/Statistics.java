package com.nurkiewicz.monkeys.simulation;

import com.google.common.collect.ImmutableMap;
import com.nurkiewicz.monkeys.behaviours.Monkey;
import lombok.Value;

import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Value
public class Statistics {

    private final ImmutableMap<String, Integer> perType;
    private final int queueSize;
    private final long totalActionsExecuted;

    public Statistics(ImmutableMap<Class<? extends Monkey>, Integer> perType, int queueSize, long totalActionsExecuted) {
        this.totalActionsExecuted = totalActionsExecuted;
        final Map<String, Integer> perTypeStr = perType
                .entrySet()
                .stream()
                .collect(toMap(
                        e -> e.getKey().getSimpleName(),
                        Map.Entry::getValue));
        this.perType = ImmutableMap.copyOf(perTypeStr);
        this.queueSize = queueSize;
    }
}

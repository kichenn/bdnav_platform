package com.bdxh.order.configration.mybatis;


import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

public class TablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        int size = collection.size();
        for (String each : collection) {
            if (each.endsWith(Math.abs(preciseShardingValue.getValue() % size )+ "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}

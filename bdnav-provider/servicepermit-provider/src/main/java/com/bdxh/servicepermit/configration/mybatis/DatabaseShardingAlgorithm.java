package com.bdxh.servicepermit.configration.mybatis;

import com.google.common.base.Objects;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * 分库算法
 */
public class DatabaseShardingAlgorithm implements PreciseShardingAlgorithm<String> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        int size = collection.size();
        for (String each : collection) {
            if (each.endsWith(Math.abs(Objects.hashCode(preciseShardingValue.getValue())) % size + "")) {
                return each;
            }
    }
        throw new UnsupportedOperationException();
    }
}

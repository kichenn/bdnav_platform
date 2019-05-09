package com.bdxh.servicepermit.configration.mybatis;


import com.google.common.base.Objects;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * 分表算法
 * 只实现了 ==和IN的分表算法实现 可拓展Between算法
 */
public class TablePreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {
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

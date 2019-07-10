package com.bdxh.wallet.configration.mybatis;


import com.google.common.base.Objects;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * @Description: 分表策略
 * @Author: Kang
 * @Date: 2019/7/10 18:19
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

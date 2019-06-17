package com.bdxh.user.configration.mybatis;

import com.google.common.base.Objects;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

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

    public static void main(String[] args) {
        int size = 4;

        int i = Math.abs(Objects.hashCode("20190617001")) % size;
        System.out.println(i);
    }
}

package com.bdxh.servicepermit.configration.mybatis.data;

import com.alibaba.druid.pool.DruidDataSource;
import com.bdxh.common.utils.AESUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Data
@ConfigurationProperties(prefix = "spring.datasource.ds1")
@Component
public class DataSourceM1 {

    private String url;

    private String username;

    private String password;

    private String type;

    private String driverClassName;

    private String filters;

    private int maxActive;

    private int initialSize;

    private int minIdle;

    private int maxWait;

    private long timeBetweenEvictionRunsMillis;

    private long minEvictableIdleTimeMillis;

    private String validationQuery;

    private boolean testWhileIdle;

    private boolean testOnBorrow;

    private boolean testOnReturn;

    private boolean poolPreparedStatements;

    private int maxOpenPreparedStatements;

    private Properties connectionProperties;

    public DataSource createDataSource() {
        DruidDataSource result = new DruidDataSource();
        try {
            result.setUrl(url);
            result.setUsername(username);
            result.setPassword(AESUtils.deCode(password, AESUtils.AesConstant.MYSQL_KEY));
            result.setDbType(type);
            result.setDriverClassName(driverClassName);
            result.setFilters(filters);
            result.setMaxActive(maxActive);
            result.setInitialSize(initialSize);
            result.setMinIdle(minIdle);
            result.setMaxActive(maxWait);
            result.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            result.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            result.setValidationQuery(validationQuery);
            result.setTestWhileIdle(testWhileIdle);
            result.setTestOnBorrow(testOnBorrow);
            result.setTestOnReturn(testOnReturn);
            result.setPoolPreparedStatements(poolPreparedStatements);
            result.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
            result.setConnectProperties(connectionProperties);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

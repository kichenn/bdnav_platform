package com.bdxh.account.configration.mybatis;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.bdxh.account.configration.mybatis.data.DataSourceM0;
import com.bdxh.account.configration.mybatis.data.DataSourceM1;
import com.github.pagehelper.PageHelper;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
@EnableTransactionManagement
public class MybatisConfig {

    @Autowired
    private DataSourceM0 dataSourceM0;

    @Autowired
    private DataSourceM1 dataSourceM1;

    @Bean(name = "shardingDataSource")
    @Primary
    public DataSource getDataSource() throws SQLException {
        //分库分表配置
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        //分库分表策略
        shardingRuleConfig.getTableRuleConfigs().add(getAccountTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getUserDeviceTableRuleConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(getAccountNuqiueTableRuleConfiguration());
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds_0", dataSourceM0.createDataSource());
        dataSourceMap.put("ds_1", dataSourceM1.createDataSource());
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), new Properties());
    }

    @Bean(name = "pageHelper")
    public PageHelper getPageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "true");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("shardingDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //指定别名包
        sqlSessionFactoryBean.setTypeAliasesPackage("com.bdxh.account.entity");
        sqlSessionFactoryBean.setDataSource(dataSource);
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            //指定mapper文件的位置
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("mybatis初始化异常！", e);
            throw new RuntimeException("mybatis初始化异常！", e);
        }

    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager testTransactionManager(@Qualifier("shardingDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TableRuleConfiguration getAccountTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_account");
        result.setActualDataNodes("ds_${0..1}.t_account");
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("school_code", new DatabaseShardingAlgorithm()));
        return result;
    }

    @Bean
    public TableRuleConfiguration getUserDeviceTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_user_device");
        result.setActualDataNodes("ds_${0..1}.t_user_device");
        result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("school_code", new DatabaseShardingAlgorithm()));
        return result;
    }


    //默认定向第一个库
    @Bean
    public TableRuleConfiguration getAccountNuqiueTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration();
        result.setLogicTable("t_account_unqiue");
        result.setActualDataNodes("ds_${0}.t_account_unqiue");
        return result;
    }

}

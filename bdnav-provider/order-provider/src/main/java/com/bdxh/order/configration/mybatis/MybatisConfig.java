package com.bdxh.order.configration.mybatis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.github.pagehelper.PageHelper;

@Slf4j
@Configuration
@EnableTransactionManagement
public class MybatisConfig {

	@Bean(name = "dataSource0")
	@ConfigurationProperties(prefix = "spring.datasource.ds0")
	public DataSource dataSource0(){
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "dataSource1")
	@ConfigurationProperties(prefix = "spring.datasource.ds1")
	public DataSource dataSource1(){
		return DruidDataSourceBuilder.create().build();
	}

	@Bean(name = "shardingDataSource")
	@Primary
	public DataSource getDataSource(@Qualifier("dataSource0") DataSource dataSource0,@Qualifier("dataSource1") DataSource dataSource1) throws SQLException {
		//分库分表配置
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		//分库分表策略
		shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
		shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
		//级联绑定表，用于优化查询
		shardingRuleConfig.getBindingTableGroups().add("t_order,t_order_item");
		Map<String, DataSource> dataSourceMap = new HashMap<>();
		dataSourceMap.put("ds_0", dataSource0);
		dataSourceMap.put("ds_1", dataSource1);
		return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig,new ConcurrentHashMap(), new Properties());
	}
	
	@Bean(name="pageHelper")
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
	
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory(@Qualifier("shardingDataSource") DataSource dataSource) {
		SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
		//指定别名包
		sqlSessionFactoryBean.setTypeAliasesPackage("com.bdxh.order.entity");
		sqlSessionFactoryBean.setDataSource(dataSource);
		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			//指定mapper文件的位置
			sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
			return sqlSessionFactoryBean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("mybatis初始化异常！", e);
			throw new RuntimeException("mybatis初始化异常！",e);
		}
		
	}

	@Bean(name="sqlSessionTemplate")
	public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("sqlSessionFactory")SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name="transactionManager")
	public DataSourceTransactionManager testTransactionManager(@Qualifier("shardingDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public TableRuleConfiguration getOrderTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration();
		result.setLogicTable("t_order");
		result.setActualDataNodes("ds_${0..1}.t_order${0..1}");
		result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("school_code", new DatabaseShardingAlgorithm()));
		result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("user_id", new TablePreciseShardingAlgorithm(),new TableRangeShardingAlgorithm()));
		return result;
	}

	@Bean
	public TableRuleConfiguration getOrderItemTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration();
		result.setLogicTable("t_order_item");
		result.setActualDataNodes("ds_${0..1}.t_order_item${0..1}");
		result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_no", new DatabaseShardingAlgorithm()));
		result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_no", new TablePreciseShardingAlgorithm(),new TableRangeShardingAlgorithm()));
		return result;
	}

}

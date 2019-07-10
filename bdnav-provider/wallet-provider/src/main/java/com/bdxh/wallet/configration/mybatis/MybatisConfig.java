package com.bdxh.wallet.configration.mybatis;

import com.bdxh.wallet.configration.mybatis.data.DataSourceM0;
import com.bdxh.wallet.configration.mybatis.data.DataSourceM1;
import com.bdxh.wallet.configration.mybatis.data.DataSourceM2;
import com.bdxh.wallet.configration.mybatis.data.DataSourceM3;
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

	@Autowired
	private DataSourceM2 dataSourceM2;

	@Autowired
	private DataSourceM3 dataSourceM3;

	@Bean(name = "shardingDataSource")
	@Primary
	public DataSource getDataSource() throws SQLException {
		//分库分表配置
		ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
		//分库分表策略
		shardingRuleConfig.getTableRuleConfigs().add(getPhysicalCardTableRuleConfiguration());
		shardingRuleConfig.getTableRuleConfigs().add(getWalletAccountTableRuleConfiguration());
		shardingRuleConfig.getTableRuleConfigs().add(getWalletConsumerTableRuleConfiguration());
		shardingRuleConfig.getTableRuleConfigs().add(getWalletRechargeTableRuleConfiguration());
		Map<String, DataSource> dataSourceMap = new HashMap<>();
		dataSourceMap.put("ds_0", dataSourceM0.createDataSource());
		dataSourceMap.put("ds_1", dataSourceM1.createDataSource());
		dataSourceMap.put("ds_2", dataSourceM2.createDataSource());
		dataSourceMap.put("ds_3", dataSourceM3.createDataSource());
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
		sqlSessionFactoryBean.setTypeAliasesPackage("com.bdxh.wallet.entity");
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
	public TableRuleConfiguration getPhysicalCardTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration();
		result.setLogicTable("t_physical_card");
		result.setActualDataNodes("ds_${0..3}.t_physical_card${0..3}");
		result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("school_code", new DatabaseShardingAlgorithm()));
		result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("card_number", new TablePreciseShardingAlgorithm()));
		return result;
	}

	@Bean
	public TableRuleConfiguration getWalletAccountTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration();
		result.setLogicTable("t_wallet_account");
		result.setActualDataNodes("ds_${0..3}.t_wallet_account${0..3}");
		result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("school_code", new DatabaseShardingAlgorithm()));
		result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("card_number", new TablePreciseShardingAlgorithm()));
		return result;
	}

	@Bean
	public TableRuleConfiguration getWalletConsumerTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration();
		result.setLogicTable("t_wallet_consumer");
		result.setActualDataNodes("ds_${0..3}.t_wallet_consumer${0..3}");
		result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("school_code", new DatabaseShardingAlgorithm()));
		result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("card_number", new TablePreciseShardingAlgorithm()));
		return result;
	}


	@Bean
	public TableRuleConfiguration getWalletRechargeTableRuleConfiguration() {
		TableRuleConfiguration result = new TableRuleConfiguration();
		result.setLogicTable("t_wallet_recharge");
		result.setActualDataNodes("ds_${0..3}.t_wallet_recharge${0..3}");
		result.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("school_code", new DatabaseShardingAlgorithm()));
		result.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("card_number", new TablePreciseShardingAlgorithm()));
		return result;
	}

}

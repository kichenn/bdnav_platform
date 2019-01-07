package com.bdxh.wallet.configration.mybatis;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@Slf4j
public class MybatisConfig {

	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource.ds0")
	public DataSource dataSource(){
		return DruidDataSourceBuilder.create().build();
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
	public SqlSessionFactory getSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) {
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
	public DataSourceTransactionManager testTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
}

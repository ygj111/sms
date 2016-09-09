package com.hhh.fund.config;

import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;

/**
 * Spring Application Context Configuration Class
 * 
 * @author 3hzl
 * 注意：<br>
 * 	<p>1、如果不采用Jpa的方式访问数据库，则不需要配置@EnableJpaRepositories。</p>
 * <p>2、注意新增业务Jpa entity需要在entityManagerFactory增加对entity所在package的扫描。</p>
 */
@Configuration
@PropertySource(value = {"classpath:db.properties", "classpath:security.properties","classpath:sms.properties"})
@ComponentScan( basePackages ={ "com.hhh.fund.service","com.hhh.sms.service","com.hhh.sms.dao"}, 
					excludeFilters = @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION)) 
@EnableJpaRepositories(basePackages = { "com.hhh.fund.dao","com.hhh.sms.dao" },
					includeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class)},
					entityManagerFactoryRef="entityManagerFactory",
					transactionManagerRef="transactionManager")
@EnableTransactionManagement 
@Import(value = {CachingConfig.class, SecurityConfig.class})  
public class AppContext {
	private final static Logger logger = LoggerFactory.getLogger(AppContext.class);
	
	@Resource
	public Environment env;
	
	@Resource(name = "dataSource")
	private DruidDataSource dataSource;
	
	@Resource(name = "jpaVendorAdapter")
	private JpaVendorAdapter jpaVendorAdapter;
	
	@Resource(name = "entityManagerFactory")
	private EntityManagerFactory entityManagerFactory;
	
	/**
	 * 数据源绑定
	 * @return
	 * @throws PropertyVetoException
	 * @throws SQLException 
	 */
	@Bean(name="dataSource")
	public DruidDataSource dataSource() 
			throws PropertyVetoException, SQLException {
		DruidDataSource dataSource = new DruidDataSource();
		
		logger.info("my jdbc.driver = " + env.getProperty("jdbc.driver"));
		// 数据库连接
		dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pwd"));
		
		// 数据池配置
		dataSource.setMaxActive(Integer.parseInt(env.getProperty("datapool.maxActive")));
		dataSource.setInitialSize(Integer.parseInt(env.getProperty("datapool.initialSize")));
		dataSource.setMaxWait(Long.parseLong(env.getProperty("datapool.maxWait")));
		dataSource.setMinIdle(Integer.parseInt(env.getProperty("datapool.minIdle")));
		dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getProperty("datapool.timeBetweenEvictionRunsMillis")));
		dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getProperty("datapool.minEvictableIdleTimeMillis")));
		dataSource.setValidationQuery(env.getProperty("datapool.validationQuery"));
		dataSource.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("datapool.testWhileIdle")));
		dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("datapool.testOnBorrow")));
		dataSource.setTestOnReturn(Boolean.parseBoolean(env.getProperty("datapool.testOnReturn")));
		
		// SQL输出
		dataSource.setFilters("stat, slf4j");
		
		logger.info("Data source properties: " + dataSource.getConnection());
		return dataSource;
	}
	
	/**
	 * Druid的日志过滤器设置
	 * @return
	 */
	@Bean(name = "logFilter")
	public Slf4jLogFilter logFilter() {
		Slf4jLogFilter logFilter =new Slf4jLogFilter();
		logFilter.setStatementExecutableSqlLogEnable(true);
		return logFilter;
	}
	
	/**
	 * 慢速SQL过滤
	 * @return
	 */
	@Bean(name = "statFilter")
	public StatFilter statFilter() {
		StatFilter statFilter = new StatFilter();
		
		statFilter.setSlowSqlMillis(Long.parseLong(env.getProperty("datapool.slowSQLMills")));
		statFilter.setLogSlowSql(Boolean.parseBoolean(env.getProperty("datapool.logSlowSql")));
		
		return statFilter;
	}
	
	/**
	 * JPA Vendor
	 * @return
	 */
	@Bean(name = "jpaVendorAdapter")
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		
		jpaVendorAdapter.setDatabase(Database.MYSQL);
		jpaVendorAdapter.setGenerateDdl(false);
		jpaVendorAdapter.setShowSql(true);
		jpaVendorAdapter.setDatabasePlatform(env.getProperty("jpa.dbPlatform"));
		
		return jpaVendorAdapter;
	} 
	
	@Bean(name = "entityManagerFactory")
	public EntityManagerFactory entityManagerFactory()  {
		LocalContainerEntityManagerFactoryBean lcemf = new LocalContainerEntityManagerFactoryBean();
		
		lcemf.setDataSource(dataSource);
//	logger.info("Retrieve data source : " + dataSource.getConnection().createStatement());
		
		lcemf.setPackagesToScan("com.hhh.fund.dao.entity","com.hhh.sms.dao.entity");
		lcemf.setJpaVendorAdapter(jpaVendorAdapter);
		logger.info("Before LocalContainerEntityManagerFactoryBean.afterPropertiesSet():::"+lcemf.getJpaPropertyMap());
		
		lcemf.setPersistenceUnitName("mysqlUnit");
		
		// 返回factory需要先设置afterProperties
		lcemf.afterPropertiesSet();
		logger.info("After LocalContainerEntityManagerFactoryBean.afterPropertiesSet():::"+lcemf.getJpaPropertyMap());
		return lcemf.getObject();
	}
	
	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		
		transactionManager.setDataSource(dataSource);
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		
		return transactionManager;
	}
	
	@Bean
	 public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	 }
	
	@Bean
	public EntityManager entityManager() {
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		logger.info("entityManager: " + entityManager.isOpen());
		
		return entityManager;
	}
}

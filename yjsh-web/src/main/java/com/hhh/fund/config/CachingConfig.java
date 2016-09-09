package com.hhh.fund.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 启用注解<cache:annotation-driven cache-manager="cacheManager" />
 * @author 3hzl
 *
 */
@Configuration
@EnableCaching
public class CachingConfig {
	private static final Logger logger = LoggerFactory.getLogger(CachingConfig.class);
	
	 @Bean  
	 public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {  
	        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();  
	        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));  
	        return ehCacheManagerFactoryBean;  
	 }
	 
	 @Bean  
	 public CacheManager cacheManager() {  
		 logger.info("EhCacheCacheManager");  
	     EhCacheCacheManager cacheManager = new EhCacheCacheManager();  
	     cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());  
	     return cacheManager;  
	 }  
}

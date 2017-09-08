package com.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.cms.repository.base.BaseRepositoryFactoryBean;


@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class CMSApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args){
		SpringApplication.run(CMSApplication.class, args);
	}
	
}

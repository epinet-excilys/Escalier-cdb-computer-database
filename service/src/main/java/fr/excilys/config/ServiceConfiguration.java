package fr.excilys.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


@Configuration
@ComponentScan(basePackages = { "fr.excilys.dao", "fr.excilys.service", "fr.excilys.controller"
		,"fr.excilys.pagination", "fr.excilys.mapper", "fr.excilys.validator", "fr.excilys.model", "fr.excilys.config"})
@PropertySource(value = "classpath:application.properties")
@EnableTransactionManagement
public class ServiceConfiguration{
	
	Environment environment;
	
	@Bean
	DataSource datasource(Environment environment) {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(environment.getProperty(EnumProperties.PROPERTIES_DRIVER.getMessage()));
		driverManagerDataSource.setUrl(environment.getProperty(EnumProperties.PROPERTIES_URL.getMessage()));
		driverManagerDataSource.setUsername(environment.getProperty(EnumProperties.PROPERTIES_USER.getMessage()));
		driverManagerDataSource.setPassword(environment.getProperty(EnumProperties.PROPERTIES_PASSWORD.getMessage()));
		
		return driverManagerDataSource;
	}
	

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(datasource(environment));
        factoryBean.setPackagesToScan("fr.excilys.model");
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
	
}

package fr.excilys.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import fr.excilys.model.Company;
import fr.excilys.model.Computer;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {
	

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
        factoryBean.setAnnotatedClasses(Computer.class, Company.class);
        factoryBean.setPackagesToScan("model","dao");
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
	

}

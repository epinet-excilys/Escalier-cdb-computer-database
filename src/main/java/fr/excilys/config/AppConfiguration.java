package fr.excilys.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "fr.excilys.dao", "fr.excilys.service", "fr.excilys.servlet"
		,"fr.excilys.pagination", "fr.excilys.mapper"})
@PropertySource(value = "classpath:application.properties")
public class AppConfiguration extends AbstractContextLoaderInitializer {
	
	@Autowired
	Environment environment;
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(AppConfiguration.class);
		
		return context;
	}
	
	@Bean
	DataSource datasource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(environment.getProperty(EnumProperties.PROPERTIES_DRIVER.getMessage()));
		driverManagerDataSource.setUrl(environment.getProperty(EnumProperties.PROPERTIES_URL.getMessage()));
		driverManagerDataSource.setUsername(environment.getProperty(EnumProperties.PROPERTIES_USER.getMessage()));
		driverManagerDataSource.setPassword(environment.getProperty(EnumProperties.PROPERTIES_PASSWORD.getMessage()));
		
		return driverManagerDataSource;
	}
	
//	@Bean
//	NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
//		NamedParameterJdbcTemplate namedParameterJdbcTemplate=new NamedParameterJdbcTemplate(dataSource);
//		
//		return namedParameterJdbcTemplate;
//	}
	

}

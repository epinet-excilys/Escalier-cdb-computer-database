package fr.excilys.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "fr.excilys.dao", "fr.excilys.service", "fr.excilys.servlet"
		,"fr.excilys.pagination", "fr.excilys.mapper", "fr.excilys.validator"})
@PropertySource(value = "classpath:application.properties")
public class AppConfiguration implements WebApplicationInitializer {
	
	@Autowired
	Environment environment;

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext annotationWebContext = new AnnotationConfigWebApplicationContext();
		annotationWebContext.register(WebConfiguration.class, AppConfiguration.class);
		annotationWebContext.setServletContext(servletContext);
		DispatcherServlet dispachterServlet = new DispatcherServlet(annotationWebContext);
		ServletRegistration.Dynamic servlet = ((ServletContext) annotationWebContext).addServlet("DashBoard", dispachterServlet);
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
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
}

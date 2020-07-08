package fr.excilys.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan(basePackages = { "fr.excilys.dao", "fr.excilys.service", "fr.excilys.controller",
		"fr.excilys.pagination", "fr.excilys.mapper", "fr.excilys.validator", "fr.excilys.model",
		"fr.excilys.config", "fr.excilys.jwtToken"})
@PropertySource(value = "classpath:application.properties")
public class AppConfiguration implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext){
		AnnotationConfigWebApplicationContext annotationWebContext = new AnnotationConfigWebApplicationContext();
		annotationWebContext.register(AppConfiguration.class, WebConfiguration.class
				, HibernateConfiguration.class,WebSecurityConfig.class, SecurityWebApplicationInitializer.class);
		annotationWebContext.setServletContext(servletContext);
		DispatcherServlet dispachterServlet = new DispatcherServlet(annotationWebContext);
		ServletRegistration.Dynamic servlet = servletContext.addServlet("login", dispachterServlet);
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}

}

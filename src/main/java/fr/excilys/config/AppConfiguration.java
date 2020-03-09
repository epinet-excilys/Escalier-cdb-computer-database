package fr.excilys.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = { "fr.excilys.dao", "fr.excilys.service", "fr.excilys.servlet" })
public class AppConfiguration implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(AppConfiguration.class,WebMvcConfigure.class);
		context.setServletContext(servletContext);

		ServletRegistration.Dynamic servlet = servletContext.addServlet("dynamicServlet",
				new DispatcherServlet(context));

		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}

}

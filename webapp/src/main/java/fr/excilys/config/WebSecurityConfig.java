package fr.excilys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.excilys.service.SecurityUserServiceCDB;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityUserServiceCDB securityUserServiceCDB;

	@Autowired
	public void userConfigurationGlobal(AuthenticationManagerBuilder authentificationManagerBuilder) throws Exception {

		authentificationManagerBuilder.userDetailsService(securityUserServiceCDB).passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
        //HTTP Basic authentication
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers("/", "/login", "/logoutSuccessful").permitAll()
        
        .antMatchers(HttpMethod.GET, "/computers/**").hasAnyRole("ADMIN","USER")
        .antMatchers(HttpMethod.POST, "/computers/**").hasAnyRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/computers/**").hasAnyRole("ADMIN")
        .antMatchers(HttpMethod.PUT, "/computers/**").hasAnyRole("ADMIN")
        
        .antMatchers(HttpMethod.GET, "/dashboard/**").hasAnyRole("ADMIN","USER")
        .antMatchers(HttpMethod.POST, "/dashboard").hasAnyRole("ADMIN","USER")
        
        .antMatchers(HttpMethod.POST, "/deleteComputer/**").hasRole("ADMIN")
        
        .antMatchers(HttpMethod.GET, "/addComputer/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/addComputer/**").hasRole("ADMIN")
        
        .antMatchers(HttpMethod.GET, "/addUser/**").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/addUser/**").hasRole("ADMIN")
        .and()
        .csrf().disable()
        .formLogin().disable();


	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
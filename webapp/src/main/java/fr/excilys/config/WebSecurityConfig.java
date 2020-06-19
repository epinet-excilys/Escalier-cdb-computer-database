package fr.excilys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

//		authentificationManagerBuilder.inMemoryAuthentication().withUser("user")
//				.password(passwordEncoder().encode("password")).roles("USER");
//
//		authentificationManagerBuilder.inMemoryAuthentication().withUser("admin")
//				.password(passwordEncoder().encode("admin")).roles("ADMIN");

		authentificationManagerBuilder.userDetailsService(securityUserServiceCDB).passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();

		http.authorizeRequests().antMatchers("/dashboard").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

		http.authorizeRequests().antMatchers("/editComputer", "/addComputer", "/deleteComputer")
				.access("hasRole('ROLE_ADMIN')");

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		http.authorizeRequests().and().formLogin().loginProcessingUrl("/j_spring_security_check").loginPage("/login")
				.defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").usernameParameter("username")
				.passwordParameter("password").and().logout().logoutUrl("/logout")
				.logoutSuccessUrl("/logoutSuccessful");

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
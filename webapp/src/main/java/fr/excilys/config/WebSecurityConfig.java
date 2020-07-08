package fr.excilys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import fr.excilys.jwtToken.JwtTokenAuthorizationOncePerRequestFilter;
import fr.excilys.jwtToken.JwtUnAuthorizedResponseAuthenticationEntryPoint;
import fr.excilys.service.SecurityUserServiceCDB;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityUserServiceCDB securityUserServiceCDB;
	@Autowired
	private JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;
	@Autowired
	private JwtTokenAuthorizationOncePerRequestFilter jwtTokenAuthorization;

	@Autowired
	public void userConfigurationGlobal(AuthenticationManagerBuilder authentificationManagerBuilder) throws Exception {

		authentificationManagerBuilder.userDetailsService(securityUserServiceCDB).passwordEncoder(passwordEncoder());

	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(securityUserServiceCDB);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
		.and().csrf().disable().exceptionHandling()
		.authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().anyRequest()
		.authenticated();
		
		http.addFilterBefore(jwtTokenAuthorization, UsernamePasswordAuthenticationFilter.class);
		
		http.headers().frameOptions().sameOrigin();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
            .antMatchers(
                HttpMethod.POST,
                "/login"
            ).antMatchers(
                    HttpMethod.POST,
                    "/users"
                )
            .antMatchers(
                    HttpMethod.POST,
                    "/bleu"
                )
            .antMatchers(HttpMethod.OPTIONS, "/**");
	}

}
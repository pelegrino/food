package br.com.pelegrino.food.infraestructure.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {
	
	
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeHttpRequests()
			.requestMatchers("/images/**", "/css/**", "/js/**", "/public/", "/sbpay").permitAll()
			.requestMatchers("/cliente/**").hasRole(Role.CLIENTE.toString())
			.requestMatchers("/restaurante/**").hasRole(Role.RESTAURANTE.toString())
			.anyRequest().authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.failureUrl("/login-error")
			.successHandler(null)
			.permitAll()
			.and()
			.logout()
			.logoutUrl("/logout")
			.permitAll();
	}
	
}

package br.com.pelegrino.food.infraestructure.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl();
    }
	
    @Override
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
				.successHandler(authenticationSuccessHandler())
				.permitAll()
			.and()
				.logout()
				.logoutUrl("/logout")
				.permitAll();
	}
	
}

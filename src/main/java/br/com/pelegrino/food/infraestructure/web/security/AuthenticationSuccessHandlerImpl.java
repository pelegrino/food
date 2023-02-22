package br.com.pelegrino.food.infraestructure.web.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import br.com.pelegrino.food.util.SecurityUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Role role = SecurityUtils.loggedUser().getRole();
		
		if (role == Role.CLIENTE) {
			response.sendRedirect("cliente/home");
			
		} else if (role == Role.RESTAURANTE) {
			response.sendRedirect("restaurante/home");
			
		} else {
			throw new IllegalStateException("Erro na autenticação");
		}
		
	}

}

package br.com.petshoptchutchucao.agenda.infra.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.petshoptchutchucao.agenda.model.User;
import br.com.petshoptchutchucao.agenda.repository.UserRepository;

public class TokenFilterVerification extends OncePerRequestFilter {

	private TokenService tokenService;
	private UserRepository userRepository;

	public TokenFilterVerification(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getHeader("Authorization");
		
		if(token == null || token.isBlank()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		token = token.replace("Bearer", "");
		boolean tokenValid = tokenService.tokenValid(token);
		
		if(tokenValid) {
			String userId = tokenService.extractUserId(token);
			User loged = userRepository.findById(userId).get();
			Authentication authentication = new UsernamePasswordAuthenticationToken(loged, null, loged.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
				
	}

}

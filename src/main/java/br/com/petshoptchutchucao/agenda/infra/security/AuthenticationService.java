package br.com.petshoptchutchucao.agenda.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.petshoptchutchucao.agenda.model.request.LoginFormDto;

@Service
public class AuthenticationService{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	public String authenticate(LoginFormDto loginForm) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword());
		authentication = authenticationManager.authenticate(authentication);
		
		return tokenService.generateToken(authentication);
	}

}

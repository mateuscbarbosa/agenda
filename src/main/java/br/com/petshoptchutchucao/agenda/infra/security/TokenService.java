package br.com.petshoptchutchucao.agenda.infra.security;

import java.util.ArrayList;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.petshoptchutchucao.agenda.dto.LoginFormDto;
import br.com.petshoptchutchucao.agenda.model.Profile;
import br.com.petshoptchutchucao.agenda.model.User;
import br.com.petshoptchutchucao.agenda.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	
	@Autowired
	private UserService userService;
	
	private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String generateToken(LoginFormDto loginForm) {
		User loged = (User) userService.loadUserByUsername(loginForm.getEmail());
		
		Claims claims = Jwts.claims().setSubject(loginForm.getEmail());
		
		ArrayList<String> profilesList = new ArrayList<>();
		
		for(Profile profile : loged.getProfiles()) {
			profilesList.add(profile.getDescription());
		}
		
		return Jwts
				.builder()
				.setId(loged.getId())
				.signWith(key)
				.compact();
	}
}

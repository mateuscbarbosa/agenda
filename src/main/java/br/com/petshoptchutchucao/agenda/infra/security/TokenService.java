package br.com.petshoptchutchucao.agenda.infra.security;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.petshoptchutchucao.agenda.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	
	@Value("${jjwt.secret}")
	private String secret;
	
	public String generateToken(Authentication authentication) {
		User loged = (User) authentication.getPrincipal();
		
		return Jwts
				.builder()
				.setSubject(loged.getId())
				.signWith(testeDeChave(secret), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean tokenValid(String token) {
		try {
			Jwts
				.parserBuilder()
				.setSigningKey(testeDeChave(secret))
				.build()
				.parseClaimsJws(token);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public String extractUserId(String token) {
		Claims claims = Jwts
							.parserBuilder()
							.setSigningKey(testeDeChave(secret))
							.build()
							.parseClaimsJws(token)
							.getBody();
		
		return claims.getSubject().toString();
	}
	
	private SecretKey testeDeChave(String secret) {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
}

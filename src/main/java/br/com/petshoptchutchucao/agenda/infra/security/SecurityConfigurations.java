package br.com.petshoptchutchucao.agenda.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.petshoptchutchucao.agenda.repository.UserRepository;

@Configuration
public class SecurityConfigurations {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.csrf().disable()			
					.authorizeHttpRequests()
					.antMatchers("/auth").permitAll()
					.antMatchers(HttpMethod.GET,"/users").hasRole("RUSER")
					.antMatchers("/users").hasRole("ADMIN")
					.anyRequest().authenticated()
					.and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and().addFilterBefore(new TokenFilterVerification(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
	
}

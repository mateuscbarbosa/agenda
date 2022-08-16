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
					.antMatchers(HttpMethod.GET,"/users").hasAnyRole("RUSER","ADMIN")
					.antMatchers(HttpMethod.POST,"/users").hasAnyRole("CUSER","ADMIN")
					.antMatchers(HttpMethod.PUT,"/users").hasAnyRole("UUSER","ADMIN")
					.antMatchers(HttpMethod.DELETE,"/users").hasAnyRole("DUSER","ADMIN")
					.antMatchers(HttpMethod.GET,"/customers").hasAnyRole("RCLIENT","ADMIN")
					.antMatchers(HttpMethod.POST,"/customers").hasAnyRole("CCLIENT","ADMIN")
					.antMatchers(HttpMethod.PUT,"/customers").hasAnyRole("UCLIENT","ADMIN")
					.antMatchers(HttpMethod.DELETE,"/customer").hasAnyRole("DCLIENT","ADMIN")
					.antMatchers(HttpMethod.GET,"/pets").hasAnyRole("RPET","ADMIN")
					.antMatchers(HttpMethod.POST,"/pets").hasAnyRole("CPET","ADMIN")
					.antMatchers(HttpMethod.PUT,"/pets").hasAnyRole("UPET","ADMIN")
					.antMatchers(HttpMethod.DELETE,"/pets").hasAnyRole("DPET","ADMIN")
					.antMatchers(HttpMethod.GET,"/tasks").hasAnyRole("RTASK","ADMIN")
					.antMatchers(HttpMethod.POST,"/tasks").hasAnyRole("CTASK","ADMIN")
					.antMatchers(HttpMethod.PUT,"/tasks").hasAnyRole("UTASK","ADMIN")
					.antMatchers(HttpMethod.DELETE,"/tasks").hasAnyRole("DTASK","ADMIN")
					.antMatchers(HttpMethod.GET,"/schedules").hasAnyRole("RSCHEDULE","ADMIN")
					.antMatchers(HttpMethod.POST,"/schedules").hasAnyRole("CSCHEDULE","ADMIN")
					.antMatchers(HttpMethod.PUT,"/schedules").hasAnyRole("USCHEDULE","ADMIN")
					.antMatchers(HttpMethod.DELETE,"/schedules").hasAnyRole("DSCHEDULE","ADMIN")
					.antMatchers(HttpMethod.GET,"/reports").hasAnyRole("RREPORTS","ADMIN")
					.antMatchers(HttpMethod.GET,"/reports/**").hasRole("ADMIN")
					.anyRequest().authenticated()
					.and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and().addFilterBefore(new TokenFilterVerification(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
	
}

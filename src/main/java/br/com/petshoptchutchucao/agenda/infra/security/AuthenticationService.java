package br.com.petshoptchutchucao.agenda.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.petshoptchutchucao.agenda.dto.LoginFormDto;
import br.com.petshoptchutchucao.agenda.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
								.orElseThrow(() -> new UsernameNotFoundException(""));
	}
	
	public String authenticate(LoginFormDto loginForm) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword());
		authentication = authenticationManager.authenticate(authentication);
		
		if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), loadUserByUsername(loginForm.getEmail()).getPassword())) {
			throw new BadCredentialsException("Erro!");
		}
		
		return tokenService.generateToken(authentication);
	}

}

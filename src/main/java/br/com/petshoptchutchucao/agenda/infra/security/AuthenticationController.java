package br.com.petshoptchutchucao.agenda.infra.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.petshoptchutchucao.agenda.model.request.LoginFormDto;
import br.com.petshoptchutchucao.agenda.model.response.TokenOutputDto;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping
	public TokenOutputDto authenticate(@RequestBody @Valid LoginFormDto loginForm) {
		return new TokenOutputDto(authenticationService.authenticate(loginForm));
	}
}

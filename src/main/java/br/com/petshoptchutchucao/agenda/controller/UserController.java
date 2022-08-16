package br.com.petshoptchutchucao.agenda.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.petshoptchutchucao.agenda.dto.UserDetailedOutputDto;
import br.com.petshoptchutchucao.agenda.dto.UserFormDto;
import br.com.petshoptchutchucao.agenda.dto.UserOutputDto;
import br.com.petshoptchutchucao.agenda.dto.UserUpdateFormDto;
import br.com.petshoptchutchucao.agenda.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping
	public Page<UserOutputDto> list(Pageable pagination){
		return service.list(pagination);
	}
	
	@PostMapping
	public ResponseEntity<UserOutputDto> register(@RequestBody @Valid UserFormDto userForm, UriComponentsBuilder uriBuilder, @CurrentSecurityContext(expression="authentication") Authentication authentication){
		UserOutputDto dto = service.register(userForm, authentication);

		URI uri = uriBuilder.path("/users/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping
	public ResponseEntity<UserOutputDto> update(@RequestBody @Valid UserUpdateFormDto userForm, @CurrentSecurityContext(expression="authentication") Authentication authentication){
		UserOutputDto userOutput = service.update(userForm, authentication);
		
		return ResponseEntity.ok(userOutput);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UserOutputDto> remove(@PathVariable @NotBlank String id, @CurrentSecurityContext(expression="authentication") Authentication authentication){
		service.inactivate(id, authentication);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDetailedOutputDto> detalis(@PathVariable @NotBlank String id){
		UserDetailedOutputDto dto = service.details(id);
		
		return ResponseEntity.ok(dto);
	}
}

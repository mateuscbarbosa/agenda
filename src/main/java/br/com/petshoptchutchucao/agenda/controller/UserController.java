package br.com.petshoptchutchucao.agenda.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.petshoptchutchucao.agenda.dto.UserDetaliedOutputDto;
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
	public ResponseEntity<UserOutputDto> register(@RequestBody @Valid UserFormDto userForm, UriComponentsBuilder uriBuilder){
		UserOutputDto dto = service.register(userForm);
		
		URI uri = uriBuilder.path("/users/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping
	public ResponseEntity<UserOutputDto> update(@RequestBody @Valid UserUpdateFormDto userForm){
		UserOutputDto userOutput = service.update(userForm);
		
		return ResponseEntity.ok(userOutput);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UserOutputDto> remove(@PathVariable @NotBlank String id){
		service.inactivate(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDetaliedOutputDto> detalis(@PathVariable @NotBlank String id){
		UserDetaliedOutputDto dto = service.details(id);
		
		return ResponseEntity.ok(dto);
	}
}

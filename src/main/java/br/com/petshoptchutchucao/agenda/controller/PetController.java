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

import br.com.petshoptchutchucao.agenda.model.response.PetDetailedOutputDto;
import br.com.petshoptchutchucao.agenda.model.request.PetFormDto;
import br.com.petshoptchutchucao.agenda.model.response.PetOutputDto;
import br.com.petshoptchutchucao.agenda.model.request.PetUpdateFormDto;
import br.com.petshoptchutchucao.agenda.service.PetService;

@RestController
@RequestMapping("/pets")
public class PetController {

	@Autowired
	private PetService service;
	
	@GetMapping
	public Page<PetOutputDto> list(Pageable pagination){
		return service.list(pagination);
	}
	
	@PostMapping
	public ResponseEntity<PetOutputDto> register (@RequestBody @Valid PetFormDto petForm, UriComponentsBuilder uriBuilder, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
		PetOutputDto petOutput = service.register(petForm, authentication);
		
		URI uri = uriBuilder.path("/pets/{id}").buildAndExpand(petOutput.getId()).toUri();
		
		return ResponseEntity.created(uri).body(petOutput);
	}
	
	@PutMapping
	public ResponseEntity<PetOutputDto> update(@RequestBody @Valid PetUpdateFormDto petUpdate, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
		PetOutputDto petOutput = service.update(petUpdate, authentication);
		
		return ResponseEntity.ok(petOutput);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PetOutputDto> delete(@PathVariable @NotBlank String id, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
		service.delete(id, authentication);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PetDetailedOutputDto> details (@PathVariable @NotBlank String id){
		PetDetailedOutputDto petDetailed = service.details(id);
		
		return ResponseEntity.ok(petDetailed);
	}
	
}

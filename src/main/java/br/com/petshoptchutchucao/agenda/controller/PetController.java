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

import br.com.petshoptchutchucao.agenda.dto.PetDetaliedOutputDto;
import br.com.petshoptchutchucao.agenda.dto.PetFormDto;
import br.com.petshoptchutchucao.agenda.dto.PetOutputDto;
import br.com.petshoptchutchucao.agenda.dto.PetUpdateFormDto;
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
	public ResponseEntity<PetOutputDto> register (@RequestBody @Valid PetFormDto petForm, UriComponentsBuilder uriBuilder){
		PetOutputDto petOutput = service.register(petForm);
		
		URI uri = uriBuilder.path("/pets/{id}").buildAndExpand(petOutput.getId()).toUri();
		
		return ResponseEntity.created(uri).body(petOutput);
	}
	
	@PutMapping
	public ResponseEntity<PetOutputDto> update(@RequestBody @Valid PetUpdateFormDto petUpdate){
		PetOutputDto petOutput = service.update(petUpdate);
		
		return ResponseEntity.ok(petOutput);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PetOutputDto> delete(@PathVariable @NotBlank String id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PetDetaliedOutputDto> details (@PathVariable @NotBlank String id){
		PetDetaliedOutputDto petDetailed = service.details(id);
		
		return ResponseEntity.ok(petDetailed);
	}
	
}

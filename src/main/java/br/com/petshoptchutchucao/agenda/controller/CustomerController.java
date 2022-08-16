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

import br.com.petshoptchutchucao.agenda.dto.CustomerDetailedOutputDto;
import br.com.petshoptchutchucao.agenda.dto.CustomerFormDto;
import br.com.petshoptchutchucao.agenda.dto.CustomerOutputDto;
import br.com.petshoptchutchucao.agenda.dto.CustomerUpdateFormDto;
import br.com.petshoptchutchucao.agenda.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@GetMapping
	public Page<CustomerOutputDto> list (Pageable pagination){
		return service.list(pagination);
	}
	
	@PostMapping
	public ResponseEntity<CustomerOutputDto> register (@RequestBody @Valid CustomerFormDto customerForm, UriComponentsBuilder uriBuilder, @CurrentSecurityContext(expression="authentication") Authentication authentication){
		CustomerOutputDto dto = service.register(customerForm, authentication);
		
		URI uri = uriBuilder.path("/customers/{id}").buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping
	public ResponseEntity<CustomerOutputDto> update (@RequestBody @Valid CustomerUpdateFormDto customerForm, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
		CustomerOutputDto customerOutput = service.update(customerForm, authentication);
		
		return ResponseEntity.ok(customerOutput);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CustomerOutputDto> inactivate(@PathVariable @NotBlank String id, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
		service.inactivate(id, authentication);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDetailedOutputDto> details(@PathVariable @NotBlank String id){
		CustomerDetailedOutputDto customerDetailed = service.details(id);
		
		return ResponseEntity.ok(customerDetailed);
	}
	
}

package br.com.petshoptchutchucao.agenda.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.petshoptchutchucao.agenda.dto.CustomerFormDto;
import br.com.petshoptchutchucao.agenda.dto.CustomerOutputDto;
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
	public ResponseEntity<CustomerOutputDto> register (@RequestBody @Valid CustomerFormDto customerForm, UriComponentsBuilder uriBuilder){
		CustomerOutputDto dto = service.register(customerForm);
		
		URI uri = uriBuilder.path("/customers/{id}").buildAndExpand(dto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(dto);
	}
}

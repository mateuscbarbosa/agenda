package br.com.petshoptchutchucao.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.petshoptchutchucao.agenda.dto.PetOutputDto;
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
	
}

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

import br.com.petshoptchutchucao.agenda.dto.ScheduleFormDto;
import br.com.petshoptchutchucao.agenda.dto.ScheduleOutputDto;
import br.com.petshoptchutchucao.agenda.service.ScheduleService;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

	@Autowired
	private ScheduleService service;
	
	@GetMapping
	public Page<ScheduleOutputDto> list(Pageable pagination){
		return service.list(pagination);
	}
	
	@PostMapping
	public ResponseEntity<ScheduleOutputDto> register(@RequestBody @Valid ScheduleFormDto scheduleForm, UriComponentsBuilder uriBuilder){
		ScheduleOutputDto scheduleDto = service.register(scheduleForm);
		
		URI uri = uriBuilder.path("/schedules/{id}").buildAndExpand(scheduleDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(scheduleDto);
	}
}

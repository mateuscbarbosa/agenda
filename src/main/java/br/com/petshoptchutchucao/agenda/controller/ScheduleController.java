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

import br.com.petshoptchutchucao.agenda.model.response.ScheduleDetailedOutputDto;
import br.com.petshoptchutchucao.agenda.model.request.ScheduleFormDto;
import br.com.petshoptchutchucao.agenda.model.response.ScheduleOutputDto;
import br.com.petshoptchutchucao.agenda.model.request.ScheduleUpdateForm;
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
	public ResponseEntity<ScheduleOutputDto> register(@RequestBody @Valid ScheduleFormDto scheduleForm, UriComponentsBuilder uriBuilder, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
		ScheduleOutputDto scheduleDto = service.register(scheduleForm, authentication);
		
		URI uri = uriBuilder.path("/schedules/{id}").buildAndExpand(scheduleDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(scheduleDto);
	}
	
	@PutMapping
	public ResponseEntity<ScheduleOutputDto> update(@RequestBody @Valid ScheduleUpdateForm scheduleUpdate, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
		ScheduleOutputDto scheduleDto = service.update(scheduleUpdate, authentication);
		
		return ResponseEntity.ok(scheduleDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ScheduleOutputDto> delete(@PathVariable @NotBlank String id, @CurrentSecurityContext(expression = "authentication") Authentication authentication){
		service.delete(id, authentication);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ScheduleDetailedOutputDto> details(@PathVariable @NotBlank String id){
		ScheduleDetailedOutputDto scheduleDetailed = service.details(id);
		
		return ResponseEntity.ok().body(scheduleDetailed);
	}
}

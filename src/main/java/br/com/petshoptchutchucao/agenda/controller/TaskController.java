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

import br.com.petshoptchutchucao.agenda.dto.TaskFormDto;
import br.com.petshoptchutchucao.agenda.dto.TaskOutputDto;
import br.com.petshoptchutchucao.agenda.dto.TaskUpdateFormDto;
import br.com.petshoptchutchucao.agenda.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService service;
	
	@GetMapping
	public Page<TaskOutputDto> list(Pageable pagination){
		return service.list(pagination);
	}
	
	@PostMapping
	public ResponseEntity<TaskOutputDto> register(@RequestBody @Valid TaskFormDto taskForm, UriComponentsBuilder uriBuilder){
		TaskOutputDto taskDto = service.register(taskForm);
		
		URI uri = uriBuilder.path("/tasks/{id}").buildAndExpand(taskDto.getId()).toUri();
		
		return ResponseEntity.created(uri).body(taskDto);
	}
	
	@PutMapping
	public ResponseEntity<TaskOutputDto> update(@RequestBody @Valid TaskUpdateFormDto taskUpdate){
		TaskOutputDto taskDto = service.update(taskUpdate);
		
		return ResponseEntity.ok(taskDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<TaskOutputDto> delete(@PathVariable @NotBlank String id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}

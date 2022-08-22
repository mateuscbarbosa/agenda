package br.com.petshoptchutchucao.agenda.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.petshoptchutchucao.agenda.dto.LogsOutputDto;
import br.com.petshoptchutchucao.agenda.service.LogsService;

@RestController
@RequestMapping("/reports")
public class ReportsController {

	@Autowired
	private LogsService logsService;
	
	@GetMapping("/logs")
	public Page<LogsOutputDto> listLogs(Pageable pagination){
		return logsService.list(pagination);
	}
	
	@DeleteMapping("/logs")
	public ResponseEntity<LogsOutputDto> cleanLogs(){
		logsService.cleanLogs(LocalDateTime.now());
		
		return ResponseEntity.noContent().build();
	}
}

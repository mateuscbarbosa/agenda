package br.com.petshoptchutchucao.agenda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

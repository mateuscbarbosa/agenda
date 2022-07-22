package br.com.petshoptchutchucao.agenda.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.petshoptchutchucao.agenda.dto.ScheduleOutputDto;
import br.com.petshoptchutchucao.agenda.model.Schedule;
import br.com.petshoptchutchucao.agenda.repository.ScheduleRepository;

@Service
public class ScheduleService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	public Page<ScheduleOutputDto> list(Pageable pagination) {
		Page<Schedule> schedules = scheduleRepository.findAll(pagination);
		return schedules.map(s -> (modelMapper.map(s, ScheduleOutputDto.class)));
	}

}

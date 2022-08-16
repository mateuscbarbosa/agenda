package br.com.petshoptchutchucao.agenda.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.petshoptchutchucao.agenda.dto.LogsOutputDto;
import br.com.petshoptchutchucao.agenda.model.Logs;
import br.com.petshoptchutchucao.agenda.repository.LogsRepository;

@Service
public class LogsService {

	@Autowired
	private LogsRepository logsRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Page<LogsOutputDto> list(Pageable pagination) {
		
		Page<Logs> logs = logsRepository.findAll(pagination);
		return logs.map(l -> modelMapper.map(l, LogsOutputDto.class));
	}

}

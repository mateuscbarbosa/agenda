package br.com.petshoptchutchucao.agenda.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.petshoptchutchucao.agenda.dto.LogsOutputDto;
import br.com.petshoptchutchucao.agenda.dto.SimplifiedOutputDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.model.Activity;
import br.com.petshoptchutchucao.agenda.model.Logs;
import br.com.petshoptchutchucao.agenda.model.SystemModule;
import br.com.petshoptchutchucao.agenda.model.User;
import br.com.petshoptchutchucao.agenda.repository.LogsRepository;
import br.com.petshoptchutchucao.agenda.repository.UserRepository;

@Service
public class LogsService {

	@Autowired
	private LogsRepository logsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Page<LogsOutputDto> list(Pageable pagination) {
		
		Page<Logs> logs = logsRepository.findAll(pagination);
		return logs.map(l -> modelMapper.map(l, LogsOutputDto.class));
	}
	
	@Transactional
	public void registerLog(Authentication authentication, Activity activity, SystemModule module, String message) {
		Logs log = new Logs(LocalDateTime.now(),loadCurrentUser(authentication),activity, module, message);
		
		logsRepository.save(log);
	}

	@Transactional
	public void cleanLogs(LocalDateTime dateTime) {
		LocalDateTime thirtyDaysBefore = dateTime.minusMonths(1);
		
		if(logsRepository.existsByDateTime(thirtyDaysBefore)) {
			//Integer registredLogs = logsRepository.countByDateTime(thirtyDaysBefore); MENSAGEM DE QUANTOS LOGS SERÃO APAGADOS
			logsRepository.cleanMonthLogs(dateTime);
		}
	}
	
	private SimplifiedOutputDto loadCurrentUser(Authentication authentication) {
		User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new BusinessRulesException("Usuário inválido para esta operação"));
		SimplifiedOutputDto userSimplified = new SimplifiedOutputDto(user.getId(), user.getName());
		
		return userSimplified;
	}
}

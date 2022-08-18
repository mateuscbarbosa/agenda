package br.com.petshoptchutchucao.agenda.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.petshoptchutchucao.agenda.dto.TaskDetailedOutputDto;
import br.com.petshoptchutchucao.agenda.dto.TaskFormDto;
import br.com.petshoptchutchucao.agenda.dto.TaskOutputDto;
import br.com.petshoptchutchucao.agenda.dto.TaskUpdateFormDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.model.Activity;
import br.com.petshoptchutchucao.agenda.model.SystemModule;
import br.com.petshoptchutchucao.agenda.model.Task;
import br.com.petshoptchutchucao.agenda.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private LogsService logsService;
	
	public Page<TaskOutputDto> list(Pageable pagination) {
		Page<Task> tasks = taskRepository.findAll(pagination);
		return tasks.map(t -> modelMapper.map(t, TaskOutputDto.class));
	}

	@Transactional
	public TaskOutputDto register(TaskFormDto taskForm, Authentication authentication) {
		Task task = modelMapper.map(taskForm, Task.class);
		
		taskRepository.save(task);
		
		logsService.registerLog(authentication, Activity.REGISTRO, SystemModule.SERVIÇOS, "Serviço: " + task.getName());
		
		return modelMapper.map(task, TaskOutputDto.class);
	}

	@Transactional
	public TaskOutputDto update(TaskUpdateFormDto taskUpdate, Authentication authentication) {
		Task task = taskRepository.findById(taskUpdate.getId()).orElseThrow(() ->new BusinessRulesException("ID do Serviço não encontrado."));
		String oldTask = String.format("Serviço: %s"
									+ " Spécie: %s"
									+ " Tamanho: %s"
									+ " Preço: %s", task.getName(), task.getSpicies().toString(), task.getSize().toString(), task.getPrice().toString());
		
		task.updateInfo(taskUpdate.getName(),
						taskUpdate.getSpicies(),
						taskUpdate.getSize(),
						taskUpdate.getPrice());
		
		String newTask = String.format(" Serviço: %s"
									+ " Spécie: %s"
									+ " Tamanho: %s"
									+ " Preço: %s", task.getName(), task.getSpicies().toString(), task.getSize().toString(), task.getPrice().toString());
		
		taskRepository.save(task);
		
		logsService.registerLog(authentication, Activity.ATUALIZAÇÃO, SystemModule.SERVIÇOS, oldTask + " //PARA// " + newTask);
		
		return modelMapper.map(task, TaskOutputDto.class);
	}

	@Transactional
	public void delete(String id, Authentication authentication) {
		Task task = taskRepository.findById(id).orElseThrow(() -> new BusinessRulesException("ID do Serviço não encontrado."));
		
		taskRepository.deleteById(task.getId());
		
		String stringTask = String.format(" Serviço: %s"
				+ " Spécie: %s"
				+ " Tamanho: %s"
				+ " Preço: %s", task.getName(), task.getSpicies().toString(), task.getSize().toString(), task.getPrice().toString());
		
		logsService.registerLog(authentication, Activity.EXCLUSÃO, SystemModule.SERVIÇOS, stringTask);
	}

	public TaskDetailedOutputDto details(String id) {
		Task task = taskRepository.findById(id).orElseThrow(() -> new BusinessRulesException("ID do Serviço não encontrado"));
		
		return modelMapper.map(task, TaskDetailedOutputDto.class);
	}

}

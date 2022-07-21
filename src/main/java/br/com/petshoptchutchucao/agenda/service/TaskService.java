package br.com.petshoptchutchucao.agenda.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.petshoptchutchucao.agenda.dto.TaskFormDto;
import br.com.petshoptchutchucao.agenda.dto.TaskOutputDto;
import br.com.petshoptchutchucao.agenda.model.Task;
import br.com.petshoptchutchucao.agenda.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private TaskRepository taskRepository;
	
	public Page<TaskOutputDto> list(Pageable pagination) {
		Page<Task> tasks = taskRepository.findAll(pagination);
		return tasks.map(t -> modelMapper.map(t, TaskOutputDto.class));
	}

	public TaskOutputDto register(TaskFormDto taskForm) {
		Task task = modelMapper.map(taskForm, Task.class);
		
		taskRepository.save(task);
		
		return modelMapper.map(task, TaskOutputDto.class);
	}

}

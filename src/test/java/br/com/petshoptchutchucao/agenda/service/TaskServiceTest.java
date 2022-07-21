package br.com.petshoptchutchucao.agenda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.petshoptchutchucao.agenda.dto.TaskFormDto;
import br.com.petshoptchutchucao.agenda.dto.TaskOutputDto;
import br.com.petshoptchutchucao.agenda.dto.TaskUpdateFormDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;
import br.com.petshoptchutchucao.agenda.model.Task;
import br.com.petshoptchutchucao.agenda.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private TaskRepository taskRepository;
	
	@InjectMocks
	private TaskService service;
	
	@Test
	void couldRegisterATask() {
		TaskFormDto taskForm = new TaskFormDto("Serviço Teste", Spicies.CACHORRO, Size.MÉDIO, new BigDecimal(100));
		
		Task task = new Task(taskForm.getName(),
							taskForm.getSpicies(),
							taskForm.getSize(),
							taskForm.getPrice());
		
		Mockito.when(modelMapper.map(taskForm, Task.class)).thenReturn(task);
		
		Mockito.when(modelMapper.map(task, TaskOutputDto.class)).thenReturn(new TaskOutputDto(null, task.getName(), task.getPrice()));
		
		TaskOutputDto taskDto = service.register(taskForm);
		
		Mockito.verify(taskRepository).save(Mockito.any());
		
		assertEquals(taskForm.getName(), taskDto.getName());
		assertEquals(taskForm.getPrice(), taskDto.getPrice());
	}
	
	@Test
	void couldNotUpdateATaskWithIncorrectId() {
		TaskUpdateFormDto taskUpdate = new TaskUpdateFormDto("123456", "Serviço Teste", Spicies.CACHORRO, Size.MÉDIO, new BigDecimal(100));
		
		Mockito.when(taskRepository.findById(taskUpdate.getId())).thenThrow(BusinessRulesException.class);
		
		assertThrows(BusinessRulesException.class,() -> service.update(taskUpdate));
	}
	
	@Test
	void couldUpdateATaskWithCorrectId() {
		TaskUpdateFormDto taskUpdate = new TaskUpdateFormDto("123456", "Serviço Teste", Spicies.CACHORRO, Size.MÉDIO, new BigDecimal(100));
		
		Task task = new Task(taskUpdate.getId(),
							taskUpdate.getName(),
							taskUpdate.getSpicies(),
							taskUpdate.getSize(),
							taskUpdate.getPrice());
		
		Mockito.when(taskRepository.findById(taskUpdate.getId())).thenReturn(Optional.of(task));
		
		Mockito.when(modelMapper.map(task, TaskOutputDto.class)).thenReturn(new TaskOutputDto(task.getId(), task.getName(), task.getPrice()));
		
		TaskOutputDto taskDto = service.update(taskUpdate);
		
		Mockito.verify(taskRepository).save(Mockito.any());
		
		assertEquals(taskUpdate.getId(), taskDto.getId());
		assertEquals(taskUpdate.getName(), taskDto.getName());
		assertEquals(task.getPrice(), taskDto.getPrice());
	}

}

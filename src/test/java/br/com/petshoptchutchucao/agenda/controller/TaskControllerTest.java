package br.com.petshoptchutchucao.agenda.controller;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.petshoptchutchucao.agenda.dto.TaskFormDto;
import br.com.petshoptchutchucao.agenda.dto.TaskUpdateFormDto;
import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;
import br.com.petshoptchutchucao.agenda.model.Task;
import br.com.petshoptchutchucao.agenda.repository.TaskRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@AfterAll
	void deteleAllTasksTest() {
		taskRepository.deleteAllByName("TESTE");
	}
	
	@Test
	void couldNotRegisterATaskWithIncompletData() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldRegisterATaskWithCompletData() throws Exception {
		TaskFormDto taskForm = new TaskFormDto("Serviço Teste", Spicies.CACHORRO, Size.MÉDIO, new BigDecimal(100));
		
		String json = objectMapper.writeValueAsString(taskForm);
		String jsonWanted = "{\"name\":\"SERVIÇO TESTE\",\"price\":100}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}
	
	@Test
	void couldNotUpdateATaskWithWrongId() throws Exception {
		TaskUpdateFormDto taskUpdate = new TaskUpdateFormDto("123456", "Serviço Teste", Spicies.CACHORRO, Size.MÉDIO, new BigDecimal(100));
		
		String json = objectMapper.writeValueAsString(taskUpdate);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldUpdateATaskWithCorrectId() throws Exception{
		Task task = createTaskInBD("Serviçoo Teste");
		
		TaskUpdateFormDto taskUpdate = new TaskUpdateFormDto(task.getId(), task.getName(), Spicies.GATO, Size.PELO_CURTO, new BigDecimal(120));
		
		String json = objectMapper.writeValueAsString(taskUpdate);
		String jsonWanted = "{\"name\":\"SERVIÇOO TESTE\",\"price\":120}";
		
		mvc.perform(MockMvcRequestBuilders
				.put("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}

	private Task createTaskInBD(String name) {
		Task task = new Task(name, Spicies.CACHORRO, Size.MÉDIO, new BigDecimal(100));
		
		taskRepository.save(task);
		
		Task registred = taskRepository.findByName(task.getName());
		
		return registred;
	}

}

package br.com.petshoptchutchucao.agenda.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.petshoptchutchucao.agenda.model.request.TaskFormDto;
import br.com.petshoptchutchucao.agenda.model.request.TaskUpdateFormDto;
import br.com.petshoptchutchucao.agenda.infra.security.TokenService;
import br.com.petshoptchutchucao.agenda.model.entities.user.Profile;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Size;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Species;
import br.com.petshoptchutchucao.agenda.model.entities.user.Status;
import br.com.petshoptchutchucao.agenda.model.entities.schedule.Task;
import br.com.petshoptchutchucao.agenda.model.entities.user.User;
import br.com.petshoptchutchucao.agenda.model.repository.ProfileRepository;
import br.com.petshoptchutchucao.agenda.model.repository.TaskRepository;
import br.com.petshoptchutchucao.agenda.model.repository.UserRepository;

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
	
	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;
	
	private String token ;
	
	@BeforeAll
	void generateToken() {
		Profile admin = profileRepository.getById(0).get();
		List<Profile> profilesList = new ArrayList<>();
		profilesList.add(admin);
		
		User loged = new User("teste@admin.com.br","123456","Teste", profilesList, Status.ATIVO);
		loged.addProfile(admin);
		userRepository.save(loged);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(loged, loged.getEmail());
		this.token = tokenService.generateToken(authentication);
	}
	
	@AfterAll
	void deteleAllTasksTest() {
		taskRepository.deleteAllByName("TESTE");
		userRepository.deleteAllByEmail("teste");
	}
	
	@Test
	void couldNotPermitAnyInteractionWithWrongUserPermition() throws Exception{
		Profile admin = profileRepository.getById(60).get();
		List<Profile> profilesList = new ArrayList<>();
		profilesList.add(admin);
		
		User loged = new User("teste@admin.com.br","123456","Teste", profilesList, Status.ATIVO);
		loged.addProfile(admin);
		userRepository.save(loged);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(loged, loged.getEmail());
		String token = tokenService.generateToken(authentication);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/tasks")
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void couldNotRegisterATaskWithIncompletData() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldRegisterATaskWithCompletData() throws Exception {
		TaskFormDto taskForm = new TaskFormDto("Serviço Teste", Species.CACHORRO, Size.MÉDIO, new BigDecimal(100));
		
		String json = objectMapper.writeValueAsString(taskForm);
		String jsonWanted = "{\"name\":\"SERVIÇO TESTE\",\"price\":100}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}
	
	@Test
	void couldNotUpdateATaskWithWrongId() throws Exception {
		TaskUpdateFormDto taskUpdate = new TaskUpdateFormDto("123456", "Serviço Teste", Species.CACHORRO, Size.MÉDIO, new BigDecimal(100));
		
		String json = objectMapper.writeValueAsString(taskUpdate);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldUpdateATaskWithCorrectId() throws Exception{
		Task task = createTaskInBD("Serviçoo Teste");
		
		TaskUpdateFormDto taskUpdate = new TaskUpdateFormDto(task.getId(), task.getName(), Species.GATO, Size.PELO_CURTO, new BigDecimal(120));
		
		String json = objectMapper.writeValueAsString(taskUpdate);
		String jsonWanted = "{\"name\":\"SERVIÇOO TESTE\",\"price\":120}";
		
		mvc.perform(MockMvcRequestBuilders
				.put("/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}
	
	@Test
	void couldDeleteATaskWithCorrectId() throws Exception{
		Task task = createTaskInBD("Serviçooo Teste");
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/tasks/" + task.getId())
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	private Task createTaskInBD(String name) {
		Task task = new Task(name, Species.CACHORRO, Size.MÉDIO, new BigDecimal(100));
		
		taskRepository.save(task);
		
		Task registred = taskRepository.findByName(task.getName());
		
		return registred;
	}

}

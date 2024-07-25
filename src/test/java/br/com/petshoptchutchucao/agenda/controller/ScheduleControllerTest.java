package br.com.petshoptchutchucao.agenda.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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

import br.com.petshoptchutchucao.agenda.model.request.ScheduleFormDto;
import br.com.petshoptchutchucao.agenda.model.request.ScheduleUpdateForm;
import br.com.petshoptchutchucao.agenda.model.response.SimplifiedOutputDto;
import br.com.petshoptchutchucao.agenda.infra.security.TokenService;
import br.com.petshoptchutchucao.agenda.model.entities.schedule.ConfirmationStatus;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Customer;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Gender;
import br.com.petshoptchutchucao.agenda.model.entities.schedule.PaymentStatus;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Pet;
import br.com.petshoptchutchucao.agenda.model.entities.user.Profile;
import br.com.petshoptchutchucao.agenda.model.entities.schedule.Schedule;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Size;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Species;
import br.com.petshoptchutchucao.agenda.model.entities.user.Status;
import br.com.petshoptchutchucao.agenda.model.entities.schedule.Task;
import br.com.petshoptchutchucao.agenda.model.entities.user.User;
import br.com.petshoptchutchucao.agenda.model.repository.CustomerRepository;
import br.com.petshoptchutchucao.agenda.model.repository.PetRepository;
import br.com.petshoptchutchucao.agenda.model.repository.ProfileRepository;
import br.com.petshoptchutchucao.agenda.model.repository.ScheduleRepository;
import br.com.petshoptchutchucao.agenda.model.repository.TaskRepository;
import br.com.petshoptchutchucao.agenda.model.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScheduleControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PetRepository petRepository;
	
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
	
	private Customer customer;
	private Pet pet;
	private Task task;
	private List<String> listTasksString = new ArrayList<>();
	private String token;
	
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
	
	@BeforeAll
	void createCustomerPetTaskInBD() {
		Customer customer = new Customer("Cliente Teste", "Rua Teste", null, Status.ATIVO);
		this.customer = customerRepository.save(customer);
		Pet pet = new Pet("Pet Teste", Species.CACHORRO, Gender.MACHO, "Vira Lata", LocalDate.now(), Size.MÉDIO, null,this.customer.getId());
		this.pet = petRepository.save(pet);
		customer.addPet(this.pet.getId(), this.pet.getName());
		this.customer = customerRepository.save(customer);
		Task task = new Task("Teste", Species.CACHORRO, Size.MÉDIO, new BigDecimal(100.3));
		this.task = taskRepository.save(task);
		listTasksString.add(this.task.getId());
	}
	
	@AfterAll
	void deleteTestsRegistreds() {
		scheduleRepository.deleteAllByObservation("Teste");
		customerRepository.deleteAllByName("Teste");
		petRepository.deleteAllByName("Teste");
		taskRepository.deleteAllByName("Teste");
		userRepository.deleteAllByEmail("teste");
	}
	
	@Test
	void couldNotPermitAnyInteractionWithWrongUserPermitions() throws Exception{
		Profile profile = profileRepository.getById(60).get();
		List<Profile> profilesList = new ArrayList<>();
		profilesList.add(profile);
		User loged = new User("teste@admin.com.br","123456","Teste", profilesList, Status.ATIVO);
		loged.addProfile(profile);
		userRepository.save(loged);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(loged, loged.getEmail());
		String token = tokenService.generateToken(authentication);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/schedules")
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
	
	@Test
	void couldNotRegisterAScheduleIncomplete() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldNotRegisterAScheduleInPast() throws Exception{
		ScheduleFormDto scheduleFormDto = createScheduleForm(LocalDate.of(2000, 07, 27), LocalTime.of(10, 00));
		
		String json = objectMapper.writeValueAsString(scheduleFormDto);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldNotRegisterAScheduleOutOfExpedient() throws Exception{
		ScheduleFormDto scheduleFormDto = createScheduleForm(LocalDate.of(2030, 07, 28), LocalTime.of(8, 00));
		
		String json = objectMapper.writeValueAsString(scheduleFormDto);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Horário informado está fora do expediente de atendimento."));
	}
	
	@Test
	void couldNotRegisterAScheduleWithWrongMinutes() throws Exception{
		ScheduleFormDto scheduleFormDto = createScheduleForm(LocalDate.of(2030, 07, 28), LocalTime.of(10, 47));
		
		String json = objectMapper.writeValueAsString(scheduleFormDto);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Horário informado está fora do intervalo correto."));
	}
	
	@Test
	void couldNotRegisterAScheduleInTheSameTimeWithAnotherSchedule() throws Exception{
		Schedule schedule = new Schedule();
		schedule.setDate(LocalDate.of(2030, 07, 28));
		schedule.setTime(LocalTime.of(10, 30));
		schedule.setObservation("Teste");
		scheduleRepository.save(schedule);
		
		ScheduleFormDto scheduleForm = createScheduleForm(LocalDate.of(2030, 07, 28), LocalTime.of(10, 30));
		
		String json = objectMapper.writeValueAsString(scheduleForm);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Horário informado para o 28/07/2030 já está ocupado."));
	}
	
	@Test
	void couldNotRegisterAScheduleWithWrongCustomerId() throws Exception{
		String customerId = this.customer.getId();
		this.customer.setId("123456");
		ScheduleFormDto scheduleForm = createScheduleForm(LocalDate.of(2030, 07, 29), LocalTime.of(10, 30));
		
		String json = objectMapper.writeValueAsString(scheduleForm);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Cliente não encontrado."));
		
		this.customer.setId(customerId);
	}

	@Test
	void couldNotRegisterAScheduleWithWrongPetId() throws Exception{
		String petId = this.pet.getId();
		this.pet.setId("123456");
		ScheduleFormDto scheduleForm = createScheduleForm(LocalDate.of(2030, 07, 29), LocalTime.of(10, 30));
		
		String json = objectMapper.writeValueAsString(scheduleForm);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Pet não encontrado."));
		
		this.pet.setId(petId);
	}
	
	@Test
	void couldNotRegisterAScheduleWithWrongPetOwner() throws Exception {
		Customer customer = new Customer("Cliente Teste", "Rua Teste", null, Status.ATIVO);
		Customer registred = customerRepository.save(customer);
		String customerId = this.customer.getId();
		this.customer.setId(registred.getId());
		
		ScheduleFormDto scheduleForm = createScheduleForm(LocalDate.of(2030, 07, 29), LocalTime.of(10, 30));
		
		String json = objectMapper.writeValueAsString(scheduleForm);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Registro de Pet diferente do Dono."));
		
		this.customer.setId(customerId);
	}
	
	@Test
	void couldNotRegisterAScheduleWithWrogTaskId() throws Exception {
		String taskId = this.task.getId();
		this.task.setId("123456");
		listTasksString.remove(0);
		listTasksString.add(this.task.getId());
		
		
		ScheduleFormDto scheduleForm = createScheduleForm(LocalDate.of(2030, 07, 29), LocalTime.of(10, 30));
		
		String json = objectMapper.writeValueAsString(scheduleForm);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Serviço não encontrado."));
		
		this.task.setId(taskId);
		listTasksString.remove(0);
		listTasksString.add(this.task.getId());
	}
	
	@Test
	void couldNotRegisterAScheduleWithWrongTypeOfTask() throws Exception {
		Task task = new Task("Teste", Species.CACHORRO, Size.GRANDE, new BigDecimal(120.3));
		Task registred = taskRepository.save(task);
		listTasksString.add(registred.getId());
		
		ScheduleFormDto scheduleForm = createScheduleForm(LocalDate.of(2030, 07, 29), LocalTime.of(10, 30));
		
		String json = objectMapper.writeValueAsString(scheduleForm);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andExpect(MockMvcResultMatchers.content().string("Serviço selecionado não corresponde ao pet informado."));
		listTasksString.remove(1);
	}
	
	@Test
	void couldRegisterAScheduleWithAllCorrectData() throws Exception{
		ScheduleFormDto scheduleForm = createScheduleForm(LocalDate.of(2030, 07, 29), LocalTime.of(11, 30));
		
		String json = objectMapper.writeValueAsString(scheduleForm);
		String jsonWanted = "{\"time\":\"11:30:00\",\"customer\":{\"id\":\""+this.customer.getId()+"\",\"name\":\"Cliente Teste\"},"
							+ "\"pet\":{\"id\":\""+this.pet.getId()+"\",\"name\":\"Pet Teste\",\"spicies\":\"CACHORRO\",\"breed\":\"Vira Lata\"},"
							+ "\"tasks\":[{\"id\":\""+this.task.getId()+"\",\"name\":\"Teste\",\"price\":100.3}],"
							+ "\"observation\":\"Teste\",\"cost\":100.3,\"advised\":\"NÃO\",\"delivered\":\"NÃO\",\"payment\":\"PENDENTE\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}
	
	@Test
	void couldUpdateAScheduleWithSameDateTime() throws Exception{
		List<Task> tasks = new ArrayList<>();
		tasks.add(task);
		
		Schedule schedule = new Schedule(LocalDate.of(2030, 07, 30), LocalTime.of(11, 00),
										new SimplifiedOutputDto(customer.getId(), customer.getName()),
										pet, tasks, new BigDecimal(task.getPrice().toString()),"Teste", PaymentStatus.PENDENTE,
										ConfirmationStatus.NÃO, ConfirmationStatus.NÃO);
		Schedule registred = scheduleRepository.save(schedule);
		
		ScheduleUpdateForm scheduleUpdate = new ScheduleUpdateForm(registred.getId(), registred.getDate(), registred.getTime(),
																registred.getCustomer().getId(), registred.getPet().getId(), listTasksString,
																registred.getObservation(), PaymentStatus.PAGO, ConfirmationStatus.SIM, ConfirmationStatus.SIM);
		
		String json = objectMapper.writeValueAsString(scheduleUpdate);
		String jsonWanted = "{\"time\":\"11:00:00\",\"customer\":{\"id\":\""+this.customer.getId()+"\",\"name\":\"Cliente Teste\"},"
							+ "\"pet\":{\"id\":\""+this.pet.getId()+"\",\"name\":\"Pet Teste\",\"spicies\":\"CACHORRO\",\"breed\":\"Vira Lata\"},"
							+ "\"tasks\":[{\"id\":\""+this.task.getId()+"\",\"name\":\"Teste\",\"price\":100.3}],"
							+ "\"observation\":\"Teste\",\"cost\":100.3,\"advised\":\"SIM\",\"delivered\":\"SIM\",\"payment\":\"PAGO\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.put("/schedules")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}
	
	@Test
	void couldDeleteAScheduleWithCorrectId() throws Exception{
		List<Task> tasks = new ArrayList<>();
		tasks.add(task);
		
		Schedule schedule = new Schedule(LocalDate.of(2030, 8, 01), LocalTime.of(9, 00),
										new SimplifiedOutputDto(customer.getId(), customer.getName()), pet,
										tasks, new BigDecimal(task.getPrice().toString()), "Teste", PaymentStatus.PENDENTE,
										ConfirmationStatus.NÃO, ConfirmationStatus.NÃO);
		Schedule registred = scheduleRepository.save(schedule);
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/schedules/"+registred.getId())
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	private ScheduleFormDto createScheduleForm(LocalDate date, LocalTime time) {
		ScheduleFormDto scheduleForm = new ScheduleFormDto(date,
														time,
														customer.getId(),
														pet.getId(),
														listTasksString,
														"Teste");
		
		return scheduleForm;
	}
}

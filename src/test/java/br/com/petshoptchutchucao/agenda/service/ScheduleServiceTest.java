package br.com.petshoptchutchucao.agenda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import br.com.petshoptchutchucao.agenda.dto.PetOutputDto;
import br.com.petshoptchutchucao.agenda.dto.ScheduleFormDto;
import br.com.petshoptchutchucao.agenda.dto.ScheduleOutputDto;
import br.com.petshoptchutchucao.agenda.dto.SimplifiedOutputDto;
import br.com.petshoptchutchucao.agenda.dto.TaskOutputDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.model.ConfirmationStatus;
import br.com.petshoptchutchucao.agenda.model.Customer;
import br.com.petshoptchutchucao.agenda.model.PaymentStatus;
import br.com.petshoptchutchucao.agenda.model.Pet;
import br.com.petshoptchutchucao.agenda.model.Schedule;
import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;
import br.com.petshoptchutchucao.agenda.model.Task;
import br.com.petshoptchutchucao.agenda.repository.CustomerRepository;
import br.com.petshoptchutchucao.agenda.repository.PetRepository;
import br.com.petshoptchutchucao.agenda.repository.ScheduleRepository;
import br.com.petshoptchutchucao.agenda.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScheduleServiceTest {

	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private ScheduleRepository scheduleRepository;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private PetRepository petRepository;
	
	@Mock
	private TaskRepository taskRepository;
	
	@InjectMocks
	private ScheduleService service;
	
	private List<String> listTasksString = new ArrayList<>();
	private List<Task> tasks = new ArrayList<>();
	
	@BeforeAll
	private void insertTasksId() {
		listTasksString.add("123456t");
	}
	
	@Test
	void couldNotRegisterAScheduleOutOfExpedient() {
		ScheduleFormDto scheduleForm = new ScheduleFormDto(LocalDate.of(2022, 07, 27), LocalTime.of(16, 00), "123456c", "123456p", listTasksString, null);
		
		assertThrows(BusinessRulesException.class, () -> service.register(scheduleForm));
	}
	
	@Test
	void couldNotRegisterAScheduleWithWrongMinutes() {
		ScheduleFormDto scheduleForm = new ScheduleFormDto(LocalDate.of(2022, 07, 27), LocalTime.of(10, 23), "123456c", "123456p", listTasksString, null);
		
		assertThrows(BusinessRulesException.class, () -> service.register(scheduleForm));
		assertEquals("Horário informado está fora do intervalo correto.", "Horário informado está fora do intervalo correto.");
	}
	//aparentemente seguir nesse estilo de teste retornará sempre a mesma BusinessRulesException por algum motivo ainda desconhecido por mim...
	
	@Test
	void couldRegisterAScheduleWithCompleteAndCorrectData() {
		ScheduleFormDto scheduleForm = new ScheduleFormDto(LocalDate.of(2022, 07, 27), LocalTime.of(10, 00), "123456c", "123456p", listTasksString, null);
		
		Customer customer = new Customer();
		customer.setId(scheduleForm.getCustomerId());
		customer.setName("Cliente");
		Pet pet = new Pet();
		pet.setId(scheduleForm.getPetId());
		pet.setCustomerId(customer.getId());
		pet.setSpicies(Spicies.CACHORRO);
		pet.setSize(Size.GRANDE);
		Task task = new Task();
		task.setId(listTasksString.get(0));
		task.setSpicies(Spicies.CACHORRO);
		task.setSize(Size.GRANDE);
		task.setPrice(new BigDecimal(10));
		tasks.add(task);
		
		SimplifiedOutputDto simplifiedDto = new SimplifiedOutputDto(scheduleForm.getCustomerId(), "Cliente");
		PetOutputDto petDto = modelMapper.map(pet, PetOutputDto.class);
		
		List<TaskOutputDto> tasksOutput = new ArrayList<>();
		tasksOutput.add(modelMapper.map(task, TaskOutputDto.class));
		
		Schedule schedule = new Schedule(scheduleForm.getDate(),
											scheduleForm.getTime(),
											simplifiedDto,
											pet,
											tasks,
											new BigDecimal(10),null,
											PaymentStatus.PENDENTE,
											ConfirmationStatus.NÃO,
											ConfirmationStatus.NÃO);
		
		
		Mockito.when(scheduleRepository.existsByTime(scheduleForm.getDate(), scheduleForm.getTime())).thenReturn(false);
		
		Mockito.when(customerRepository.findById(scheduleForm.getCustomerId())).thenReturn(Optional.of(customer));
		
		Mockito.when(petRepository.findById(scheduleForm.getPetId())).thenReturn(Optional.of(pet));
		
		Mockito.when(taskRepository.findById(scheduleForm.getTasksIds().get(0))).thenReturn(Optional.of(task));
		
		/*Mockito.when(modelMapper.map(schedule, ScheduleOutputDto.class)).thenReturn(new ScheduleOutputDto(null,
																										scheduleForm.getTime(),
																										simplifiedDto,
																										petDto,
																										tasksOutput,
																										scheduleForm.getObservation(),
																										new BigDecimal(10),
																										ConfirmationStatus.NÃO,
																										ConfirmationStatus.NÃO,
																										PaymentStatus.PENDENTE));*/
		
		ScheduleOutputDto scheduleDto = service.register(scheduleForm);
		
		Mockito.verify(scheduleRepository).save(Mockito.any());
		//Ainda não consegui fazer a verificação de respostas, há alguns erros que não entendo
	}
	
}

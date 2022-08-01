package br.com.petshoptchutchucao.agenda.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.petshoptchutchucao.agenda.dto.ScheduleDetailedOutputDto;
import br.com.petshoptchutchucao.agenda.dto.ScheduleFormDto;
import br.com.petshoptchutchucao.agenda.dto.ScheduleOutputDto;
import br.com.petshoptchutchucao.agenda.dto.ScheduleUpdateForm;
import br.com.petshoptchutchucao.agenda.dto.SimplifiedOutputDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.model.ConfirmationStatus;
import br.com.petshoptchutchucao.agenda.model.Customer;
import br.com.petshoptchutchucao.agenda.model.PaymentStatus;
import br.com.petshoptchutchucao.agenda.model.Pet;
import br.com.petshoptchutchucao.agenda.model.Schedule;
import br.com.petshoptchutchucao.agenda.model.Task;
import br.com.petshoptchutchucao.agenda.repository.CustomerRepository;
import br.com.petshoptchutchucao.agenda.repository.PetRepository;
import br.com.petshoptchutchucao.agenda.repository.ScheduleRepository;
import br.com.petshoptchutchucao.agenda.repository.TaskRepository;

@Service
public class ScheduleService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	private BigDecimal totalCost = new BigDecimal(0);
	
	public Page<ScheduleOutputDto> list(Pageable pagination) {
		Page<Schedule> schedules = scheduleRepository.findAll(pagination);
		return schedules.map(s -> (modelMapper.map(s, ScheduleOutputDto.class)));
	}

	@Transactional
	public ScheduleOutputDto register(ScheduleFormDto scheduleForm) {
		Schedule schedule = new Schedule();
		schedule.setDate(scheduleForm.getDate());
		schedule.setTime(validateTime(scheduleForm.getDate(), scheduleForm.getTime()));
		schedule.setCustomer(findCustomer(scheduleForm.getCustomerId()));
		schedule.setPet(findPet(scheduleForm.getPetId(), scheduleForm.getCustomerId()));
		schedule.setTasks(findTasks(scheduleForm.getTasksIds(), scheduleForm.getPetId(), scheduleForm.getCustomerId()));
		schedule.setCost(totalCost);
		schedule.setObservation(scheduleForm.getObservation());
		schedule.setPayment(PaymentStatus.PENDENTE);
		schedule.setAdvised(ConfirmationStatus.NÃO);
		schedule.setDelivered(ConfirmationStatus.NÃO);
		
		scheduleRepository.save(schedule);
		
		return modelMapper.map(schedule, ScheduleOutputDto.class);
	}
	
	@Transactional
	public ScheduleOutputDto update(ScheduleUpdateForm scheduleUpdate) {
		Schedule schedule = scheduleRepository.findById(scheduleUpdate.getId()).orElseThrow(() -> new BusinessRulesException("ID da agenda não encontrado."));
		
		schedule.updateInfo(scheduleUpdate.getDate(),
							validateTimeSameSchedule(schedule.getId(), scheduleUpdate.getDate(), scheduleUpdate.getTime()),
							findCustomer(scheduleUpdate.getCustomerId()),
							findPet(scheduleUpdate.getPetId(), scheduleUpdate.getCustomerId()),
							findTasks(scheduleUpdate.getTasksIds(), scheduleUpdate.getPetId(), scheduleUpdate.getCustomerId()),
							totalCost,
							scheduleUpdate.getObservation(),
							scheduleUpdate.getPayment(),
							scheduleUpdate.getAdvised(),
							scheduleUpdate.getDelivered());
		
		scheduleRepository.save(schedule);
		
		return modelMapper.map(schedule, ScheduleOutputDto.class);
	}
	
	@Transactional
	public void delete(String id) {
		Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new BusinessRulesException("Agendamento não encontrado."));
		
		scheduleRepository.delete(schedule);
	}
	
	public ScheduleDetailedOutputDto details(String id) {
		Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new BusinessRulesException("Agendamento não encontrado."));
		
		return modelMapper.map(schedule, ScheduleDetailedOutputDto.class);
	}
	
	private LocalTime validateTime(LocalDate day,LocalTime time) {
		LocalTime firstCall = LocalTime.of(8, 30);
		LocalTime lastCall = LocalTime.of(15, 30);
		
		if(time.isBefore(firstCall) || time.isAfter(lastCall)) {
			throw new BusinessRulesException("Horário informado está fora do expediente de atendimento.");
		}
		else if(!(time.getMinute() == 00) && !(time.getMinute() == 30)) {
			throw new BusinessRulesException("Horário informado está fora do intervalo correto.");
		}
		else if(scheduleRepository.existsByTime(day, time)) {
			throw new BusinessRulesException("Horário informado para o " + day.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+ " já está ocupado.");
		}
		else {
			return time;
		}
	}
	
	private SimplifiedOutputDto findCustomer(String customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new BusinessRulesException("Cliente não encontrado."));
		
		return new SimplifiedOutputDto(customer.getId(), customer.getName());
	}

	private Pet findPet(String petId, String customerId) {
		Pet pet = petRepository.findById(petId).orElseThrow(() -> new BusinessRulesException("Pet não encontrado."));
		
		if(!pet.getCustomerId().equals(customerId)) {
			throw new BusinessRulesException("Registro de Pet diferente do Dono.");
		}
		
		return pet;
	}
	
	private List<Task> findTasks(List<String> tasksIds, String petId, String customerId) {
		totalCost = new BigDecimal(0);
		Pet pet = findPet(petId, customerId);
		List<Task> tasks = new ArrayList<>();
		
		for (String task : tasksIds) {
			Task taskBD = taskRepository.findById(task).orElseThrow(() -> new BusinessRulesException("Serviço não encontrado."));
			if(taskBD.getSpicies().equals(pet.getSpicies()) && taskBD.getSize().equals(pet.getSize())) {
				tasks.add(taskBD);
				totalCost = totalCost.add(taskBD.getPrice());
			}
			else {
				throw new BusinessRulesException("Serviço selecionado não corresponde ao pet informado.");
			}
		}
		
		return tasks;
	}
	
	private LocalTime validateTimeSameSchedule(String id, LocalDate date, LocalTime time) {
		Schedule schedule = scheduleRepository.findById(id).get();
		
		if(!schedule.getDate().equals(date) && !schedule.getTime().equals(time)) {
			validateTime(date, time);
		}
		
		return time;
	}

}

package br.com.petshoptchutchucao.agenda.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ScheduleFormDto {

	@NotNull
	@FutureOrPresent
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonAlias("dia")
	private LocalDate date;
	
	@NotNull
	@JsonFormat(pattern = "HH:mm")
	@JsonAlias("horario")
	private LocalTime time;
	
	@NotBlank
	@JsonAlias("cliente_id")
	private String customerId;
	
	@NotBlank
	@JsonAlias("pet_id")
	private String petId;
	
	@NotNull
	@JsonAlias("servicos_id")
	private List<String> tasksIds;
	
	@JsonAlias("observacao")
	private String observation;
	
	public ScheduleFormDto() {}

	public ScheduleFormDto(@FutureOrPresent @NotNull LocalDate date, @NotNull LocalTime time, @NotBlank String customerId,
			@NotBlank String petId, @NotNull List<String> tasksIds, String observation) {
		this.date = date;
		this.time = time;
		this.customerId = customerId;
		this.petId = petId;
		this.tasksIds = tasksIds;
		this.observation = observation;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getTime() {
		return time;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getPetId() {
		return petId;
	}

	public List<String> getTasksIds() {
		return tasksIds;
	}

	public String getObservation() {
		return observation;
	}
	
}

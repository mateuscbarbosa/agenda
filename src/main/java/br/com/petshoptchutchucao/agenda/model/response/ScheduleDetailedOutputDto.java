package br.com.petshoptchutchucao.agenda.model.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.petshoptchutchucao.agenda.model.entities.schedule.ConfirmationStatus;
import br.com.petshoptchutchucao.agenda.model.entities.schedule.PaymentStatus;

public class ScheduleDetailedOutputDto extends ScheduleOutputDto {

	private LocalDate date;

	public ScheduleDetailedOutputDto() {}

	public ScheduleDetailedOutputDto(String id, LocalDate date, LocalTime time, SimplifiedOutputDto customer, PetOutputDto pet,
									 List<TaskOutputDto> tasks, String observation, BigDecimal cost, ConfirmationStatus advised,
									 ConfirmationStatus delivered, PaymentStatus payment) {
		super(id, time, customer, pet, tasks, observation, cost, advised, delivered, payment);
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
}

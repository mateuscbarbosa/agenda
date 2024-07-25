package br.com.petshoptchutchucao.agenda.model.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.petshoptchutchucao.agenda.model.entities.schedule.ConfirmationStatus;
import br.com.petshoptchutchucao.agenda.model.entities.schedule.PaymentStatus;

public class ScheduleUpdateForm extends ScheduleFormDto {

	@NotBlank
	@JsonAlias("ID")
	private String id;
	
	@NotNull
	@JsonAlias("pagamento")
	private PaymentStatus payment;
	
	@NotNull
	@JsonAlias("avisado")
	private ConfirmationStatus advised;
	
	@NotNull
	@JsonAlias("entregue")
	private ConfirmationStatus delivered;

	public ScheduleUpdateForm() {}

	public ScheduleUpdateForm(String id, LocalDate date, LocalTime time, String customerId, String petId, List<String> tasksIds,
			String observation, PaymentStatus payment, ConfirmationStatus advised, ConfirmationStatus delivered) {
		super(date, time, customerId, petId, tasksIds, observation);
		this.id = id;
		this.payment = payment;
		this.advised = advised;
		this.delivered = delivered;
	}
	
	public String getId() {
		return id;
	}

	public PaymentStatus getPayment() {
		return payment;
	}

	public ConfirmationStatus getAdvised() {
		return advised;
	}

	public ConfirmationStatus getDelivered() {
		return delivered;
	}
	
}

package br.com.petshoptchutchucao.agenda.model.response;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import br.com.petshoptchutchucao.agenda.model.entities.schedule.ConfirmationStatus;
import br.com.petshoptchutchucao.agenda.model.entities.schedule.PaymentStatus;

public class ScheduleOutputDto {

	private String id;
	private LocalTime time;
	private SimplifiedOutputDto customer;
	private PetOutputDto pet;
	private List<TaskOutputDto> tasks;
	private String observation;
	private BigDecimal cost;
	private ConfirmationStatus advised;
	private ConfirmationStatus delivered;
	private PaymentStatus payment;
	
	public ScheduleOutputDto() {}
	
	public ScheduleOutputDto(String id, LocalTime time, SimplifiedOutputDto customer, PetOutputDto pet,
			List<TaskOutputDto> tasks, String observation, BigDecimal cost, ConfirmationStatus advised,
			ConfirmationStatus delivered, PaymentStatus payment) {
		this.id = id;
		this.time = time;
		this.customer = customer;
		this.pet = pet;
		this.tasks = tasks;
		this.observation = observation;
		this.cost = cost;
		this.advised = advised;
		this.delivered = delivered;
		this.payment = payment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public SimplifiedOutputDto getCustomer() {
		return customer;
	}

	public void setCustomer(SimplifiedOutputDto customer) {
		this.customer = customer;
	}

	public PetOutputDto getPet() {
		return pet;
	}

	public void setPet(PetOutputDto pet) {
		this.pet = pet;
	}

	public List<TaskOutputDto> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskOutputDto> tasks) {
		this.tasks = tasks;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public ConfirmationStatus getAdvised() {
		return advised;
	}

	public void setAdvised(ConfirmationStatus advised) {
		this.advised = advised;
	}

	public ConfirmationStatus getDelivered() {
		return delivered;
	}

	public void setDelivered(ConfirmationStatus delivered) {
		this.delivered = delivered;
	}

	public PaymentStatus getPayment() {
		return payment;
	}

	public void setPayment(PaymentStatus payment) {
		this.payment = payment;
	}
	
}

package br.com.petshoptchutchucao.agenda.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="schedules")
public class Schedule {

	@Id
	private String id;
	private LocalDate date;
	private LocalTime time;
	private Customer customer;
	private Pet pet;
	private List<Task> tasks = new ArrayList<>();
	private BigDecimal cost;
	private String observation;
	private PaymentStatus payment;
	private ConfirmationStatus advised;
	private ConfirmationStatus delivered;
	
	public Schedule() {}

	public Schedule(String id, LocalDate date, LocalTime time, Customer customer, Pet pet, List<Task> tasks,
			BigDecimal cost, String observation, PaymentStatus payment, ConfirmationStatus advised, ConfirmationStatus delivered) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.customer = customer;
		this.pet = pet;
		this.tasks = tasks;
		this.cost = cost;
		this.observation = observation;
		this.payment = payment;
		this.advised = advised;
		this.delivered = delivered;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public PaymentStatus getPayment() {
		return payment;
	}

	public void setPayment(PaymentStatus payment) {
		this.payment = payment;
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
	
}

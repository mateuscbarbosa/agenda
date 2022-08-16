package br.com.petshoptchutchucao.agenda.model;

import org.springframework.data.mongodb.core.mapping.Document;

import br.com.petshoptchutchucao.agenda.dto.SimplifiedOutputDto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

@Document(collection = "logs")
public class Logs {

	@Id
	private String Id;
	private LocalDateTime dateTime;
	private SimplifiedOutputDto user;
	private Activity activity;
	private String changes;
	
	public Logs() {}
	
	public Logs(LocalDateTime dateTime, SimplifiedOutputDto user, Activity activity, String changes) {
		super();
		this.dateTime = dateTime;
		this.user = user;
		this.activity = activity;
		this.changes = changes;
	}

	public Logs(String id, LocalDateTime dateTime, SimplifiedOutputDto user, Activity activity, String changes) {
		Id = id;
		this.dateTime = dateTime;
		this.user = user;
		this.activity = activity;
		this.changes = changes;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public SimplifiedOutputDto getUser() {
		return user;
	}

	public void setUser(SimplifiedOutputDto user) {
		this.user = user;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}
	
}

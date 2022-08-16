package br.com.petshoptchutchucao.agenda.dto;

import java.time.LocalDateTime;

import br.com.petshoptchutchucao.agenda.model.Activity;

public class LogsOutputDto {
	
	private String id;
	private LocalDateTime dateTime;
	private SimplifiedOutputDto user;
	private Activity activity;
	private String changes;
	
	public LogsOutputDto() {}

	public LogsOutputDto(String id, LocalDateTime dateTime, SimplifiedOutputDto user, Activity activity, String changes) {
		this.id = id;
		this.dateTime = dateTime;
		this.user = user;
		this.activity = activity;
		this.changes = changes;
	}

	public String getId() {
		return id;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public SimplifiedOutputDto getUser() {
		return user;
	}

	public Activity getActivity() {
		return activity;
	}

	public String getChanges() {
		return changes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public void setUser(SimplifiedOutputDto user) {
		this.user = user;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}
	
}

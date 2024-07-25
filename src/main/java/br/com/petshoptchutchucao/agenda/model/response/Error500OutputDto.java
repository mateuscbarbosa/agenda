package br.com.petshoptchutchucao.agenda.model.response;

import java.time.LocalDateTime;

public class Error500OutputDto {

	private LocalDateTime timestamp;
	private String error;
	private String message;
	private String path;
	
	public Error500OutputDto() {}

	public Error500OutputDto(LocalDateTime timestamp, String error, String message, String path) {
		this.timestamp = timestamp;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}	

}

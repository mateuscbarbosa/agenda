package br.com.petshoptchutchucao.agenda.model.response;

public class Error400OutputDto {

	private String field;
	private String message;
	
	public Error400OutputDto(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
	
	
}

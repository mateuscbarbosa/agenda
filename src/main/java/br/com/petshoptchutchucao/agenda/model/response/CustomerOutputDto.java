package br.com.petshoptchutchucao.agenda.model.response;

import java.util.List;

public class CustomerOutputDto extends SimplifiedOutputDto {

	private String address;
	private List<String> contactNumbers;
	
	public CustomerOutputDto() {}

	public CustomerOutputDto(String id,String name, String address, List<String> contactNumbers) {
		super(id, name);
		this.address = address;
		this.contactNumbers = contactNumbers;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getContactNumbers() {
		return contactNumbers;
	}

	public void setContactNumbers(List<String> contactNumbers) {
		this.contactNumbers = contactNumbers;
	}
}

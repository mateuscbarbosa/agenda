package br.com.petshoptchutchucao.agenda.dto;

import java.math.BigDecimal;

public class TaskOutputDto extends SimplifiedOutputDto{

	private BigDecimal price;

	public TaskOutputDto() {}
	
	public TaskOutputDto(String id, String name, BigDecimal price) {
		super(id,name);
		this.price = price;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}

package br.com.petshoptchutchucao.agenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.petshoptchutchucao.agenda.dto.CustomerFormDto;
import br.com.petshoptchutchucao.agenda.dto.CustomerUpdateFormDto;
import br.com.petshoptchutchucao.agenda.model.Customer;
import br.com.petshoptchutchucao.agenda.model.Status;
import br.com.petshoptchutchucao.agenda.repository.CustomerRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private List<String> contactNumbers = new ArrayList<>();

	@BeforeAll
	void insertContactNumber() {
		contactNumbers.add("00 00000-0000");
	}
	
	@AfterAll
	void deleteAllCustomersTest() {
		customerRepository.deleteAllByName("Teste");
	}
	
	@Test
	void couldNotRegisterACustomerWithIncompleteData() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldRegisterACustomerWithCompleteData() throws Exception {
		CustomerFormDto customerForm = new CustomerFormDto("Cliente Teste", "Rua Teste, 00 - Bairro Teste. Teste/TE", contactNumbers);
		
		String json = objectMapper.writeValueAsString(customerForm);
		String jsonWanted = "{\"name\":\"Cliente Teste\",\"address\":\"Rua Teste, 00 - Bairro Teste. Teste/TE\",\"contactNumbers\":[\"00 00000-0000\"]}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));		
	}

	@Test
	void couldNotUpdateACustomerWithIncorrectId() throws Exception {
		CustomerUpdateFormDto customerUpdate = new CustomerUpdateFormDto("123456", "Cliente Teste", null, contactNumbers, Status.ATIVO, null);
		
		String json = objectMapper.writeValueAsString(customerUpdate);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldUpdateAcustomerWithCorrectId() throws Exception {
		Customer customer = createCustomerInBD("Cliente Um Teste");
		
		CustomerUpdateFormDto customerUpdate = new CustomerUpdateFormDto(customer.getId(),
																		customer.getName(),
																		"Rua Teste, 11 - Bairro Teste 1. Teste/TE",
																		contactNumbers,
																		customer.getStatus(),
																		customer.getPets());
		String json = objectMapper.writeValueAsString(customerUpdate);
		String jsonWanted = "{\"name\":\"Cliente Um Teste\",\"address\":\"Rua Teste, 11 - Bairro Teste 1. Teste/TE\",\"contactNumbers\":[\"00 00000-0000\"]}";
		
		mvc.perform(MockMvcRequestBuilders
				.put("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
		
	}

	private Customer createCustomerInBD(String name) {
		Customer newCustomer = new Customer(name,"Rua Teste, 00 - Bairro Teste. Teste/TE", contactNumbers, Status.ATIVO);
		
		customerRepository.save(newCustomer);
		
		Customer registred = customerRepository.findByName(newCustomer.getName());
				
		return registred;
	}
}

package br.com.petshoptchutchucao.agenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
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
		contactNumbers.add("00 00000-0000");
		
		CustomerFormDto customerForm = new CustomerFormDto("Cliente Teste", "Rua Teste, 00 - Bairro Teste. Teste/TE", contactNumbers);
		
		String json = objectMapper.writeValueAsString(customerForm);
		String jsonWanted = "{\"name\":\"Cliente Teste\",\"address\":\"Rua Teste, 00 - Bairro Teste. Teste/TE\",\"contactNumbers\":[\"00 00000-0000\"]}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
		
		contactNumbers.removeAll(contactNumbers);
	}

}
